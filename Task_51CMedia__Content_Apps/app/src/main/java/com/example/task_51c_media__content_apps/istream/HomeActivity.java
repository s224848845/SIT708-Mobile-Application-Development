package com.example.task_51c_media__content_apps.istream;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.task_51c_media__content_apps.R;
import com.example.task_51c_media__content_apps.istream.data.AppDatabase;
import com.example.task_51c_media__content_apps.istream.data.SessionManager;
import com.example.task_51c_media__content_apps.istream.model.PlaylistItem;
import com.example.task_51c_media__content_apps.istream.util.YouTubeUtil;

public class HomeActivity extends AppCompatActivity {

    private EditText etYoutubeUrl;
    private Button btnPlay;
    private Button btnAddToPlaylist;
    private Button btnMyPlaylist;
    private Button btnLogout;
    private WebView webViewPlayer;
    private AppDatabase db;
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        etYoutubeUrl = findViewById(R.id.etYoutubeUrl);
        btnPlay = findViewById(R.id.btnPlay);
        btnAddToPlaylist = findViewById(R.id.btnAddToPlaylist);
        btnMyPlaylist = findViewById(R.id.btnMyPlaylist);
        btnLogout = findViewById(R.id.btnLogout);
        webViewPlayer = findViewById(R.id.webViewPlayer);

        db = AppDatabase.getInstance(this);
        currentUserId = SessionManager.getLoggedInUserId(this);

        if (currentUserId == -1) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        WebSettings settings = webViewPlayer.getSettings();
        settings.setJavaScriptEnabled(true);

        String selectedUrl = getIntent().getStringExtra("selected_url");
        if (selectedUrl != null) {
            etYoutubeUrl.setText(selectedUrl);
            playVideo(selectedUrl);
        }

//        btnPlay.setOnClickListener(v -> {
//            String url = etYoutubeUrl.getText().toString().trim();
//            playVideo(url);
//        });

        btnPlay.setOnClickListener(v -> {
            String url = etYoutubeUrl.getText().toString().trim();
            String videoId = YouTubeUtil.extractVideoId(url);

            if (videoId == null) {
                Toast.makeText(this, "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
                return;
            }

            // Try playing in WebView
            playVideo(url);

            // Show fallback message
            Toast.makeText(this,
                    "If video doesn't play, opening in YouTube...",
                    Toast.LENGTH_SHORT).show();

            // Delay fallback slightly (better UX)
            webViewPlayer.postDelayed(() -> openVideoExternally(url), 3000);
        });

        btnAddToPlaylist.setOnClickListener(v -> {
            String url = etYoutubeUrl.getText().toString().trim();
            String videoId = YouTubeUtil.extractVideoId(url);

            if (videoId == null) {
                Toast.makeText(this, "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
                return;
            }

            db.playlistDao().insert(new PlaylistItem(currentUserId, url));
            Toast.makeText(this, "Added to playlist", Toast.LENGTH_SHORT).show();
        });

        btnMyPlaylist.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, PlaylistActivity.class)));

        btnLogout.setOnClickListener(v -> {
            SessionManager.logout(this);
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void openVideoExternally(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(url));
        startActivity(intent);
    }

    private void playVideo(String url) {
        String videoId = YouTubeUtil.extractVideoId(url);

        if (videoId == null) {
            Toast.makeText(this, "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
            return;
        }

        WebSettings settings = webViewPlayer.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);

        webViewPlayer.setWebChromeClient(new android.webkit.WebChromeClient());
        webViewPlayer.setWebViewClient(new android.webkit.WebViewClient());

        String html =
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                        "  <style>" +
                        "    body { margin:0; background:#000; }" +
                        "    #player { position:absolute; top:0; left:0; width:100%; height:100%; }" +
                        "  </style>" +
                        "</head>" +
                        "<body>" +
                        "  <div id='player'></div>" +
                        "  <script>" +
                        "    var tag = document.createElement('script');" +
                        "    tag.src = 'https://www.youtube.com/iframe_api';" +
                        "    var firstScriptTag = document.getElementsByTagName('script')[0];" +
                        "    firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);" +
                        "" +
                        "    var player;" +
                        "    function onYouTubeIframeAPIReady() {" +
                        "      player = new YT.Player('player', {" +
                        "        videoId: '" + videoId + "'," +
                        "        playerVars: {" +
                        "          'autoplay': 0," +
                        "          'controls': 1," +
                        "          'rel': 0" +
                        "        }" +
                        "      });" +
                        "    }" +
                        "  </script>" +
                        "</body>" +
                        "</html>";

        webViewPlayer.loadDataWithBaseURL(
                "https://www.youtube.com",
                html,
                "text/html",
                "utf-8",
                null
        );
    }
}