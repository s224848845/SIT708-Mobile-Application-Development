package com.example.sit708_40hd_deakin_oneai_app.utils;

/**
 * Utility class for reusable prompt-related helper methods.
 * This separates prompt validation from UI logic and supports future extension,
 * such as adding prompt templates, local caching, and prompt history.
 */
public class PromptUtils {

    /**
     * Returns true when the prompt is empty or only whitespace.
     */
    public static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }

    /**
     * Cleans the user input before sending it to the AI service.
     */
    public static String cleanPrompt(String text) {
        if (text == null) {
            return "";
        }

        return text.trim();
    }

    /**
     * Basic warning used in the UI and report to discourage sensitive input.
     */
    public static String privacyWarning() {
        return "Do not enter passwords, student ID numbers, private documents, medical details, or sensitive personal data.";
    }
}