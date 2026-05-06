package com.example.task81c_llmchatbot;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * RecyclerView adapter that displays chat messages as bubbles.
 * User messages are aligned right and bot messages are aligned left.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private final List<Message> messageList;

    public ChatAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);

        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        Message message = messageList.get(position);

        holder.messageTextView.setText(message.messageText);
        holder.timeTextView.setText(message.timestamp);

        if (message.isUser) {
            // User messages appear on the right with a green bubble.
            holder.messageContainer.setGravity(Gravity.END);
            holder.messageTextView.setBackgroundResource(R.drawable.user_bubble);
        } else {
            // Bot messages appear on the left with a white bubble.
            holder.messageContainer.setGravity(Gravity.START);
            holder.messageTextView.setBackgroundResource(R.drawable.bot_bubble);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {

        LinearLayout messageContainer;
        TextView messageTextView;
        TextView timeTextView;

        public ChatViewHolder(View itemView) {
            super(itemView);

            messageContainer = itemView.findViewById(R.id.messageContainer);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}
