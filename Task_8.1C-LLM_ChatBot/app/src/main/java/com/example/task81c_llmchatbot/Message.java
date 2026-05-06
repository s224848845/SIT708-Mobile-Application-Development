package com.example.task81c_llmchatbot;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room entity representing a single chat message.
 * Each record stores the message text, sender type, and timestamp.
 */
@Entity(tableName = "messages")
public class Message {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String messageText;

    // true means the message was sent by the user.
    // false means the message was sent by the chatbot.
    public boolean isUser;

    public String timestamp;

    public Message(String messageText, boolean isUser, String timestamp) {
        this.messageText = messageText;
        this.isUser = isUser;
        this.timestamp = timestamp;
    }
}