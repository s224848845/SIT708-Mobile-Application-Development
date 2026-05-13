# Deakin OneAI – Gemini-Powered Android Learning Assistant

## SIT708 – 8.2HD Android GenAI App Prototype

Deakin OneAI is a modern Android mobile application prototype developed for SIT708 Mobile Application Development. The application integrates Google Gemini AI into an Android study assistant platform designed to support Deakin University students with academic productivity, study planning, AI-assisted learning, browser-based research, calendar scheduling, file organisation, and Deakin student service access.

The application combines productivity tools, AI learning utilities, and university resources into a single Android workspace while following modern mobile UI/UX principles.

---

# Application Features

## ✨ OneAI Studio

OneAI Studio is the primary AI-powered learning environment inside the app. It integrates Google Gemini AI to support academic productivity and learning workflows.

### Supported AI Utilities

- Ask academic questions
- Summarise lecture notes
- Generate study plans
- Create assignment checklists
- Explain difficult concepts
- Generate revision flashcards

### OneAI Features

- Academic prompt workspace
- AI tool selector interface
- Persistent selected-tool highlighting
- Gemini response generation
- Response formatting cleanup
- Copy response support
- Clear response support
- Loading progress indicator
- Privacy and safety notice

---

## 🏠 Home Dashboard

The Home screen acts as the central productivity dashboard for the application.

### Features

- Welcome productivity dashboard
- AI quick action cards
- Study planner shortcut
- Weekly calendar shortcut
- Deakin Hub shortcut
- Productivity readiness section
- Embedded Deakin news preview
- Information popup access

---

## 📋 Study Planner

The Study Planner supports structured academic task preparation and assignment management.

### Features

- Unit name input
- Task name input
- Native Android date picker
- Local study plan generation
- Structured milestone workflow
- Assignment preparation guidance
- Academic planning support

### Example Use Cases

- Assignment planning
- Weekly workload preparation
- Demo preparation
- Assessment tracking

---

## 📅 Weekly Calendar

The Weekly Calendar provides a Teams-style academic scheduling workflow.

### Features

- Add custom calendar events
- Persistent local event storage
- Date picker integration
- Time picker integration
- Event category selection
- Event templates
- Reminder support
- Online session toggle
- Dynamic event cards
- Reset sample events option

### Supported Event Types

- Lecture
- Workshop
- Tutorial
- Study Block
- Assessment
- Meeting

### Event Templates

- SIT708 lecture
- Mobile app workshop
- Assessment preparation
- Gemini AI testing
- Demo recording session

---

## 🎓 Deakin Hub

The Deakin Hub provides access to student productivity resources and academic services inside the app.

### Deakin Resources

- CloudDeakin
- OnTrack
- StudentConnect
- DeakinSync
- STAR Timetable

### Productivity Workspace

- Browser Workspace
- GitHub Workspace
- Local Study Files
- Weekly Calendar

All resources are opened inside the application using embedded browser workflows for consistent navigation.

---

## 🌐 Browser Workspace

The Browser Workspace provides in-app browsing support for academic resources and productivity websites.

### Features

- URL input
- Google search support
- Back navigation
- Reload support
- Embedded browsing
- Open current page externally if required

---

## 📁 Local Study Files

The Local Study Files section simulates a student file management workflow.

### Features

- Simulated file upload
- Academic file list
- Local workspace interface
- File management preview workflow

### Planned Future Support

- Android Storage Access Framework
- PDF parsing
- Gemini document summarisation
- Cloud storage integration
- Firebase file sync

---

# Gemini AI Integration

The application uses Google Gemini AI in a hybrid mobile-cloud architecture.

## Gemini Workflow

- User enters prompt inside OneAI Studio
- Prompt is processed through Gemini API
- AI response is returned inside the Android app
- Output is formatted for mobile readability

## AI Use Cases

Gemini AI is used for learning-focused utilities rather than general conversation only.

Supported learning workflows include:

- Lesson summarisation
- Assignment planning
- Checklist generation
- Concept explanation
- Flashcard generation
- Academic assistance

---

# Privacy and Safety

The application includes privacy and safety handling for responsible AI usage.

## Privacy Notice

Users are advised not to enter:

- Passwords
- Student IDs
- Medical information
- Private documents
- Sensitive personal data

## Safety Handling

Unsafe prompts related to:

- Malware
- Credential theft
- Phishing
- Illegal activity
- Exploit instructions

are rejected locally before sending requests to Gemini AI.

---

# Android Compatibility

The application is designed for modern Android compatibility.

## Recommended Testing

- Android API 35
- Android API 36

## Android Features Used

- Fragment navigation
- BottomNavigationView
- WebView
- SharedPreferences
- ScrollView
- DatePickerDialog
- TimePickerDialog
- AlertDialog
- OnBackPressedDispatcher

---

# User Interface Design

The application follows a modern Android productivity-focused UI structure.

## UI Characteristics

- Premium gradient dashboard
- Rounded modern cards
- Floating bottom navigation
- Icon-only navigation system
- Improved typography hierarchy
- Responsive layouts
- Scrollable workspace design
- Embedded productivity widgets
- Teams-style calendar interface

---

# Technologies Used

## Android Development

- Java
- Android Studio
- Android SDK
- XML Layouts
- Fragments
- Material Components

## Android Components

- BottomNavigationView
- WebView
- ScrollView
- SharedPreferences
- AlertDialog
- DatePickerDialog
- TimePickerDialog

## AI Integration

- Google Gemini API
- HTTP networking
- JSON request/response handling
- Prompt safety validation

---

# Project Structure

```text
app/
└── src/
    └── main/
        ├── java/
        │   └── com/example/sit708_40hd_deakin_oneai_app/
        │       ├── ai/
        │       │   └── GeminiService.java
        │       ├── fragments/
        │       │   ├── AboutFragment.java
        │       │   ├── AIFragment.java
        │       │   ├── BrowserFragment.java
        │       │   ├── CalendarFragment.java
        │       │   ├── DeakinFragment.java
        │       │   ├── FilesFragment.java
        │       │   ├── GithubFragment.java
        │       │   ├── HomeFragment.java
        │       │   └── ScheduleFragment.java
        │       └── MainActivity.java
        │
        └── res/
            ├── drawable/
            ├── layout/
            ├── menu/
            ├── values/
            └── xml/
```

---

# Setup Instructions

## 1. Open Project

Open the project folder in Android Studio.

---

## 2. Add Gemini API Key

Add the Gemini API key according to the current project configuration.

If using `local.properties`, add:

```properties
GEMINI_API_KEY=PASTE_YOUR_GEMINI_API_KEY_HERE
```

Do not commit real API keys to GitHub.

---

## 3. Sync Gradle

In Android Studio:

```text
File > Sync Project with Gradle Files
```

---

## 4. Run the Application

Run using:

- Android Emulator
- Physical Android Device

### Recommended Versions

- API 35
- API 36

---

# Suggested Demonstration Flow

1. Open the Home Dashboard.
2. Explain productivity workflow and navigation.
3. Open OneAI Studio.
4. Enter academic prompt and select AI tool.
5. Generate Gemini AI response.
6. Demonstrate copy and clear response functions.
7. Open Study Planner and generate a study plan.
8. Open Weekly Calendar and add a new event.
9. Restart the app and demonstrate persistent calendar storage.
10. Open Deakin Hub resources.
11. Open Browser Workspace.
12. Open Local Study Files.
13. Explain privacy and AI safety handling.

---

# Example Gemini Prompts

```text
Summarise Android Fragment lifecycle for revision.
```

```text
Create a study plan for SIT708 8.2HD GenAI App Prototype.
```

```text
Explain why Android uses fragments in mobile app development.
```

```text
Create an assignment checklist for an Android GenAI application prototype.
```

```text
Generate five flashcards about Android WebView and mobile security.
```

---

# Application Capabilities

The application demonstrates:

- Android fragment navigation
- Gemini AI integration
- Academic productivity workflows
- Local persistent storage
- Browser-based learning tools
- Student service integration
- Mobile AI-assisted study support
- Modern Android UI workflows

---

# Future Enhancements

## AI & Productivity

- STAR Timetable synchronisation
- Google Calendar integration
- Microsoft Teams integration
- AI-generated reminders
- AI calendar scheduling

## Cloud Features

- Firebase Authentication
- Firebase Firestore
- Cloud file storage
- Cross-device sync

## AI Enhancements

- PDF summarisation
- Lecture note analysis
- Voice prompts
- Offline AI support

---

# Compatibility

Tested on:

- Android API 35
- Android API 36

Compatible with:

- Android Emulator
- Physical Android Devices

---
