package com.example.task_41p_personal_event_planner_app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.Calendar;

public class AddEditEventFragment extends Fragment {

    // Input fields and controls for the Add/Edit Event screen.
    private EditText editTitle, editLocation;
    private Spinner spinnerCategory;
    private TextView textSelectedDateTime;
    private Button btnPickDateTime, btnSaveEvent;

    // Shared ViewModel used to insert or update event data.
    private EventViewModel eventViewModel;

    // Stores the selected event date/time as milliseconds.
    private long selectedDateTimeMillis = 0;

    // Used to determine whether the fragment is adding a new event or editing an existing one.
    private boolean isEditMode = false;
    private int eventId = -1;

    public AddEditEventFragment() {
        super(R.layout.fragment_add_edit_event);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Bind UI elements from the layout.
        editTitle = view.findViewById(R.id.editTitle);
        editLocation = view.findViewById(R.id.editLocation);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        textSelectedDateTime = view.findViewById(R.id.textSelectedDateTime);
        btnPickDateTime = view.findViewById(R.id.btnPickDateTime);
        btnSaveEvent = view.findViewById(R.id.btnSaveEvent);

        // Get the shared ViewModel instance from the parent activity.
        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);

        // Set up category spinner values.
        String[] categories = {"Work", "Social", "Travel"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // Check whether this screen was opened for editing an existing event.
        checkEditMode();

        // Open date/time picker when the user taps the button.
        btnPickDateTime.setOnClickListener(v -> showDateTimePicker());

        // Save a new event or update an existing event.
        btnSaveEvent.setOnClickListener(v -> saveOrUpdateEvent(view));
    }

    private void checkEditMode() {
        Bundle args = getArguments();
        // If arguments contain an event ID, this fragment is in edit mode.
        if (args != null && args.containsKey("eventId")) {
            isEditMode = true;
            eventId = args.getInt("eventId");

            // Load existing event values into the form.
            String title = args.getString("title", "");
            String category = args.getString("category", "Work");
            String location = args.getString("location", "");
            long dateTime = args.getLong("dateTime", 0);

            editTitle.setText(title);
            editLocation.setText(location);
            selectedDateTimeMillis = dateTime;

            if (dateTime != 0) {
                textSelectedDateTime.setText(DateTimeUtils.formatDateTime(dateTime));
            }

            // Pre-select the correct category in the spinner.
            if (category.equals("Work")) {
                spinnerCategory.setSelection(0);
            } else if (category.equals("Social")) {
                spinnerCategory.setSelection(1);
            } else if (category.equals("Travel")) {
                spinnerCategory.setSelection(2);
            }

            // Change button text so the user knows this is an update action.
            btnSaveEvent.setText("Update Event");
        }
    }

    private void showDateTimePicker() {
        Calendar calendar = Calendar.getInstance();

        // If editing, preload the existing event date/time.
        if (selectedDateTimeMillis != 0) {
            calendar.setTimeInMillis(selectedDateTimeMillis);
        }

        // Show DatePicker first.
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (dateView, year, month, dayOfMonth) -> {
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(Calendar.YEAR, year);
                    selectedCalendar.set(Calendar.MONTH, month);
                    selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    // After date selection, show TimePicker.
                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            requireContext(),
                            (timeView, hourOfDay, minute) -> {
                                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                selectedCalendar.set(Calendar.MINUTE, minute);
                                selectedCalendar.set(Calendar.SECOND, 0);
                                selectedCalendar.set(Calendar.MILLISECOND, 0);

                                // Save selected date/time as milliseconds and display it to the user.
                                selectedDateTimeMillis = selectedCalendar.getTimeInMillis();
                                textSelectedDateTime.setText(
                                        DateTimeUtils.formatDateTime(selectedDateTimeMillis)
                                );
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                    );

                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void saveOrUpdateEvent(View view) {
        String title = editTitle.getText().toString().trim();
        String location = editLocation.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();

        // Validation: title must not be empty.
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(requireContext(), "Title is required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validation: a date/time must be selected.
        if (selectedDateTimeMillis == 0) {
            Toast.makeText(requireContext(), "Please select date and time", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validation: new events cannot be created in the past.
        if (!isEditMode && selectedDateTimeMillis < System.currentTimeMillis()) {
            Toast.makeText(requireContext(), "Past dates are not allowed", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isEditMode) {
            // Update existing event in Room database.
            Event event = new Event(title, category, location, selectedDateTimeMillis);
            event.setId(eventId);
            eventViewModel.update(event);
            Toast.makeText(requireContext(), "Event updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Insert new event into Room database.
            Event event = new Event(title, category, location, selectedDateTimeMillis);
            eventViewModel.insert(event);
            Toast.makeText(requireContext(), "Event saved successfully", Toast.LENGTH_SHORT).show();
        }

        // Reset form after save/update.
        clearForm();

        // Navigate back to the event list screen.
        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.eventListFragment);
    }

    private void clearForm() {
        // Reset all form values to default state.
        editTitle.setText("");
        editLocation.setText("");
        spinnerCategory.setSelection(0);
        selectedDateTimeMillis = 0;
        textSelectedDateTime.setText("No date selected");
        btnSaveEvent.setText("Save Event");
        isEditMode = false;
        eventId = -1;
    }
}