package com.example.task_31c_quiz_app;

import java.util.ArrayList;
import java.util.List;

/*
 * This class provides the quiz questions.
 * For this task, questions are hardcoded.
 * Later, this could be replaced with JSON, database, or API data.
 */
public class QuizRepository {

    public static List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new Question(
                "What does XML stand for in Android layouts?",
                new String[]{
                        "Extra Markup Language",
                        "Extensible Markup Language",
                        "Example Modern Language",
                        "Executable Markup Language"
                },
                1
        ));

        questions.add(new Question(
                "Which Android component is commonly used to move from one screen to another?",
                new String[]{
                        "Intent",
                        "Toast",
                        "Drawable",
                        "Manifest"
                },
                0
        ));

        questions.add(new Question(
                "Which file is commonly used to define colours in Android?",
                new String[]{
                        "strings.xml",
                        "colors.xml",
                        "AndroidManifest.xml",
                        "build.gradle"
                },
                1
        ));

        questions.add(new Question(
                "Which lifecycle method is called first when an Activity starts?",
                new String[]{
                        "onPause()",
                        "onDestroy()",
                        "onCreate()",
                        "onStop()"
                },
                2
        ));

        questions.add(new Question(
                "Which widget is used to display progress visually?",
                new String[]{
                        "TextView",
                        "ProgressBar",
                        "RecyclerView",
                        "ImageView"
                },
                1
        ));

        return questions;
    }
}