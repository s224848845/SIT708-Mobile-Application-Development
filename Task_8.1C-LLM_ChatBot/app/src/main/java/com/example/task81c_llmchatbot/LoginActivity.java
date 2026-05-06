package com.example.task81c_llmchatbot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * LoginActivity is the authentication/entry screen.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        goButton = findViewById(R.id.goButton);

        goButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString().trim();

            // Basic validation prevents blank usernames from continuing.
            if (username.isEmpty()) {
                Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
                return;
            }

            // The username is passed to ChatActivity to personalise the welcome text.
            Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
        });
    }
}