package com.example.sit708_40hd_deakin_oneai_app.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.sit708_40hd_deakin_oneai_app.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * CalendarFragment provides a Teams-style local calendar prototype.
 * - Users can add real local events during the demo.
 * - Events are displayed dynamically inside the app.
 * - Sample academic events are preloaded for demonstration.
 *
 * Future production version:
 * - STAR Timetable sync
 * - Google Calendar API
 * - Microsoft Teams / Graph Calendar integration
 * - Reminder notifications
 */
public class CalendarFragment extends Fragment {

    private LinearLayout eventsListContainer;
    private TextView txtCalendarSummary;
    private ScrollView calendarRootScroll;

    private final List<CalendarEvent> events = new ArrayList<>();

    public CalendarFragment() {
        // Required empty constructor.
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarRootScroll = view.findViewById(R.id.calendarRootScroll);
        eventsListContainer = view.findViewById(R.id.eventsListContainer);
        txtCalendarSummary = view.findViewById(R.id.txtCalendarSummary);

        Button btnAddCalendarEvent = view.findViewById(R.id.btnAddCalendarEvent);
        Button btnResetCalendar = view.findViewById(R.id.btnResetCalendar);

        loadSampleEvents();
        renderEvents();

        btnAddCalendarEvent.setOnClickListener(v -> showAddEventDialog());

        btnResetCalendar.setOnClickListener(v -> {
            events.clear();
            loadSampleEvents();
            renderEvents();
            Toast.makeText(requireContext(), "Sample events restored.", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    /**
     * Preloads realistic academic events for this stage.
     */
    private void loadSampleEvents() {
        events.add(new CalendarEvent(
                "Lecture",
                "SIT708 Android Development Lecture",
                "Monday",
                "10:00 AM",
                "12:00 PM",
                "Online / CloudDeakin",
                "Review Android fragments, layouts and UI principles."
        ));

        events.add(new CalendarEvent(
                "Workshop",
                "Mobile App Practical Workshop",
                "Tuesday",
                "2:00 PM",
                "4:00 PM",
                "Burwood / Online",
                "Test Gemini API integration and UI workflow."
        ));

        events.add(new CalendarEvent(
                "Study Block",
                "OneAI Study Block",
                "Wednesday",
                "6:00 PM",
                "7:30 PM",
                "Home Study",
                "Summarise notes and generate assignment checklist."
        ));

        events.add(new CalendarEvent(
                "Assessment",
                "8.2HD Demo Preparation",
                "Friday",
                "4:00 PM",
                "5:00 PM",
                "Home / Teams Recording",
                "Record app walkthrough and API 35/36 testing evidence."
        ));
    }

    /**
     * Opens a form-style dialog to add a new calendar event.
     */
    private void showAddEventDialog() {
        LinearLayout formLayout = new LinearLayout(requireContext());
        formLayout.setOrientation(LinearLayout.VERTICAL);
        formLayout.setPadding(32, 16, 32, 8);

        EditText inputTitle = createInput("Event title, e.g., SIT708 Tutorial");
        EditText inputLocation = createInput("Location, e.g., Burwood / Online");
        EditText inputNotes = createInput("Notes, e.g., prepare screenshots");

        TextView txtDate = createPickerText("Select date");
        TextView txtStartTime = createPickerText("Select start time");
        TextView txtEndTime = createPickerText("Select end time");

        Spinner spinnerType = new Spinner(requireContext());
        String[] types = {"Lecture", "Workshop", "Tutorial", "Study Block", "Assessment", "Meeting"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                types
        );
        spinnerType.setAdapter(adapter);

        final String[] selectedDate = {""};
        final String[] selectedStartTime = {""};
        final String[] selectedEndTime = {""};

        txtDate.setOnClickListener(v -> showDatePicker(txtDate, selectedDate));
        txtStartTime.setOnClickListener(v -> showTimePicker(txtStartTime, selectedStartTime));
        txtEndTime.setOnClickListener(v -> showTimePicker(txtEndTime, selectedEndTime));

        formLayout.addView(spinnerType);
        formLayout.addView(inputTitle);
        formLayout.addView(txtDate);
        formLayout.addView(txtStartTime);
        formLayout.addView(txtEndTime);
        formLayout.addView(inputLocation);
        formLayout.addView(inputNotes);

        new AlertDialog.Builder(requireContext())
                .setTitle("Add Calendar Event")
                .setView(formLayout)
                .setPositiveButton("Add", (dialog, which) -> {
                    String title = inputTitle.getText().toString().trim();
                    String location = inputLocation.getText().toString().trim();
                    String notes = inputNotes.getText().toString().trim();
                    String type = spinnerType.getSelectedItem().toString();

                    if (title.isEmpty()
                            || selectedDate[0].isEmpty()
                            || selectedStartTime[0].isEmpty()
                            || selectedEndTime[0].isEmpty()) {

                        Toast.makeText(
                                requireContext(),
                                "Please enter title, date, start time and end time.",
                                Toast.LENGTH_LONG
                        ).show();
                        return;
                    }

                    events.add(new CalendarEvent(
                            type,
                            title,
                            selectedDate[0],
                            selectedStartTime[0],
                            selectedEndTime[0],
                            location.isEmpty() ? "Not specified" : location,
                            notes.isEmpty() ? "No notes added." : notes
                    ));

                    renderEvents();

                    calendarRootScroll.postDelayed(() ->
                            calendarRootScroll.smoothScrollTo(0, txtCalendarSummary.getTop()), 250);

                    Toast.makeText(requireContext(), "Event added.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    /**
     * Renders all calendar events dynamically as cards.
     */
    private void renderEvents() {
        eventsListContainer.removeAllViews();

        txtCalendarSummary.setText("Upcoming Events (" + events.size() + ")");

        for (CalendarEvent event : events) {
            eventsListContainer.addView(createEventCard(event));
        }
    }

    /**
     * Creates a single Teams-style event card.
     */
    private View createEventCard(CalendarEvent event) {
        LinearLayout card = new LinearLayout(requireContext());
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setBackgroundResource(R.drawable.bg_resource_card);
        card.setPadding(18, 18, 18, 18);

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(0, 0, 0, 14);
        card.setLayoutParams(cardParams);

        TextView icon = new TextView(requireContext());
        icon.setText(getIconForType(event.type));
        icon.setTextSize(28);
        icon.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(56, 56);
        icon.setLayoutParams(iconParams);

        LinearLayout content = new LinearLayout(requireContext());
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(16, 0, 0, 0);

        LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1
        );
        content.setLayoutParams(contentParams);

        TextView title = new TextView(requireContext());
        title.setText(event.dayOrDate + " • " + event.startTime + " - " + event.endTime);
        title.setTextColor(getResources().getColor(R.color.primary_blue));
        title.setTextSize(16);
        title.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView subtitle = new TextView(requireContext());
        subtitle.setText(event.title + "\n" + event.location + "\n" + event.notes);
        subtitle.setTextColor(getResources().getColor(R.color.text_secondary));
        subtitle.setTextSize(14);
        subtitle.setPadding(0, 6, 0, 0);

        TextView tag = new TextView(requireContext());
        tag.setText(event.type);
        tag.setTextColor(getResources().getColor(R.color.info_text));
        tag.setTextSize(13);
        tag.setPadding(0, 8, 0, 0);

        content.addView(title);
        content.addView(subtitle);
        content.addView(tag);

        card.addView(icon);
        card.addView(content);

        return card;
    }

    private EditText createInput(String hint) {
        EditText input = new EditText(requireContext());
        input.setHint(hint);
        input.setSingleLine(false);
        input.setPadding(16, 16, 16, 16);
        input.setTextSize(14);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 12, 0, 0);
        input.setLayoutParams(params);

        return input;
    }

    private TextView createPickerText(String text) {
        TextView picker = new TextView(requireContext());
        picker.setText(text);
        picker.setTextSize(15);
        picker.setTextColor(getResources().getColor(R.color.primary_blue));
        picker.setPadding(16, 18, 16, 18);
        picker.setBackgroundResource(R.drawable.bg_input);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 12, 0, 0);
        picker.setLayoutParams(params);

        return picker;
    }

    private void showDatePicker(TextView target, String[] selectedDate) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                    selectedDate[0] = date;
                    target.setText(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

    private void showTimePicker(TextView target, String[] selectedTime) {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog dialog = new TimePickerDialog(
                requireContext(),
                (view, hourOfDay, minute) -> {
                    String amPm = hourOfDay >= 12 ? "PM" : "AM";
                    int hour = hourOfDay % 12;
                    if (hour == 0) {
                        hour = 12;
                    }

                    String time = String.format("%02d:%02d %s", hour, minute, amPm);
                    selectedTime[0] = time;
                    target.setText(time);
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
        );

        dialog.show();
    }

    private String getIconForType(String type) {
        switch (type) {
            case "Lecture":
                return "📘";
            case "Workshop":
                return "💻";
            case "Tutorial":
                return "👨‍🏫";
            case "Study Block":
                return "🧠";
            case "Assessment":
                return "🎥";
            case "Meeting":
                return "👥";
            default:
                return "📆";
        }
    }

    /**
     * Local event model used only inside CalendarFragment.
     */
    private static class CalendarEvent {
        String type;
        String title;
        String dayOrDate;
        String startTime;
        String endTime;
        String location;
        String notes;

        CalendarEvent(String type,
                      String title,
                      String dayOrDate,
                      String startTime,
                      String endTime,
                      String location,
                      String notes) {
            this.type = type;
            this.title = title;
            this.dayOrDate = dayOrDate;
            this.startTime = startTime;
            this.endTime = endTime;
            this.location = location;
            this.notes = notes;
        }
    }
}