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
import com.example.sit708_40hd_deakin_oneai_app.ai.GeminiService;

import java.util.Calendar;

/**
 * ScheduleFragment
 *
 * Academic study planner for Deakin OneAI.
 *
 * Features:
 * - Native Android DatePicker
 * - Structured local study-plan generation
 * - Gemini AI study-plan generation
 * - Productivity-focused academic workflow
 * - Local plan works even without internet/API key
 * - AI plan provides more personalised Gemini-generated guidance
 */
public class ScheduleFragment extends Fragment {

    // User input fields
    private EditText inputUnitName;
    private EditText inputTaskName;

    // Due-date display field
    private TextView txtSelectedDate;

    // Output area for generated plan
    private TextView txtGeneratedPlan;

    // Gemini service for AI-generated study plans
    private GeminiService geminiService;

    // Stores selected due date
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

        // =========================
        // Bind UI components
        // =========================
        inputUnitName = view.findViewById(R.id.inputUnitName);
        inputTaskName = view.findViewById(R.id.inputTaskName);
        txtSelectedDate = view.findViewById(R.id.txtSelectedDate);
        txtGeneratedPlan = view.findViewById(R.id.txtGeneratedPlan);

        Button btnGeneratePlan = view.findViewById(R.id.btnGeneratePlan);
        Button btnGenerateAIPlan = view.findViewById(R.id.btnGenerateAIPlan);

        // Initialise Gemini service
        geminiService = new GeminiService();

        /*
         * UX improvement:
         * Clicking the Due Date field directly opens the native DatePickerDialog.
         */
        txtSelectedDate.setOnClickListener(v -> showDatePicker());

        /*
         * Local planner:
         * Works offline and does not depend on Gemini API.
         */
        btnGeneratePlan.setOnClickListener(v -> generateLocalPlan());

        /*
         * AI planner:
         * Uses Gemini to generate a more personalised study plan.
         */
        btnGenerateAIPlan.setOnClickListener(v -> generateAIStudyPlan());

        return view;
    }

    /**
     * Opens the native Android DatePickerDialog.
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
     * Generates a structured local study plan.
     *
     * This works without Gemini API and is useful as a fallback.
     */
    private void generateLocalPlan() {

        String unitName = inputUnitName.getText().toString().trim();
        String taskName = inputTaskName.getText().toString().trim();

        if (!validateInputs(unitName, taskName)) {
            return;
        }

        String plan =
                "Structured Local Study Plan\n\n"
                        + "Unit: " + unitName + "\n"
                        + "Assessment: " + taskName + "\n"
                        + "Due Date: " + selectedDueDate + "\n\n"

                        + "PHASE 1 — REQUIREMENT ANALYSIS\n"
                        + "• Review the task sheet carefully.\n"
                        + "• Highlight HD-level marking criteria.\n"
                        + "• Identify required screenshots, reports, references, and demonstrations.\n\n"

                        + "PHASE 2 — DESIGN & UI IMPROVEMENT\n"
                        + "• Finalise premium mobile UI design.\n"
                        + "• Verify bottom navigation consistency.\n"
                        + "• Ensure responsive spacing and clean Android layouts.\n"
                        + "• Improve accessibility and readability.\n\n"

                        + "PHASE 3 — FEATURE IMPLEMENTATION\n"
                        + "• Test Gemini AI functionality using realistic prompts.\n"
                        + "• Verify browser workspace, calendar, planner, and Deakin Hub modules.\n"
                        + "• Validate WebView navigation and internal fragment transitions.\n"
                        + "• Check all buttons, tabs, and productivity tools.\n\n"

                        + "PHASE 4 — TESTING & VALIDATION\n"
                        + "• Test the application on API 35 and API 36.\n"
                        + "• Validate emulator and physical-device compatibility.\n"
                        + "• Review crashes, layout issues, and navigation behaviour.\n"
                        + "• Confirm all generated responses display correctly.\n\n"

                        + "PHASE 5 — DOCUMENTATION\n"
                        + "• Update README.md with setup instructions.\n"
                        + "• Document Gemini API configuration and safety handling.\n"
                        + "• Add architecture explanation and future improvements.\n"
                        + "• Prepare screenshots for submission evidence.\n\n"

                        + "FINAL CHECKLIST\n"
                        + "✔ Local planner working\n"
                        + "✔ AI planner tested\n"
                        + "✔ Calendar events functional\n"
                        + "✔ Internal navigation stable\n"
                        + "✔ README completed\n"
                        + "✔ Demo video prepared";

        txtGeneratedPlan.setText(plan);
    }

    /**
     * Generates a Gemini AI-based study plan.
     *
     * This gives a more personalised response based on the unit,
     * task name, and selected due date.
     */
    private void generateAIStudyPlan() {

        String unitName = inputUnitName.getText().toString().trim();
        String taskName = inputTaskName.getText().toString().trim();

        if (!validateInputs(unitName, taskName)) {
            return;
        }

        txtGeneratedPlan.setText("Generating AI study plan using Gemini. Please wait...");

        String prompt =
                "Create a detailed university study plan for the following task.\n\n"
                        + "Unit: " + unitName + "\n"
                        + "Assessment / Task: " + taskName + "\n"
                        + "Due Date: " + selectedDueDate + "\n\n"
                        + "The plan should include:\n"
                        + "1. Requirement analysis\n"
                        + "2. Weekly or phased milestones\n"
                        + "3. Implementation tasks\n"
                        + "4. Testing and evidence preparation\n"
                        + "5. README and documentation tasks\n"
                        + "6. Demo video preparation\n"
                        + "7. Final submission checklist\n\n"
                        + "Write the response in a clear, student-friendly format suitable for an Android app screen.";

        geminiService.generateLearningResponse(
                "PLAN",
                prompt,
                new GeminiService.GeminiCallback() {
                    @Override
                    public void onSuccess(String result) {
                        txtGeneratedPlan.setText(cleanGeminiFormatting(result));
                    }

                    @Override
                    public void onError(String error) {
                        txtGeneratedPlan.setText(
                                "AI plan could not be generated.\n\n"
                                        + "Reason:\n"
                                        + error
                                        + "\n\nYou can still use the local study plan option."
                        );
                    }
                }
        );
    }

    /**
     * Validates required planner inputs.
     */
    private boolean validateInputs(String unitName, String taskName) {

        if (TextUtils.isEmpty(unitName)
                || TextUtils.isEmpty(taskName)
                || TextUtils.isEmpty(selectedDueDate)) {

            txtGeneratedPlan.setText(
                    "Please enter the unit name, task name, and select a due date."
            );

            return false;
        }

        return true;
    }

    /**
     * Makes Gemini output easier to read in a TextView.
     */
    private String cleanGeminiFormatting(String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace("###", "\n")
                .replace("##", "\n")
                .replace("**", "")
                .replace("* ", "• ")
                .replace("\n\n\n", "\n\n")
                .trim();
    }
}