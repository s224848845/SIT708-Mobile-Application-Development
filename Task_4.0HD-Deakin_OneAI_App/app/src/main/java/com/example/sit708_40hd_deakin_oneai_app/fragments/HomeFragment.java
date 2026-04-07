package com.example.sit708_40hd_deakin_oneai_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.sit708_40hd_deakin_oneai_app.R;

/**
 * HomeFragment shows the dashboard cards for quick navigation.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        CardView cardCloudDeakin = view.findViewById(R.id.cardCloudDeakin);
        CardView cardDeakinSync = view.findViewById(R.id.cardDeakinSync);
        CardView cardStudentConnect = view.findViewById(R.id.cardStudentConnect);
        CardView cardOnTrack = view.findViewById(R.id.cardOnTrack);
        CardView cardTimetable = view.findViewById(R.id.cardTimetable);
        CardView cardFiles = view.findViewById(R.id.cardFiles);
        CardView cardSchedule = view.findViewById(R.id.cardSchedule);
        CardView cardGithub = view.findViewById(R.id.cardGithub);
        CardView cardAI = view.findViewById(R.id.cardAI);

        cardCloudDeakin.setOnClickListener(v -> openFragment(DeakinFragment.newInstance("clouddeakin")));
        cardDeakinSync.setOnClickListener(v -> openFragment(DeakinFragment.newInstance("deakinsync")));
        cardStudentConnect.setOnClickListener(v -> openFragment(DeakinFragment.newInstance("studentconnect")));
        cardOnTrack.setOnClickListener(v -> openFragment(DeakinFragment.newInstance("ontrack")));
        cardTimetable.setOnClickListener(v -> openFragment(DeakinFragment.newInstance("timetable")));
        cardFiles.setOnClickListener(v -> openFragment(new FilesFragment()));
        cardSchedule.setOnClickListener(v -> openFragment(new ScheduleFragment()));
        cardGithub.setOnClickListener(v -> openFragment(new GithubFragment()));
        cardAI.setOnClickListener(v -> openFragment(new AIFragment()));

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