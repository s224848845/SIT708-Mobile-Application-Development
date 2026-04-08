package com.example.task_41p_personal_event_planner_app;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventRepository {

    // Repository acts as the single source of data for the ViewModel.
    private final EventDao eventDao;
    private final LiveData<List<Event>> allEvents;

    // Room database operations should not run on the main thread.
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public EventRepository(Application application) {
        EventDatabase db = EventDatabase.getDatabase(application);
        eventDao = db.eventDao();
        allEvents = eventDao.getAllEvents();
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public void insert(Event event) {
        executorService.execute(() -> eventDao.insert(event));
    }

    public void update(Event event) {
        executorService.execute(() -> eventDao.update(event));
    }

    public void delete(Event event) {
        executorService.execute(() -> eventDao.delete(event));
    }
}