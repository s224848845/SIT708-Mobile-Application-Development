package com.example.task81c_llmchatbot;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * DAO defines database operations for storing and reading chat messages.
 */
@Dao
public interface MessageDao {

    @Insert
    void insertMessage(Message message);

    @Query("SELECT * FROM messages ORDER BY id ASC")
    List<Message> getAllMessages();

    @Query("DELETE FROM messages")
    void clearMessages();
}