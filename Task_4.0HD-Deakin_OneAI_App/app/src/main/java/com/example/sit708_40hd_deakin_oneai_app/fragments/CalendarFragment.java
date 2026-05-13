package com.example.sit708_40hd_deakin_oneai_app.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.sit708_40hd_deakin_oneai_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * CalendarFragment provides a Teams-style local calendar prototype.
 *
 * Features:
 * - Add local calendar events
 * - Use quick templates for common academic activities
 * - Select event type using a dropdown
 * - Add reminder and online/Teams options
 * - Save user-added events using SharedPreferences
 * - Display events dynamically as Teams-style cards
 *
 * Future production version:
 * - STAR Timetable sync
 * - Google Calendar API
 * - Microsoft Teams / Graph Calendar integration
 * - Reminder notifications
 */
public class CalendarFragment extends Fragment {

    private static final String PREF_NAME = "calendar_events_pref";
    private static final String KEY_EVENTS = "saved_events";

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

        /*
         * Load saved events first.
         * If nothing exists, load the default academic sample events once.
         */
        loadEventsFromStorage();

        if (events.isEmpty()) {
            loadSampleEvents();
            saveEventsToStorage();
        }

        renderEvents();

        btnAddCalendarEvent.setOnClickListener(v -> showAddEventDialog());

        /*
         * Reset restores the original sample calendar and saves it locally.
         */
        btnResetCalendar.setOnClickListener(v -> {
            events.clear();
            loadSampleEvents();
            saveEventsToStorage();
            renderEvents();

            Toast.makeText(
                    requireContext(),
                    "Sample events restored.",
                    Toast.LENGTH_SHORT
            ).show();
        });

        return view;
    }

    /**
     * Preloads realistic academic events for demonstration.
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
     * Opens a user-friendly calendar event dialog.
     * - Event type dropdown
     * - Quick template dropdown
     * - Native date and time pickers
     * - Reminder checkbox
     * - Online/Teams checkbox
     */
    private void showAddEventDialog() {

        LinearLayout formLayout = new LinearLayout(requireContext());
        formLayout.setOrientation(LinearLayout.VERTICAL);
        formLayout.setPadding(32, 16, 32, 8);

        TextView lblType = createDialogLabel("Event type");

        Spinner spinnerType = new Spinner(requireContext());

        String[] types = {
                "Lecture",
                "Workshop",
                "Tutorial",
                "Study Block",
                "Assessment",
                "Meeting"
        };

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                types
        );

        spinnerType.setAdapter(typeAdapter);

        TextView lblTemplate = createDialogLabel("Quick template");

        Spinner spinnerTemplate = new Spinner(requireContext());

        String[] templates = {
                "Custom event",
                "SIT708 lecture",
                "Mobile app workshop",
                "Assessment preparation",
                "Gemini AI testing",
                "Demo recording session"
        };

        ArrayAdapter<String> templateAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                templates
        );

        spinnerTemplate.setAdapter(templateAdapter);

        EditText inputTitle = createInput("Event title, e.g., SIT708 Tutorial");
        EditText inputLocation = createInput("Location, e.g., Burwood / Online");
        EditText inputNotes = createInput("Notes, e.g., prepare screenshots");

        TextView txtDate = createPickerText("Select date");
        TextView txtStartTime = createPickerText("Select start time");
        TextView txtEndTime = createPickerText("Select end time");

        CheckBox chkReminder = new CheckBox(requireContext());
        chkReminder.setText("Add reminder note");
        chkReminder.setTextSize(14);

        CheckBox chkOnline = new CheckBox(requireContext());
        chkOnline.setText("Online / Microsoft Teams session");
        chkOnline.setTextSize(14);

        final String[] selectedDate = {""};
        final String[] selectedStartTime = {""};
        final String[] selectedEndTime = {""};

        txtDate.setOnClickListener(v -> showDatePicker(txtDate, selectedDate));
        txtStartTime.setOnClickListener(v -> showTimePicker(txtStartTime, selectedStartTime));
        txtEndTime.setOnClickListener(v -> showTimePicker(txtEndTime, selectedEndTime));

        /*
         * Auto-fill event details based on selected template.
         */
        spinnerTemplate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view,
                                       int position,
                                       long id) {

                String selectedTemplate = templates[position];

                if (selectedTemplate.equals("SIT708 lecture")) {
                    spinnerType.setSelection(0);
                    inputTitle.setText("SIT708 Android Development Lecture");
                    inputLocation.setText("Online / CloudDeakin");
                    inputNotes.setText("Review Android fragments, layouts and UI principles.");
                } else if (selectedTemplate.equals("Mobile app workshop")) {
                    spinnerType.setSelection(1);
                    inputTitle.setText("Mobile App Practical Workshop");
                    inputLocation.setText("Burwood / Online");
                    inputNotes.setText("Test Gemini API integration and UI workflow.");
                } else if (selectedTemplate.equals("Assessment preparation")) {
                    spinnerType.setSelection(4);
                    inputTitle.setText("8.2HD Assessment Preparation");
                    inputLocation.setText("Home Study");
                    inputNotes.setText("Prepare screenshots, README updates and demo video evidence.");
                } else if (selectedTemplate.equals("Gemini AI testing")) {
                    spinnerType.setSelection(3);
                    inputTitle.setText("Gemini AI Testing Session");
                    inputLocation.setText("Home Study");
                    inputNotes.setText("Test prompts for summaries, study plans, checklists and flashcards.");
                } else if (selectedTemplate.equals("Demo recording session")) {
                    spinnerType.setSelection(5);
                    inputTitle.setText("Demo Recording Session");
                    inputLocation.setText("Microsoft Teams / Screen Recording");
                    inputNotes.setText("Record app walkthrough showing Home, OneAI, Planner, Calendar and Hub.");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action required.
            }
        });

        formLayout.addView(lblType);
        formLayout.addView(spinnerType);
        formLayout.addView(lblTemplate);
        formLayout.addView(spinnerTemplate);
        formLayout.addView(inputTitle);
        formLayout.addView(txtDate);
        formLayout.addView(txtStartTime);
        formLayout.addView(txtEndTime);
        formLayout.addView(inputLocation);
        formLayout.addView(inputNotes);
        formLayout.addView(chkReminder);
        formLayout.addView(chkOnline);

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

                    if (chkOnline.isChecked()) {
                        location = location.isEmpty()
                                ? "Online / Microsoft Teams"
                                : location + " / Online option";
                    }

                    if (chkReminder.isChecked()) {
                        notes = notes.isEmpty()
                                ? "Reminder: Review this event before it starts."
                                : notes + "\nReminder: Review this event before it starts.";
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

                    saveEventsToStorage();
                    renderEvents();

                    calendarRootScroll.postDelayed(() ->
                            calendarRootScroll.smoothScrollTo(
                                    0,
                                    txtCalendarSummary.getTop()
                            ), 250);

                    Toast.makeText(
                            requireContext(),
                            "Event added and saved.",
                            Toast.LENGTH_SHORT
                    ).show();
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
        icon.setLayoutParams(new LinearLayout.LayoutParams(56, 56));

        LinearLayout content = new LinearLayout(requireContext());
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(16, 0, 0, 0);

        LinearLayout.LayoutParams contentParams =
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1
                );

        content.setLayoutParams(contentParams);

        TextView title = new TextView(requireContext());
        title.setText(
                event.dayOrDate
                        + " • "
                        + event.startTime
                        + " - "
                        + event.endTime
        );
        title.setTextColor(getResources().getColor(R.color.primary_blue));
        title.setTextSize(16);
        title.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView subtitle = new TextView(requireContext());
        subtitle.setText(
                event.title
                        + "\n"
                        + event.location
                        + "\n"
                        + event.notes
        );
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

    /**
     * Creates a labelled EditText for the add-event dialog.
     */
    private EditText createInput(String hint) {
        EditText input = new EditText(requireContext());

        input.setHint(hint);
        input.setSingleLine(false);
        input.setPadding(16, 16, 16, 16);
        input.setTextSize(14);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

        params.setMargins(0, 12, 0, 0);
        input.setLayoutParams(params);

        return input;
    }

    /**
     * Creates a clickable picker field used for date and time selection.
     */
    private TextView createPickerText(String text) {
        TextView picker = new TextView(requireContext());

        picker.setText(text);
        picker.setTextSize(15);
        picker.setTextColor(getResources().getColor(R.color.primary_blue));
        picker.setPadding(16, 18, 16, 18);
        picker.setBackgroundResource(R.drawable.bg_input);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

        params.setMargins(0, 12, 0, 0);
        picker.setLayoutParams(params);

        return picker;
    }

    /**
     * Creates a small section label for dialog fields.
     */
    private TextView createDialogLabel(String labelText) {
        TextView label = new TextView(requireContext());

        label.setText(labelText);
        label.setTextColor(getResources().getColor(R.color.text_primary));
        label.setTextSize(14);
        label.setTypeface(null, android.graphics.Typeface.BOLD);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

        params.setMargins(0, 12, 0, 4);
        label.setLayoutParams(params);

        return label;
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

                    String time = String.format(
                            "%02d:%02d %s",
                            hour,
                            minute,
                            amPm
                    );

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
     * Saves calendar events locally using SharedPreferences.
     */
    private void saveEventsToStorage() {
        JSONArray jsonArray = new JSONArray();

        try {
            for (CalendarEvent event : events) {
                JSONObject object = new JSONObject();

                object.put("type", event.type);
                object.put("title", event.title);
                object.put("dayOrDate", event.dayOrDate);
                object.put("startTime", event.startTime);
                object.put("endTime", event.endTime);
                object.put("location", event.location);
                object.put("notes", event.notes);

                jsonArray.put(object);
            }

            SharedPreferences preferences = requireContext()
                    .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

            preferences.edit()
                    .putString(KEY_EVENTS, jsonArray.toString())
                    .apply();

        } catch (JSONException e) {
            Toast.makeText(
                    requireContext(),
                    "Unable to save calendar events.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    /**
     * Loads saved calendar events from SharedPreferences.
     */
    private void loadEventsFromStorage() {
        events.clear();

        SharedPreferences preferences = requireContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        String savedData = preferences.getString(KEY_EVENTS, "");

        if (savedData.isEmpty()) {
            return;
        }

        try {
            JSONArray jsonArray = new JSONArray(savedData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                events.add(new CalendarEvent(
                        object.getString("type"),
                        object.getString("title"),
                        object.getString("dayOrDate"),
                        object.getString("startTime"),
                        object.getString("endTime"),
                        object.getString("location"),
                        object.getString("notes")
                ));
            }

        } catch (JSONException e) {
            Toast.makeText(
                    requireContext(),
                    "Unable to load saved calendar events.",
                    Toast.LENGTH_SHORT
            ).show();
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