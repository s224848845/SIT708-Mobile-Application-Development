# 📱 Lost & Found Android App (SIT708 Task 7.1P)

## 📌 Overview
The Lost & Found App is an Android mobile application developed as part of SIT708 – Mobile Application Development. The application allows users to create, view, filter, and manage lost or found item advertisements. The goal of the app is to help reconnect lost items with their owners by providing a simple, user-friendly interface.

This project demonstrates core Android development concepts including SQLite database management, activity navigation, image handling using the Storage Access Framework, and dynamic UI updates.

---

## 🚀 Features

- Create Lost or Found adverts  
- Mandatory image upload for every advert  
- Categorise items (Electronics, Pets, Wallets, Bags, Documents, Keys, Other)  
- Filter adverts by category using Spinner  
- View all adverts in a ListView  
- View detailed information for each advert  
- Remove advert once the item is returned  
- Timestamp showing when the advert was posted  
- Persistent local storage using SQLite  

---

## 🏗️ Tech Stack

- Programming Language: Java  
- IDE: Android Studio  
- Database: SQLite (SQLiteOpenHelper)  
- UI Components: Activities, ListView, Spinner, ImageView  
- Image Handling: Android Storage Access Framework (URI-based)  

---

## 📂 Project Structure

Java Classes:
- MainActivity.java
- CreateAdvertActivity.java
- ItemListActivity.java
- ItemDetailActivity.java
- DBHelper.java
- Advert.java
- AdvertAdapter.java

Layout Files:
- activity_main.xml
- activity_create_advert.xml
- activity_item_list.xml
- activity_item_detail.xml
- item_advert.xml

---

## 🗄️ Database Design

Table: adverts

- id (Primary Key, Auto Increment)  
- post_type (Lost or Found)  
- name (Item name/title)  
- phone (Contact number)  
- description (Item details)  
- date_text (Date lost/found)  
- location (Location)  
- category (Item category)  
- image_uri (Image URI)  
- created_at (Timestamp of post)  

---

## ⚙️ How to Run the App

1. Clone the repository:
   git clone https://github.com/s224848845/SIT708-Mobile-Application-Development/tree/main/Task_7.1P_Lost_and_Found_App

2. Open the project in Android Studio

3. Start an emulator or connect a device

4. Click Run ▶️

---

## 🧪 How to Use the App

1. Open the app  
2. Click "Create a New Advert"  
3. Enter all details (name, phone, description, date, location)  
4. Select category  
5. Upload image (mandatory)  
6. Save advert  
7. Go to "Show All Items"  
8. Filter by category  
9. Click an item to view details  
10. Remove advert once item is returned  

---

## 📸 Image Handling

- Images are selected using ACTION_OPEN_DOCUMENT  
- Stored as URI references in SQLite  
- Permissions persisted using takePersistableUriPermission()  
- Images displayed dynamically in ImageView  

---

## 🔍 Category Filtering

- Implemented using Spinner + ListView  
- Supports:
  - All  
  - Electronics  
  - Pets  
  - Wallets  
  - Bags  
  - Documents  
  - Keys  
  - Other  

---

## 🔄 Application Flow

MainActivity  
→ CreateAdvertActivity (Add item)  
→ ItemListActivity (View & Filter)  
→ ItemDetailActivity (View & Remove)  

---

## 📈 Future Improvements

- Cloud database integration (Firebase / API)  
- User authentication system  
- Push notifications  
- Location-based search (GPS)  
- AI-based image matching  
- Improved UI with Material Design  

---

## 🎥 Demonstration Video

https://youtube.com/your-demo-link

---

## 🔗 GitHub Repository

https://github.com/s224848845/SIT708-Mobile-Application-Development/tree/main/Task_7.1P_Lost_and_Found_App

---

## 📚 References

Android Developers (2026) Save data in SQLite  
https://developer.android.com/training/data-storage/sqlite  
(Accessed: 28 April 2026)

Android Developers (2026) Storage Access Framework  
https://developer.android.com/guide/topics/providers/document-provider  
(Accessed: 28 April 2026)

Android Developers (2026) Activity Lifecycle  
https://developer.android.com/guide/components/activities/activity-lifecycle  
(Accessed: 27 April 2026)

GeeksforGeeks (2026) SQLite Database in Android  
https://www.geeksforgeeks.org/sqlite-database-in-android/  
(Accessed: 28 April 2026)

GeeksforGeeks (2026) Image Selection in Android  
https://www.geeksforgeeks.org/how-to-select-an-image-from-gallery-in-android/  
(Accessed: 28 April 2026)

---

## 👨‍💻 Author

Ashan Indika Wijayarathne Hewayalage
224848845
SIT708 – Mobile Application Development  
Deakin University
