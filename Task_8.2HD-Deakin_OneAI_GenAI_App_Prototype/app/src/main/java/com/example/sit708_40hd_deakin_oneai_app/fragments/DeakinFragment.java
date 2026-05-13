package com.example.sit708_40hd_deakin_oneai_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sit708_40hd_deakin_oneai_app.R;

/**
 * Deakin Hub.
 *
 * Features:
 * - Opens Deakin services inside the app
 * - Browser workspace
 * - GitHub workspace
 * - Local study files module
 *
 * Security Note:
 * This prototype demonstrates integrated academic access.
 * Users are warned not to enter sensitive credentials
 * inside embedded WebViews in production systems.
 */
public class DeakinFragment extends Fragment {

    public DeakinFragment() {
        // Required empty constructor.
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_deakin, container, false);

        // =========================
        // Resource cards
        // =========================

        LinearLayout cardCloudDeakin =
                view.findViewById(R.id.cardCloudDeakin);

        LinearLayout cardOnTrack =
                view.findViewById(R.id.cardOnTrack);

        LinearLayout cardStudentConnect =
                view.findViewById(R.id.cardStudentConnect);

        LinearLayout cardDeakinSync =
                view.findViewById(R.id.cardDeakinSync);

        LinearLayout cardStarTimetable =
                view.findViewById(R.id.cardStarTimetable);

        LinearLayout cardGithub =
                view.findViewById(R.id.cardGithub);

        LinearLayout cardBrowser =
                view.findViewById(R.id.cardBrowser);

        LinearLayout cardFiles =
                view.findViewById(R.id.cardFiles);

        LinearLayout cardCalendar =
                view.findViewById(R.id.cardCalendar);

        // =========================
        // Deakin resources
        // =========================

        cardCloudDeakin.setOnClickListener(v ->
                openInAppBrowser(
                        "CloudDeakin",
                        "https://d2l.deakin.edu.au/"
                )
        );

        cardOnTrack.setOnClickListener(v ->
                openInAppBrowser(
                        "OnTrack",
                        "https://ontrack.deakin.edu.au/"
                )
        );

        cardStudentConnect.setOnClickListener(v ->
                openInAppBrowser(
                        "StudentConnect",
                        "https://studentconnect.deakin.edu.au/"
                )
        );

        cardDeakinSync.setOnClickListener(v ->
                openInAppBrowser(
                        "DeakinSync",
                        "https://sync.deakin.edu.au/"
                )
        );

        cardStarTimetable.setOnClickListener(v ->
                openInAppBrowser(
                        "STAR Timetable",
                        "https://www.deakin.edu.au/students/study-support/study-timetables"
                )
        );

        // =========================
        // Developer workspace
        // =========================

        cardGithub.setOnClickListener(v ->
                openInAppBrowser(
                        "GitHub Workspace",
                        "https://github.com/ashanindika3737"
                )
        );

        cardBrowser.setOnClickListener(v ->
                openInAppBrowser(
                        "Browser Workspace",
                        "https://www.google.com/search?q=Android+Gemini+API+student+productivity+app"
                )
        );

        // =========================
        // Productivity Tools
        // =========================

        cardCalendar.setOnClickListener(v ->
                openInAppFragment(new CalendarFragment())
        );

        // =========================
        // Local files section
        // =========================

        cardFiles.setOnClickListener(v ->
                openInAppFragment(new FilesFragment())
        );

        return view;
    }

    /**
     * Opens BrowserFragment inside the app.
     */
    private void openInAppBrowser(String title, String url) {

        BrowserFragment fragment =
                BrowserFragment.newInstance(title, url);

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Opens a normal fragment inside the app.
     */
    private void openInAppFragment(Fragment fragment) {

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}