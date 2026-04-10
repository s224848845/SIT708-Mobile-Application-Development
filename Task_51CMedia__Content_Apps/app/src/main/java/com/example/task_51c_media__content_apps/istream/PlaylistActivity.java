package com.example.task_51c_media__content_apps.istream;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_51c_media__content_apps.R;
import com.example.task_51c_media__content_apps.istream.adapter.PlaylistAdapter;
import com.example.task_51c_media__content_apps.istream.data.AppDatabase;
import com.example.task_51c_media__content_apps.istream.data.SessionManager;
import com.example.task_51c_media__content_apps.istream.model.PlaylistItem;

import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    private RecyclerView rvPlaylist;
    private Button btnBackHome;
    private Button btnLogout;
    private AppDatabase db;
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        rvPlaylist = findViewById(R.id.rvPlaylist);
        btnBackHome = findViewById(R.id.btnBackHome);
        btnLogout = findViewById(R.id.btnLogoutPlaylist);

        db = AppDatabase.getInstance(this);
        currentUserId = SessionManager.getLoggedInUserId(this);

        List<PlaylistItem> playlist = db.playlistDao().getPlaylistForUser(currentUserId);

        rvPlaylist.setLayoutManager(new LinearLayoutManager(this));
        rvPlaylist.setAdapter(new PlaylistAdapter(playlist, item -> {
            Intent intent = new Intent(PlaylistActivity.this, HomeActivity.class);
            intent.putExtra("selected_url", item.videoUrl);
            startActivity(intent);
        }));

        btnBackHome.setOnClickListener(v -> finish());

        btnLogout.setOnClickListener(v -> {
            SessionManager.logout(this);
            Intent intent = new Intent(PlaylistActivity.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();
        });
    }
}