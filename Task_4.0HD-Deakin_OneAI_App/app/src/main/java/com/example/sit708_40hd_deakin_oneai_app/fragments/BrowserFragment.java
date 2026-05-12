package com.example.sit708_40hd_deakin_oneai_app.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sit708_40hd_deakin_oneai_app.R;

/**
 * In-app Browser Workspace for public resources.
 *
 * Security note:
 * This browser should be used for public resources only.
 * Credential-based university login should be opened in the external browser.
 */
public class BrowserFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_URL = "url";

    private WebView webViewBrowser;
    private EditText inputUrl;

    private String pageTitle;
    private String startUrl;

    public static BrowserFragment newInstance(String title, String url) {
        BrowserFragment fragment = new BrowserFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_URL, url);

        fragment.setArguments(args);
        return fragment;
    }

    public BrowserFragment() {
        // Required empty constructor.
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_browser, container, false);

        TextView txtBrowserTitle = view.findViewById(R.id.txtBrowserTitle);
        inputUrl = view.findViewById(R.id.inputUrl);
        webViewBrowser = view.findViewById(R.id.webViewBrowser);

        Button btnGo = view.findViewById(R.id.btnGo);
        Button btnBackWeb = view.findViewById(R.id.btnBackWeb);
        Button btnReload = view.findViewById(R.id.btnReload);
        Button btnOpenChrome = view.findViewById(R.id.btnOpenChrome);

        if (getArguments() != null) {
            pageTitle = getArguments().getString(ARG_TITLE, "Browser Workspace");
            startUrl = getArguments().getString(ARG_URL, "https://www.google.com");
        } else {
            pageTitle = "Browser Workspace";
            startUrl = "https://www.google.com";
        }

        txtBrowserTitle.setText(pageTitle);
        inputUrl.setText(startUrl);

        setupWebView();
        webViewBrowser.loadUrl(startUrl);

        btnGo.setOnClickListener(v -> loadFromInput());

        btnBackWeb.setOnClickListener(v -> {
            if (webViewBrowser.canGoBack()) {
                webViewBrowser.goBack();
            } else {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btnReload.setOnClickListener(v -> webViewBrowser.reload());

        btnOpenChrome.setOnClickListener(v -> openCurrentPageExternally());

        return view;
    }

    private void setupWebView() {
        WebSettings settings = webViewBrowser.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        webViewBrowser.setWebViewClient(new WebViewClient());
    }

    private void loadFromInput() {
        String value = inputUrl.getText().toString().trim();

        if (value.isEmpty()) {
            Toast.makeText(requireContext(), "Enter a URL or search query.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url;

        if (value.startsWith("http://") || value.startsWith("https://")) {
            url = value;
        } else if (value.contains(".")) {
            url = "https://" + value;
        } else {
            url = "https://www.google.com/search?q=" + Uri.encode(value);
        }

        inputUrl.setText(url);
        webViewBrowser.loadUrl(url);
    }

    private void openCurrentPageExternally() {
        try {
            String currentUrl = webViewBrowser.getUrl();

            if (currentUrl == null || currentUrl.trim().isEmpty()) {
                currentUrl = startUrl;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentUrl));
            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(requireContext(), "Unable to open external browser.", Toast.LENGTH_SHORT).show();
        }
    }
}