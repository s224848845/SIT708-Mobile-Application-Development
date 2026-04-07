package com.example.sit708_40hd_deakin_oneai_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sit708_40hd_deakin_oneai_app.R;

/**
 * DeakinFragment is used to load different Deakin services in a WebView.
 * The requested portal name is passed through fragment arguments.
 */
public class DeakinFragment extends Fragment {

    private static final String ARG_PORTAL = "portal";

    public DeakinFragment() {
        // Required empty constructor
    }

    public static DeakinFragment newInstance(String portal) {
        DeakinFragment fragment = new DeakinFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PORTAL, portal);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deakin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WebView webView = view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        String portal = getArguments() != null ? getArguments().getString(ARG_PORTAL, "clouddeakin") : "clouddeakin";
        webView.loadUrl(getPortalUrl(portal));
    }

    /**
     * Returns the relevant URL for each Deakin portal.
     */
    private String getPortalUrl(String portal) {
        switch (portal) {
            case "deakinsync":
                return "https://sync.deakin.edu.au/home";
            case "studentconnect":
                return "https://www.deakin.edu.au/students/help/it-support-and-systems/studentconnect";
            case "ontrack":
                return "https://ontrack.deakin.edu.au/home";
            case "timetable":
                return "https://www.deakin.edu.au/students/study-support/study-timetables";
            case "clouddeakin":
            default:
                return "https://d2l.deakin.edu.au/d2l/home";
        }
    }
}