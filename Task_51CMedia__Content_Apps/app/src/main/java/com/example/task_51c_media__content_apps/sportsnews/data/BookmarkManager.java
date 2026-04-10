package com.example.task_51c_media__content_apps.sportsnews.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.task_51c_media__content_apps.sportsnews.model.NewsItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookmarkManager {

    private static final String PREF_NAME = "bookmarks_pref";
    private static final String KEY_BOOKMARKS = "bookmarks";

    public static void saveBookmark(Context context, NewsItem item) {
        List<NewsItem> bookmarks = getBookmarks(context);

        // Prevent duplicate bookmarks
        for (NewsItem news : bookmarks) {
            if (news.getTitle().equals(item.getTitle())) {
                return;
            }
        }

        bookmarks.add(item);

        SharedPreferences preferences =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        preferences.edit()
                .putString(KEY_BOOKMARKS, new Gson().toJson(bookmarks))
                .apply();
    }

    public static List<NewsItem> getBookmarks(Context context) {
        SharedPreferences preferences =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        String json = preferences.getString(KEY_BOOKMARKS, null);

        if (json == null) {
            return new ArrayList<>();
        }

        Type type = new TypeToken<List<NewsItem>>() {}.getType();
        return new Gson().fromJson(json, type);
    }
}