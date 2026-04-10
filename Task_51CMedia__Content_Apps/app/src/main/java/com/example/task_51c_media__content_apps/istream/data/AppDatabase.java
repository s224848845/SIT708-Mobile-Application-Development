package com.example.task_51c_media__content_apps.istream.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.task_51c_media__content_apps.istream.model.PlaylistItem;
import com.example.task_51c_media__content_apps.istream.model.User;

@Database(entities = {User.class, PlaylistItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract PlaylistDao playlistDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "istream_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}