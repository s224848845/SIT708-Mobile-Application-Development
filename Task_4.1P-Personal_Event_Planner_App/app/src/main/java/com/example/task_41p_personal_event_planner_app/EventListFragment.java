package com.example.task_41p_personal_event_planner_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EventListFragment extends Fragment {

    // Shared ViewModel used to load and manage event data.
    private EventViewModel eventViewModel;

    public EventListFragment() {
        super(R.layout.fragment_event_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up RecyclerView and adapter for displaying saved events.
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewEvents);
        EventAdapter adapter = new EventAdapter(requireContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Observe Room database data through ViewModel.
        // Any insert, update, or delete automatically refreshes the list.
        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        eventViewModel.getAllEvents().observe(getViewLifecycleOwner(), adapter::setEvents);

        // Handle edit and delete actions for each event item.
        adapter.setOnEventClickListener(new EventAdapter.OnEventClickListener() {
            @Override
            public void onEditClick(Event event) {
                // Pass selected event data to AddEditEventFragment.
                Bundle bundle = new Bundle();
                bundle.putInt("eventId", event.getId());
                bundle.putString("title", event.getTitle());
                bundle.putString("category", event.getCategory());
                bundle.putString("location", event.getLocation());
                bundle.putLong("dateTime", event.getDateTime());

                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.addEditEventFragment, bundle);
            }

            @Override
            public void onDeleteClick(Event event) {
                // Delete selected event from Room database.
                eventViewModel.delete(event);
                Toast.makeText(requireContext(), "Event deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}