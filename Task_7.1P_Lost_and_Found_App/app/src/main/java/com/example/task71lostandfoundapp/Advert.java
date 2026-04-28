package com.example.task71lostandfoundapp;

public class Advert {

    private int id;
    private String postType;
    private String name;
    private String phone;
    private String description;
    private String dateText;
    private String location;
    private String category;
    private String imageUri;
    private String createdAt;

    public Advert(int id, String postType, String name, String phone,
                  String description, String dateText, String location,
                  String category, String imageUri, String createdAt) {
        this.id = id;
        this.postType = postType;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.dateText = dateText;
        this.location = location;
        this.category = category;
        this.imageUri = imageUri;
        this.createdAt = createdAt;
    }

    public Advert(String postType, String name, String phone,
                  String description, String dateText, String location,
                  String category, String imageUri, String createdAt) {
        this.postType = postType;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.dateText = dateText;
        this.location = location;
        this.category = category;
        this.imageUri = imageUri;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public String getPostType() { return postType; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getDescription() { return description; }
    public String getDateText() { return dateText; }
    public String getLocation() { return location; }
    public String getCategory() { return category; }
    public String getImageUri() { return imageUri; }
    public String getCreatedAt() { return createdAt; }
}