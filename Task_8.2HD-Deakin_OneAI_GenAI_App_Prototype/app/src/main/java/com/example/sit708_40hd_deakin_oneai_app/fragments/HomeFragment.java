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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.sit708_40hd_deakin_oneai_app.R;

/**
 * Home dashboard for Deakin OneAI.
 *
 * Includes:
 * - Gemini AI quick actions
 * - Study planner shortcut
 * - Weekly calendar shortcut
 * - Deakin Hub shortcut
 * - About information popup
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

        // Main Home dashboard action cards
        LinearLayout cardOpenAI = view.findViewById(R.id.cardOpenAI);
        LinearLayout cardOpenPlanner = view.findViewById(R.id.cardOpenPlanner);
        LinearLayout cardOpenCalendar = view.findViewById(R.id.cardOpenCalendar);
        LinearLayout cardOpenHub = view.findViewById(R.id.cardOpenHub);

        // Information icon in the Home top title row
        TextView btnInfoAbout = view.findViewById(R.id.btnInfoAbout);

        // Deakin news preview and news browser shortcut
        TextView txtOpenDeakinNews = view.findViewById(R.id.txtOpenDeakinNews);
        webViewHomeNews = view.findViewById(R.id.webViewHomeNews);

        /*
         * Navigation actions.
         * Each card opens a dedicated Fragment while staying inside the app.
         */
        cardOpenAI.setOnClickListener(v -> openFragment(new AIFragment()));
        cardOpenPlanner.setOnClickListener(v -> openFragment(new ScheduleFragment()));
        cardOpenCalendar.setOnClickListener(v -> openFragment(new CalendarFragment()));
        cardOpenHub.setOnClickListener(v -> openFragment(new DeakinFragment()));

        /*
         * About information is shown as a popup instead of a separate screen.
         * This follows a more common industry pattern for secondary information.
         */
        btnInfoAbout.setOnClickListener(v -> showAboutDialog());

        setupNewsWebView();

        String deakinNewsUrl = "https://www.deakin.edu.au/about-deakin/news-and-media-releases";

        // Loads a public Deakin news preview directly inside the Home screen.
        webViewHomeNews.loadUrl(deakinNewsUrl);

        // Opens the same Deakin news page in the full Browser workspace.
        txtOpenDeakinNews.setOnClickListener(v ->
                openFragment(BrowserFragment.newInstance("Latest from Deakin", deakinNewsUrl))
        );

        return view;
    }

    /**
     * Configures WebView for displaying public Deakin news content.
     *
     * JavaScript and DOM storage are enabled because modern university pages
     * often require them for rendering menus, news cards, and embedded content.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void setupNewsWebView() {
        WebSettings settings = webViewHomeNews.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        // Keeps links inside the app preview instead of launching an external browser.
        webViewHomeNews.setWebViewClient(new WebViewClient());
    }

    /**
     * Shows app information as a popup dialog.
     *
     * This replaces the previous About bottom-tab screen and keeps the main
     * navigation focused on active user tasks: Home, AI, Planner, Calendar, and Hub.
     */
    private void showAboutDialog() {
        String message =
                "Deakin OneAI is a Gemini-powered Android study assistant prototype.\n\n"
                        + "AI Integration:\n"
                        + "• Hybrid mobile + Gemini API workflow\n"
                        + "• Academic prompts, summaries, study plans, checklists and flashcards\n\n"
                        + "Privacy:\n"
                        + "• Do not enter passwords, student IDs or sensitive personal data\n"
                        + "• Prompts are sent to Gemini for response generation\n\n"
                        + "Safety:\n"
                        + "• Unsafe prompts are filtered locally before Gemini submission\n\n"
                        + "Compatibility:\n"
                        + "• Designed for API 35 and API 36 testing\n"
                        + "• Uses modern fragment navigation and Android UI components";

        new AlertDialog.Builder(requireContext())
                .setTitle("About Deakin OneAI")
                .setMessage(message)
                .setPositiveButton("Close", null)
                .show();
    }

    /**
     * Opens a new Fragment inside the app and adds it to the back stack.
     *
     * This allows users to press Back and return to the previous Home screen,
     * creating a smoother mobile navigation experience.
     */
    private void openFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
