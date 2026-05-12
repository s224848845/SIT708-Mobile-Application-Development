package com.example.sit708_40hd_deakin_oneai_app.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sit708_40hd_deakin_oneai_app.R;

import java.util.Calendar;

/**
 * Study planner with DatePickerDialog.
 * Produces a structured study plan for assessment preparation.
 */
public class ScheduleFragment extends Fragment {

    private EditText inputUnitName;
    private EditText inputTaskName;
    private TextView txtSelectedDate;
    private TextView txtGeneratedPlan;

    private String selectedDueDate = "";

    public ScheduleFragment() {
        // Required empty public constructor.
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        inputUnitName = view.findViewById(R.id.inputUnitName);
        inputTaskName = view.findViewById(R.id.inputTaskName);
        txtSelectedDate = view.findViewById(R.id.txtSelectedDate);
        txtGeneratedPlan = view.findViewById(R.id.txtGeneratedPlan);

        Button btnPickDate = view.findViewById(R.id.btnPickDate);
        Button btnGeneratePlan = view.findViewById(R.id.btnGeneratePlan);

        btnPickDate.setOnClickListener(v -> showDatePicker());
        txtSelectedDate.setOnClickListener(v -> showDatePicker());

        btnGeneratePlan.setOnClickListener(v -> generateLocalPlan());

        return view;
    }

    /**
     * Opens a native Android DatePickerDialog.
     */
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    int displayMonth = selectedMonth + 1;

                    selectedDueDate = selectedDay + "/" + displayMonth + "/" + selectedYear;

                    txtSelectedDate.setText(selectedDueDate);
                    txtSelectedDate.setTextColor(getResources().getColor(R.color.text_primary));
                },
                year,
                month,
                day
        );

        dialog.show();
    }

    /**
     * Generates a local study plan based on user inputs.
     */
    private void generateLocalPlan() {
        String unitName = inputUnitName.getText().toString().trim();
        String taskName = inputTaskName.getText().toString().trim();

        if (TextUtils.isEmpty(unitName) || TextUtils.isEmpty(taskName) || TextUtils.isEmpty(selectedDueDate)) {
            txtGeneratedPlan.setText("Please enter unit name, task name, and select a due date.");
            return;
        }

        String plan =
                "Study Plan for " + unitName + "\n\n"
                        + "Task: " + taskName + "\n"
                        + "Due Date: " + selectedDueDate + "\n\n"
                        + "1. Review the task sheet and identify all HD requirements.\n"
                        + "2. Finalise UI improvements and ensure all navigation tabs work.\n"
                        + "3. Test Gemini AI features using realistic academic prompts.\n"
                        + "4. Capture screenshots on API 35 and API 36 emulator/device.\n"
                        + "5. Prepare README with setup, SDK, privacy, and AI integration notes.\n"
                        + "6. Record the demo showing planner, Deakin Hub, and OneAI Studio.\n"
                        + "7. Review the final Task 8.2 folder before submitting to OnTrack.";

        txtGeneratedPlan.setText(plan);
    }
}