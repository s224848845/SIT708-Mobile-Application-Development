# 📱 Personal Event Planner App
**SIT708 – Mobile Application Development (Task 4.1P)**

---

## 📌 Overview
The Personal Event Planner App is an Android application developed using Java that allows users to manage their personal events, appointments, and schedules efficiently.

The app supports full CRUD operations (Create, Read, Update, Delete) and uses the Room Persistence Library to store data locally on the device. It follows modern Android development practices using Fragments and the Jetpack Navigation Component.

---

## 🚀 Features

### Core Functionality
- Add new events with:
    - Title
    - Category (Work, Social, Travel)
    - Location
    - Date & Time
- View all upcoming events sorted by date
- Edit existing events
- Delete events

### Data Persistence
- Uses Room Database
- Data stored locally on device
- Events persist after app restart

### Navigation
- Jetpack Navigation Component
- Fragment-based architecture
- Bottom Navigation Bar:
    - Events
    - Add Event

### Validation & Error Handling
- Prevents saving without title
- Prevents saving without date/time
- Prevents selecting past dates for new events
- Displays user feedback using Toast messages

---

## 🛠️ Technologies Used
- Java
- Android Studio
- Room Database
- RecyclerView
- Fragments
- Jetpack Navigation Component
- ViewModel & LiveData
- Material UI Components

---

## 🧱 Project Structure

Task_4.1P-Personal_Event_Planner_App/
│
├── java/com/example/task_41p_personal_event_planner_app/
│   ├── MainActivity.java
│   ├── Event.java
│   ├── EventDao.java
│   ├── EventDatabase.java
│   ├── EventRepository.java
│   ├── EventViewModel.java
│   ├── EventListFragment.java
│   ├── AddEditEventFragment.java
│   └── EventAdapter.java
│
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   ├── fragment_event_list.xml
│   │   ├── fragment_add_edit_event.xml
│   │   └── item_event.xml
│   │
│   ├── navigation/
│   │   └── nav_graph.xml
│   │
│   ├── menu/
│   │   └── bottom_nav_menu.xml
│
└── README.md

---

## ▶️ How to Run the App

1. Clone the repository:
   git clone https://github.com/<your-username>/SIT708-Mobile-Application-Development.git

2. Open the project in Android Studio

3. Navigate to:
   Task_4.1P-Personal_Event_Planner_App

4. Sync Gradle

5. Run the app on:
- Emulator OR
- Physical Android device

---

## 🧪 Testing Scenarios

### CRUD Operations
- Add event → appears in list
- Edit event → updates correctly
- Delete event → removed from list

### Validation
- Empty title → blocked
- No date/time → blocked
- Past date → blocked

### Persistence
- Close app → reopen → events still available

---

## 🎥 Demonstration
Demo: 

---

## 🤖 LLM Declaration
Generative AI tools (e.g., ChatGPT) were used as supplementary learning aids for understanding concepts, debugging issues, and improving documentation.

All implementation, testing, and final submission were completed independently in accordance with academic integrity requirements.

---

## 🔗 Repository Link
GitHub Repository: 

---

## 📌 Notes
- Developed for SIT708 Mobile Application Development
- Demonstrates modern Android architecture and best practices

---

## ✅ Final Status
✔ CRUD operations implemented  
✔ Room persistence verified  
✔ Navigation and fragments implemented  
✔ Validation and error handling complete

---