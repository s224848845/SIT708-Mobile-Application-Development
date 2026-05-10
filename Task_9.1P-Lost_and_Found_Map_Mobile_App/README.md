## SIT708 – Mobile Application Development  
### Task 9.1P – Lost and Found Map Mobile App

---

# Project Overview

This Android application is an extension of the Lost and Found mobile app developed in Task 7.1P. The application allows users to create and manage Lost and Found adverts using SQLite local storage while integrating Google geo-location and map-based features.

The app supports:
- Creating Lost or Found adverts
- Uploading item images
- Selecting item categories
- Google Places location autocomplete
- Current GPS location selection
- Google Maps integration
- Radius-based search
- Category-based filtering
- Viewing advert details
- Removing adverts

The application was developed using Java in Android Studio.

---

# Features

## Core Features
- Create Lost or Found adverts
- Save adverts locally using SQLite
- Upload item image from device
- Select category using Spinner
- View all adverts in ListView
- Remove adverts

## Geo Features
- Google Maps integration
- Google Places Autocomplete
- Current location selection
- Map markers for saved items
- Radius-based item search
- Zoom and map gesture support

---

# Technologies Used

| Technology | Purpose |
|---|---|
| Java | Android application development |
| Android Studio | Development environment |
| SQLite | Local database storage |
| Google Maps SDK | Map integration |
| Google Places SDK | Location autocomplete |
| Fused Location Provider | Current GPS location |
| XML | User interface layouts |

---

# Project Structure

```text
app/
 ├── java/com/example/task71lostandfoundapp/
 │    ├── MainActivity.java
 │    ├── CreateAdvertActivity.java
 │    ├── ItemListActivity.java
 │    ├── ItemDetailActivity.java
 │    ├── MapActivity.java
 │    ├── DBHelper.java
 │    ├── Advert.java
 │    └── AdvertAdapter.java
 │
 ├── res/layout/
 │    ├── activity_main.xml
 │    ├── activity_create_advert.xml
 │    ├── activity_item_list.xml
 │    ├── activity_item_detail.xml
 │    ├── activity_map.xml
 │    └── item_advert.xml
 │
 └── AndroidManifest.xml
```

---

# Google Maps API Setup

## Step 1 – Create Google Cloud Project
1. Open Google Cloud Console
2. Create a new project

## Step 2 – Enable APIs
Enable the following APIs:
- Maps SDK for Android
- Places API
- Geocoding API

## Step 3 – Create API Key
1. Open APIs & Services
2. Open Credentials
3. Create API Key

## Step 4 – Add SHA1
Run:

```bash
./gradlew signingReport
```

Copy SHA1 and add it to Android App restrictions.

## Step 5 – Add API Key
Open:

```text
res/values/strings.xml
```

Replace:

```xml
<string name="google_maps_key">PASTE_YOUR_GOOGLE_MAPS_API_KEY_HERE</string>
```

with your own API key.

---

# How to Run the App

## Requirements
- Android Studio
- Android Emulator or Physical Device
- Internet connection
- Google Maps API key

## Steps
1. Clone/download the project
2. Open project in Android Studio
3. Add Google Maps API key
4. Sync Gradle
5. Run the application

---

# Application Workflow

## Create Advert
1. Select Lost or Found
2. Enter item details
3. Select date using DatePicker
4. Select location using:
   - Places autocomplete OR
   - Current GPS location
5. Upload item image
6. Save advert

## View Items
- All adverts appear in ListView
- Filter by category using Spinner
- Open advert details

## Map Screen
- All adverts displayed using markers
- Radius-based search supported
- Lost items shown using red markers
- Found items shown using green markers

---

# Radius-Based Search

The map screen supports radius-based filtering.

Users can:
1. Enter radius in kilometres
2. Click Search
3. Only nearby items are displayed

The application calculates distance using:

```java
Location.distanceBetween()
```

---

# Database Design

The application uses SQLite local storage.

## Advert Table Fields

| Column | Description |
|---|---|
| id | Primary key |
| post_type | Lost or Found |
| name | Item name |
| phone | Contact number |
| description | Item description |
| date_text | Lost/found date |
| location | Selected address |
| latitude | GPS latitude |
| longitude | GPS longitude |
| category | Item category |
| image_uri | Device image URI |
| created_at | Advert created timestamp |

---

# Learning Outcomes Achieved

This project demonstrates:
- Android activity navigation
- SQLite database integration
- Google Maps SDK usage
- Google Places autocomplete
- GPS location services
- Recycler/ListView integration
- Custom adapters
- Runtime permissions
- Mobile UI/UX implementation
- Geo-location based mobile app development

---

# Known Limitations

- Data is stored locally only
- No cloud synchronisation
- No user authentication
- Radius search currently uses Melbourne as demo search centre
- Internet required for Maps and Places APIs

---

# Future Improvements

Possible future improvements include:
- Firebase cloud database
- User login system
- Push notifications
- Real-time item tracking
- AI-based item matching
- Chat system between users
- Admin moderation dashboard

---

# References

1. Android Developers. (2026). Android Developer Documentation. https://developer.android.com/

2. Google Developers. (2026). Google Maps SDK for Android Documentation. https://developers.google.com/maps/documentation/android-sdk

3. Google Developers. (2026). Places SDK for Android Documentation. https://developers.google.com/maps/documentation/places/android-sdk

4. SQLite Documentation. (2026). SQLite Official Documentation. https://www.sqlite.org/docs.html

5. Material Design. (2026). Material Design Guidelines. https://m3.material.io/

---

# GitHub Repository

Add your GitHub repository link here:

```text
https://github.com/s224848845/SIT708-Mobile-Application-Development/tree/main/Task_9.1P-Lost_and_Found_Map_Mobile_App
```

---

# Demonstration Video



