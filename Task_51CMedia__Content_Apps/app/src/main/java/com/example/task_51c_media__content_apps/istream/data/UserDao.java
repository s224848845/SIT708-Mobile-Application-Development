package com.example.task_51c_media__content_apps.istream.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.task_51c_media__content_apps.istream.model.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    User getUserByUsername(String username);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User login(String username, String password);
}