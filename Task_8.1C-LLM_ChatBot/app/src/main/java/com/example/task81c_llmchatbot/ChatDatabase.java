package com.example.task81c_llmchatbot;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Singleton Room database for local SQLite chat history.
 */
@Database(entities = {Message.class}, version = 1)
public abstract class ChatDatabase extends RoomDatabase {

    private static ChatDatabase instance;

    public abstract MessageDao messageDao();

    public static synchronized ChatDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ChatDatabase.class,
                    "chat_database"
            ).build();
        }

        return instance;
    }
}