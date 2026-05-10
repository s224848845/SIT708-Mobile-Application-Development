package com.example.task71lostandfoundapp;

/**
 * Model class
 * This object is used to transfer advert data between SQLite, ListView, detail screen, and map screen.
 */
public class Advert {

    private int id;
    private String postType;
    private String name;
    private String phone;
    private String description;
    private String dateText;
    private String location;
    private double latitude;
    private double longitude;
    private String category;
    private String imageUri;
    private String createdAt;

    // Constructor used when reading adverts from SQLite database because ID already exists.
    public Advert(int id, String postType, String name, String phone,
                  String description, String dateText, String location,
                  double latitude, double longitude,
                  String category, String imageUri, String createdAt) {
        this.id = id;
        this.postType = postType;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.dateText = dateText;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.imageUri = imageUri;
        this.createdAt = createdAt;
    }

    // Constructor used when creating a new advert before SQLite generates the ID.
    public Advert(String postType, String name, String phone,
                  String description, String dateText, String location,
                  double latitude, double longitude,
                  String category, String imageUri, String createdAt) {
        this.postType = postType;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.dateText = dateText;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
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
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getCategory() { return category; }
    public String getImageUri() { return imageUri; }
    public String getCreatedAt() { return createdAt; }
}