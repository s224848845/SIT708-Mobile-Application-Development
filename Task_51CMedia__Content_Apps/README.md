# 📱 Task 5.1C – Media & Content Apps

### Sports News Feed App and iStream Video App

---

## 📌 Project Overview

This project consists of two Android applications integrated into a single project:

1. **Sports News Feed App**
2. **iStream Video Streaming App**

The objective of this project is to demonstrate key mobile application development concepts such as user interface design, navigation, local data persistence, and media content integration.

---

## 📰 1. Sports News Feed App

### ✨ Features

* 🏟️ **Featured Matches (Horizontal Scroll)**
* 📰 **Latest Sports News (Vertical List)**
* 🔍 **Search and Filter by Category or Title**
* 📄 **Detailed News View Screen**
* 🔗 **Related Stories (Based on Category)**
* ⭐ **Bookmark System (Persistent Storage)**
* 📚 **Bookmarks Screen for Saved Stories**

### 🧱 Technologies Used

* RecyclerView (Horizontal and Vertical Lists)
* Fragments (Single Activity Architecture)
* CardView UI Components
* SharedPreferences (Bookmark Persistence)

---

## 🎬 2. iStream Video App

### ✨ Features

* 🔐 **User Authentication**

    * Sign Up with validation
    * Login functionality
    * Session management using SharedPreferences

* ▶️ **YouTube Video Playback**

    * Input YouTube video URL
    * Extract Video ID dynamically
    * Attempt playback using WebView and YouTube iFrame Player API

* ➕ **Playlist Management**

    * Add videos to user-specific playlist
    * Store playlist using Room Database
    * View and select saved videos

* 🔄 **Navigation**

    * Home → Playlist navigation
    * Logout functionality (Home & Playlist screens)

---

## 🧱 Technologies Used

* Room Database (User and Playlist storage)
* WebView with YouTube iFrame API
* SharedPreferences (Session management)
* RecyclerView (Playlist display)

---

## ⚠️ Important Note – YouTube Playback

The application integrates YouTube video playback using the **YouTube iFrame Player API within a WebView**.

However, during testing, some videos may not play and display:

> **Error 153 – Video player configuration error**

### Reason

This occurs due to platform-level restrictions:

* YouTube enforces security and integrity policies for embedded players
* WebView does not always provide the required client identification (referer/origin)
* This is a **platform limitation, not a coding issue**

### ✅ Fallback Approach

To maintain usability, the application supports opening videos in the **YouTube app or browser** when embedded playback is restricted.

This ensures:

* Continuous functionality
* Better user experience
* Real-world robustness

---

## 📂 Project Structure

```bash
com.example.task_51c_media__content_apps
│
├── MainActivity.java
│
├── sportsnews/
│   ├── SportsNewsActivity.java
│   ├── fragment/
│   ├── adapter/
│   ├── model/
│   └── data/
│
└── istream/
    ├── LoginActivity.java
    ├── SignUpActivity.java
    ├── HomeActivity.java
    ├── PlaylistActivity.java
    ├── adapter/
    ├── model/
    ├── data/
    └── util/
```

---

## 🛠️ Setup Requirements

* Android Studio (latest version recommended)
* Android SDK 24 or above
* Internet connection (required for YouTube playback)
* Emulator or physical Android device

---

## 🚀 How to Run the App

1. Open the project in **Android Studio**
2. Sync Gradle files
3. Connect an emulator or Android device
4. Click **Run ▶️**
5. Launch the application and choose:

    * Sports News Feed App
    * iStream Video App

---

## 🎥 Demonstration Coverage

The application demonstrates:

* User authentication workflow (Sign Up, Login, Logout)
* Sports news browsing and filtering
* Bookmarking and persistent storage
* YouTube URL input and embedded playback attempt using WebView
* Handling playback limitations through platform-aware design
* Playlist creation and retrieval using Room Database
* Navigation between multiple screens

---

## 🧠 Learning Outcomes

This project demonstrates:

* Android UI/UX design principles
* RecyclerView and Adapter design patterns
* Fragment-based navigation architecture
* Local data persistence (Room Database and SharedPreferences)
* Media content integration using external APIs (YouTube)
* Handling real-world API limitations and fallback strategies

---

## 📚 References

* Android Developers 2024, *RecyclerView Guide*
  https://developer.android.com/guide/topics/ui/layout/recyclerview

* Android Developers 2024, *Room Persistence Library*
  https://developer.android.com/training/data-storage/room

* YouTube Developers 2024, *IFrame Player API*
  https://developers.google.com/youtube/iframe_api_reference

* Android Developers 2024, *WebView Documentation*
  https://developer.android.com/reference/android/webkit/WebView

---

## ✅ Conclusion

The project successfully implements two fully functional Android applications that meet all specified requirements. While YouTube playback within WebView is subject to external platform restrictions, the application incorporates a practical fallback approach to ensure consistent usability and a seamless user experience.

---

## ⭐ Author

**Ashan Indika Wijayarathne Hewayalage**
Master of Information Technology – Deakin University
