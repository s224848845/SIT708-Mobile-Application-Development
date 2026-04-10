package com.example.task_51c_media__content_apps.istream.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "playlist")
public class PlaylistItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int userId;
    public String videoUrl;

    public PlaylistItem(int userId, String videoUrl) {
        this.userId = userId;
        this.videoUrl = videoUrl;
    }
}