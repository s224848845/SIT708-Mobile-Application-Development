package com.example.sit708_40hd_deakin_oneai_app.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sit708_40hd_deakin_oneai_app.R;
import com.example.sit708_40hd_deakin_oneai_app.ai.GeminiService;

/**
 * OneAI Studio
 *
 * Modernised GenAI interface using:
 * - Prompt workspace
 * - AI tool selector
 * - Single generate action
 * - Improved response card
 * - Persistent selected-tool highlighting
 *
 * Industry-inspired UX:
 * Similar interaction style to Microsoft Copilot,
 * Gemini mobile apps, and ChatGPT mobile interfaces.
 */
public class AIFragment extends Fragment {

    private EditText inputPrompt;
    private TextView outputResponse;
    private TextView txtResponseTitle;
    private TextView txtResponseMeta;
    private ProgressBar progressGemini;
    private ScrollView aiRootScroll;

    private GeminiService geminiService;

    private String latestResponse = "";

    /*
     * Currently selected AI feature.
     * ASK is selected by default when the screen opens.
     */
    private String selectedFeature = "ASK";
    private String selectedTitle = "General Answer";

    /*
     * Tool selector buttons.
     * Stored as fields so their styles can be reset/highlighted.
     */
    private Button btnAsk;
    private Button btnSummarise;
    private Button btnPlan;
    private Button btnChecklist;
    private Button btnExplain;
    private Button btnFlashcards;

    public AIFragment() {
        // Required empty public constructor.
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ai, container, false);

        geminiService = new GeminiService();

        aiRootScroll = view.findViewById(R.id.aiRootScroll);
        inputPrompt = view.findViewById(R.id.inputPrompt);

        outputResponse = view.findViewById(R.id.outputResponse);
        txtResponseTitle = view.findViewById(R.id.txtResponseTitle);
        txtResponseMeta = view.findViewById(R.id.txtResponseMeta);

        progressGemini = view.findViewById(R.id.progressGemini);

        /*
         * AI tool selector buttons.
         */
        btnAsk = view.findViewById(R.id.btnAsk);
        btnSummarise = view.findViewById(R.id.btnSummarise);
        btnPlan = view.findViewById(R.id.btnPlan);
        btnChecklist = view.findViewById(R.id.btnChecklist);
        btnExplain = view.findViewById(R.id.btnExplain);
        btnFlashcards = view.findViewById(R.id.btnFlashcards);

        /*
         * Main generation button.
         */
        Button btnGenerateAI = view.findViewById(R.id.btnGenerateAI);

        Button btnCopyResponse = view.findViewById(R.id.btnCopyResponse);
        Button btnClearResponse = view.findViewById(R.id.btnClearResponse);

        outputResponse.setMovementMethod(new ScrollingMovementMethod());

        /*
         * AI feature selections.
         * The selected button remains visually highlighted.
         */
        btnAsk.setOnClickListener(v -> selectFeature("ASK", "General Answer", btnAsk));
        btnSummarise.setOnClickListener(v -> selectFeature("SUMMARY", "Lesson Summary", btnSummarise));
        btnPlan.setOnClickListener(v -> selectFeature("PLAN", "Study Plan", btnPlan));
        btnChecklist.setOnClickListener(v -> selectFeature("CHECKLIST", "Assignment Checklist", btnChecklist));
        btnExplain.setOnClickListener(v -> selectFeature("EXPLAIN", "Concept Explanation", btnExplain));
        btnFlashcards.setOnClickListener(v -> selectFeature("FLASHCARDS", "Revision Flashcards", btnFlashcards));

        /*
         * Default tool selection when opening the OneAI screen.
         */
        selectFeature("ASK", "General Answer", btnAsk);

        /*
         * Main generate action.
         */
        btnGenerateAI.setOnClickListener(v -> runGemini());

        btnCopyResponse.setOnClickListener(v -> copyResponse());
        btnClearResponse.setOnClickListener(v -> clearResponse());

        return view;
    }

    /**
     * Stores selected AI feature and updates the selected-tool UI state.
     *
     * Default buttons stay blue, while the active tool becomes dark navy
     * so users can clearly see which AI mode is selected.
     */
    private void selectFeature(String featureType, String title, Button selectedButton) {

        selectedFeature = featureType;
        selectedTitle = title;

        resetToolButtonStyles();

        selectedButton.setBackgroundColor(Color.parseColor("#0F172A"));
        selectedButton.setTextColor(Color.WHITE);

        txtResponseTitle.setText(title);
        txtResponseMeta.setText("Selected tool: " + title);
    }

    /**
     * Resets all tool selector buttons to the default blue state.
     */
    private void resetToolButtonStyles() {
        int defaultBlue = Color.parseColor("#2563EB");

        Button[] buttons = {
                btnAsk,
                btnSummarise,
                btnPlan,
                btnChecklist,
                btnExplain,
                btnFlashcards
        };

        for (Button button : buttons) {
            button.setBackgroundColor(defaultBlue);
            button.setTextColor(Color.WHITE);
        }
    }

    /**
     * Sends the user prompt to Gemini using the currently selected AI tool.
     */
    private void runGemini() {

        String prompt = inputPrompt.getText().toString().trim();

        if (TextUtils.isEmpty(prompt)) {
            Toast.makeText(
                    requireContext(),
                    "Please enter a prompt first.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        progressGemini.setVisibility(View.VISIBLE);

        txtResponseTitle.setText(selectedTitle);
        txtResponseMeta.setText("OneAI is generating a response...");
        outputResponse.setText("Generating academic response...");

        scrollToResponse();

        geminiService.generateLearningResponse(
                selectedFeature,
                prompt,
                new GeminiService.GeminiCallback() {

                    @Override
                    public void onSuccess(String result) {

                        progressGemini.setVisibility(View.GONE);

                        latestResponse = cleanGeminiFormatting(result);

                        txtResponseTitle.setText(selectedTitle);
                        txtResponseMeta.setText(
                                "Generated using Gemini AI academic assistance."
                        );

                        outputResponse.setText(latestResponse);

                        scrollToResponse();
                    }

                    @Override
                    public void onError(String error) {

                        progressGemini.setVisibility(View.GONE);

                        latestResponse = error;

                        txtResponseTitle.setText("Generation Error");
                        txtResponseMeta.setText(
                                "Check API key, internet connection or Gemini availability."
                        );

                        outputResponse.setText(error);

                        scrollToResponse();
                    }
                });
    }

    /**
     * Cleans markdown formatting from Gemini responses to make the
     * result easier to read inside a normal Android TextView.
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

    /**
     * Copies the latest AI response to the Android clipboard.
     */
    private void copyResponse() {

        if (TextUtils.isEmpty(latestResponse)) {

            Toast.makeText(
                    requireContext(),
                    "No response to copy.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        ClipboardManager clipboardManager =
                (ClipboardManager) requireContext()
                        .getSystemService(Context.CLIPBOARD_SERVICE);

        ClipData clipData =
                ClipData.newPlainText("OneAI Response", latestResponse);

        clipboardManager.setPrimaryClip(clipData);

        Toast.makeText(
                requireContext(),
                "Response copied.",
                Toast.LENGTH_SHORT
        ).show();
    }

    /**
     * Clears current response card and resets the visible response text.
     * The selected AI tool remains selected.
     */
    private void clearResponse() {

        latestResponse = "";

        txtResponseTitle.setText(selectedTitle);
        txtResponseMeta.setText("Selected tool: " + selectedTitle);

        outputResponse.setText(
                "Start by entering a prompt and selecting an AI tool."
        );
    }

    /**
     * Smoothly scrolls to the response section.
     */
    private void scrollToResponse() {

        aiRootScroll.postDelayed(() ->
                aiRootScroll.smoothScrollTo(
                        0,
                        outputResponse.getTop()
                ), 250);
    }
}