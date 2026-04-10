package com.example.task_51c_media__content_apps.istream.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String fullName;

    @NonNull
    public String username;

    @NonNull
    public String password;

    public User(@NonNull String fullName, @NonNull String username, @NonNull String password) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }
}