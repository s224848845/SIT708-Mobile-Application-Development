package com.example.task_41p_personal_event_planner_app;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventDao {

    // Inserts a new event into the Room database.
    @Insert
    void insert(Event event);

    // Updates an existing event in the Room database.
    @Update
    void update(Event event);

    // Deletes the selected event from the Room database.
    @Delete
    void delete(Event event);

    // Returns all events ordered by date and time in ascending order.
    @Query("SELECT * FROM events ORDER BY dateTime ASC")
    LiveData<List<Event>> getAllEvents();
}