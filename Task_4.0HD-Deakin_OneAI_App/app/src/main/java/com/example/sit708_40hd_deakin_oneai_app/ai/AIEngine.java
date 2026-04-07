package com.example.sit708_40hd_deakin_oneai_app.ai;

/**
 * AIEngine handles all AI-related processing for the prototype.
 * Current stage:
 * - Uses simulated rule-based responses
 * - Demonstrates intended AI interaction flow
 * Future stage:
 * - Integrate Llama 3.2 on-device inference here
 */
public class AIEngine {

    public AIEngine() {
        // Default constructor
    }

    /**
     * Generates a prototype AI response based on user input.
     * The current implementation is keyword-based to simulate how a future on-device LLM assistant may behave.
     * @param input user question or request
     * @return AI-style response
     */
    public String generateResponse(String input) {

        if (input == null || input.trim().isEmpty()) {
            return "Please enter a valid question for OneAI.";
        }

        String text = input.toLowerCase().trim();

        // Summary-related responses
        if (text.contains("summary") || text.contains("summarise") || text.contains("summarize")) {
            return "Summary:\n"
                    + "This request appears to be asking for a summary of academic content. "
                    + "In the current prototype, OneAI provides simulated responses. "
                    + "In the full version, on-device Llama integration will generate more detailed "
                    + "and context-aware summaries of lecture notes, PDFs, and screenshots.";
        }

        // Study plan / scheduling responses
        if (text.contains("study plan") || text.contains("plan my study") || text.contains("study")) {
            return "Suggested Study Plan:\n"
                    + "1. Review this week’s lecture materials.\n"
                    + "2. Complete any pending coding or reading tasks.\n"
                    + "3. Revise key concepts for at least 30 minutes.\n"
                    + "4. Check timetable and OnTrack for upcoming deadlines.\n"
                    + "5. Use GitHub and saved notes for practice and revision.";
        }

        // Java / debugging / coding help
        if (text.contains("java") || text.contains("error") || text.contains("bug") || text.contains("debug")) {
            return "Java Debugging Support:\n"
                    + "• Check for syntax issues such as missing brackets or semicolons.\n"
                    + "• Confirm variable names and method calls are correct.\n"
                    + "• Review Logcat or compiler output for the exact error message.\n"
                    + "• Test one section of code at a time to isolate the issue.\n"
                    + "A future Llama-based version could provide deeper code explanations.";
        }

        // GitHub-related responses
        if (text.contains("github") || text.contains("repository") || text.contains("repo") || text.contains("code")) {
            return "GitHub Support:\n"
                    + "The GitHub tab can be used to access repositories, review project files, "
                    + "and manage coding work related to your subjects. "
                    + "In a future version, the AI assistant could help explain repository structure, "
                    + "summarise README files, and provide coding guidance.";
        }

        // Deakin-related responses
        if (text.contains("deakin") || text.contains("clouddeakin") || text.contains("ontrack")
                || text.contains("studentconnect") || text.contains("timetable")) {
            return "Deakin Academic Support:\n"
                    + "Deakin OneAI is designed to unify access to CloudDeakin, DeakinSync, "
                    + "StudentConnect, OnTrack, timetable resources, and other student tools "
                    + "within one mobile application. The AI component is intended to support "
                    + "navigation, planning, and academic assistance.";
        }

        // File-related responses
        if (text.contains("file") || text.contains("pdf") || text.contains("document") || text.contains("notes")) {
            return "File Assistance:\n"
                    + "The My Files area is intended to help store and organise academic resources "
                    + "such as notes, lecture slides, and assignment documents. "
                    + "A future AI-enhanced version could summarise files, organise them by subject, "
                    + "and provide quick revision support.";
        }

        // Default fallback response
        return "OneAI Prototype Response:\n"
                + "I received your request: \"" + input + "\".\n"
                + "The current prototype provides simulated AI behaviour to demonstrate the "
                + "interaction flow. Full on-device Llama 3.2 integration is planned as a future enhancement.";
    }
}