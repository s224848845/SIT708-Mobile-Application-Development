package com.example.sit708_40hd_deakin_oneai_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.sit708_40hd_deakin_oneai_app.R;

/**
 * ScheduleFragment shows planning-related actions such as timetable,
 * calendar, and OnTrack access.
 */
public class ScheduleFragment extends Fragment {

    public ScheduleFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        Button btnOpenTimetable = view.findViewById(R.id.btnOpenTimetable);
        Button btnOpenCalendar = view.findViewById(R.id.btnOpenCalendar);
        Button btnOpenOnTrack = view.findViewById(R.id.btnOpenOnTrack);

        btnOpenTimetable.setOnClickListener(v ->
                openFragment(DeakinFragment.newInstance("timetable")));

        btnOpenCalendar.setOnClickListener(v ->
                openFragment(DeakinFragment.newInstance("calendar")));

        btnOpenOnTrack.setOnClickListener(v ->
                openFragment(DeakinFragment.newInstance("ontrack")));

        return view;
    }

    private void openFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}