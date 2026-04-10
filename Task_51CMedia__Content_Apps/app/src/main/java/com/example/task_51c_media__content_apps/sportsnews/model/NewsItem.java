package com.example.task_51c_media__content_apps.sportsnews.model;

public class NewsItem {

    private final int imageResId;
    private final String title;
    private final String description;
    private final String category;

    public NewsItem(int imageResId, String title, String description, String category) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
        this.category = category;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }
}