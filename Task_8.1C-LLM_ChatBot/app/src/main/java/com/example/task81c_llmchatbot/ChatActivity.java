package com.example.task81c_llmchatbot;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * ChatActivity is the main chatbot screen.
 * It manages user input, displays messages, calls the LLM service,
 * stores messages in Room, and reloads previous chat history.
 */
public class ChatActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private RecyclerView chatRecyclerView;
    private EditText messageEditText;
    private Button sendButton;

    private ChatAdapter chatAdapter;
    private List<Message> messageList;

    private ChatDatabase chatDatabase;
    private LLMService llmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        String username = getIntent().getStringExtra("USERNAME");
        welcomeTextView.setText("Welcome " + username + "!");

        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList);

        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(chatAdapter);

        chatDatabase = ChatDatabase.getInstance(this);
        llmService = new LLMService();

        // Load previous messages from Room when the chat screen opens.
        loadChatHistory();

        sendButton.setOnClickListener(view -> sendUserMessage());
    }

    private void sendUserMessage() {
        String userText = messageEditText.getText().toString().trim();

        // Prevent empty messages from being saved or sent to the API.
        if (userText.isEmpty()) {
            Toast.makeText(this, "Please type a message", Toast.LENGTH_SHORT).show();
            return;
        }

        messageEditText.setText("");

        Message userMessage = new Message(userText, true, getCurrentTime());
        addMessage(userMessage, true);

        // Temporary message improves user experience while waiting for the API response.
        Message thinkingMessage = new Message("Bot is thinking...", false, getCurrentTime());
        messageList.add(thinkingMessage);
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        chatRecyclerView.scrollToPosition(messageList.size() - 1);

        llmService.sendMessageToLLM(userText, new LLMCallback() {
            @Override
            public void onSuccess(String reply) {
                runOnUiThread(() -> {
                    removeThinkingMessage(thinkingMessage);

                    Message botMessage = new Message(reply, false, getCurrentTime());
                    addMessage(botMessage, true);
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    removeThinkingMessage(thinkingMessage);

                    Message errorMessage = new Message(
                            "Sorry, I could not get a response. " + error,
                            false,
                            getCurrentTime()
                    );

                    addMessage(errorMessage, true);
                });
            }
        });
    }

    private void addMessage(Message message, boolean saveToDatabase) {
        messageList.add(message);
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        chatRecyclerView.scrollToPosition(messageList.size() - 1);

        // Database operations are executed on a background thread to avoid UI freezing.
        if (saveToDatabase) {
            new Thread(() -> chatDatabase.messageDao().insertMessage(message)).start();
        }
    }

    private void removeThinkingMessage(Message thinkingMessage) {
        int index = messageList.indexOf(thinkingMessage);

        if (index >= 0) {
            messageList.remove(index);
            chatAdapter.notifyItemRemoved(index);
        }
    }

    private void loadChatHistory() {
        new Thread(() -> {
            List<Message> savedMessages = chatDatabase.messageDao().getAllMessages();

            runOnUiThread(() -> {
                messageList.clear();
                messageList.addAll(savedMessages);
                chatAdapter.notifyDataSetChanged();

                if (!messageList.isEmpty()) {
                    chatRecyclerView.scrollToPosition(messageList.size() - 1);
                }
            });
        }).start();
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }
}