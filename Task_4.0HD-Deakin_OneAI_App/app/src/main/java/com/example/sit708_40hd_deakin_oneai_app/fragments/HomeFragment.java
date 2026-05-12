package com.example.sit708_40hd_deakin_oneai_app.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sit708_40hd_deakin_oneai_app.R;

/**
 * Home dashboard for Deakin OneAI.
 *
 * Includes:
 * - Gemini AI quick actions
 * - Study planner shortcut
 * - Hub shortcut
 * - Embedded Deakin latest news preview
 */
public class HomeFragment extends Fragment {

    private WebView webViewHomeNews;

    public HomeFragment() {
        // Required empty constructor.
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayout cardOpenAI = view.findViewById(R.id.cardOpenAI);
        LinearLayout cardOpenPlanner = view.findViewById(R.id.cardOpenPlanner);
        LinearLayout cardOpenHub = view.findViewById(R.id.cardOpenHub);
        TextView txtOpenDeakinNews = view.findViewById(R.id.txtOpenDeakinNews);
        webViewHomeNews = view.findViewById(R.id.webViewHomeNews);

        cardOpenAI.setOnClickListener(v -> openFragment(new AIFragment()));
        cardOpenPlanner.setOnClickListener(v -> openFragment(new ScheduleFragment()));
        cardOpenHub.setOnClickListener(v -> openFragment(new DeakinFragment()));

        setupNewsWebView();

        String deakinNewsUrl = "https://www.deakin.edu.au/about-deakin/news-and-media-releases";

        webViewHomeNews.loadUrl(deakinNewsUrl);

        txtOpenDeakinNews.setOnClickListener(v ->
                openFragment(BrowserFragment.newInstance("Latest from Deakin", deakinNewsUrl))
        );

        return view;
    }

    /**
     * Loads Deakin public news section inside the Home screen.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void setupNewsWebView() {
        WebSettings settings = webViewHomeNews.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        webViewHomeNews.setWebViewClient(new WebViewClient());
    }

    private void openFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}