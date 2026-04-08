package com.example.task_41p_personal_event_planner_app;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Event.class}, version = 1, exportSchema = false)
public abstract class EventDatabase extends RoomDatabase {

    // Provides access to database operations defined in EventDao.
    public abstract EventDao eventDao();

    // Singleton instance so only one database object exists in the app.
    private static volatile EventDatabase INSTANCE;

    public static EventDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (EventDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            EventDatabase.class,
                            "event_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}