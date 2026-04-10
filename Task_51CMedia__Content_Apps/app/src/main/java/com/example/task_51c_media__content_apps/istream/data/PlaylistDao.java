package com.example.task_51c_media__content_apps.istream.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.task_51c_media__content_apps.istream.model.PlaylistItem;

import java.util.List;

@Dao
public interface PlaylistDao {

    @Insert
    void insert(PlaylistItem item);

    @Query("SELECT * FROM playlist WHERE userId = :userId")
    List<PlaylistItem> getPlaylistForUser(int userId);
}