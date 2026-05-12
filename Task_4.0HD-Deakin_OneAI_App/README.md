# Deakin OneAI – Gemini-Powered Android Learning Assistant

## SIT708 8.2HD Android GenAI App Prototype Implementation and Presentation

Deakin OneAI is a modern Android mobile application prototype developed for SIT708 Mobile Application Development. The application integrates Google Gemini AI into an Android study assistant platform designed to support Deakin University students with academic productivity, study planning, AI-assisted learning, file organisation, calendar planning, browser access, and Deakin student service integration.

This project extends the original 4.0HD Deakin OneAI prototype into a more advanced 8.2HD implementation. The updated version includes a premium UI redesign, Gemini-powered learning utilities, Deakin Hub integration, local academic calendar tools, file workspace support, and a browser workspace.

---

## Key Features

### 1. OneAI Studio

OneAI Studio is the main AI-powered learning area of the app. It uses Gemini AI to support academic learning tasks.

Supported AI features include:

- Ask academic questions
- Summarise lecture notes
- Generate study plans
- Create assignment checklists
- Explain difficult concepts
- Generate revision flashcards

The AI response section includes a user-friendly response card, readable output formatting, copy response support, clear response support, and loading feedback.

---

### 2. Home Dashboard

The Home screen acts as the main productivity dashboard.

Features include:

- Gradient welcome section
- AI quick action cards
- Study planner shortcut
- Productivity snapshot
- Prototype readiness progress bar
- Deakin Hub shortcut
- Embedded Deakin news access

---

### 3. Study Planner

The Study Planner helps students prepare structured plans for assignments and weekly tasks.

Features include:

- Unit name input
- Task name input
- Native Android date picker
- Local study plan generation
- Structured assessment milestone support

Example use cases:

- Assignment planning
- Weekly workload planning
- Demo preparation
- Assessment deadline tracking

---

### 4. Deakin Hub

Deakin Hub provides access to Deakin student services and productivity tools from one workspace.

Deakin Resources:

- CloudDeakin
- OnTrack
- StudentConnect
- DeakinSync
- STAR Timetable

Developer Workspace:

- GitHub Workspace
- Browser Workspace
- Local Study Files

Productivity Tools:

- Weekly Calendar

All resources are opened inside the app using an embedded browser workflow for prototype consistency.

---

### 5. Browser Workspace

The Browser Workspace provides an in-app web browsing feature.

Features include:

- URL input
- Google search support
- Back navigation
- Reload support
- Open current page in Chrome
- Public academic resource browsing

---

### 6. Local Study Files

The Local Study Files section simulates an academic file management workflow.

Features include:

- Simulated academic file upload
- Sample file list
- File tools overview
- Future support for AI summarisation of uploaded notes

Planned future improvements:

- Android Storage Access Framework integration
- PDF parsing
- Gemini document summarisation
- Cloud storage support
- Firebase file sync

---

### 7. Weekly Calendar

The Weekly Calendar provides a Microsoft Teams-style academic calendar workflow.

Features include:

- Preloaded sample academic events
- Add new calendar events
- Date picker integration
- Time picker integration
- Event type selection
- Dynamic event cards
- Reset sample events option

Supported event types:

- Lecture
- Workshop
- Tutorial
- Study Block
- Assessment
- Meeting

Planned future improvements:

- STAR Timetable sync
- Google Calendar API integration
- Microsoft Teams calendar sync
- Reminder notifications
- Firebase calendar storage

---

## Gemini AI Integration

The app uses Gemini AI in a hybrid mobile and cloud architecture.

### Integration Mode

Hybrid mode:

- Android app provides the mobile interface
- User prompt is entered inside the app
- Prompt is sent to Gemini API
- Gemini response is displayed inside OneAI Studio

### AI Use Cases

Gemini is used for learning-focused utilities, not only general chat. The AI features are designed to improve the student learning workflow by supporting summarisation, planning, explanation, checklist generation, and revision preparation.

---

## Privacy and Safety

The app includes privacy and safety handling to support responsible AI use.

### Privacy Notice

The app warns users not to enter:

- Passwords
- Student ID numbers
- Private documents
- Medical information
- Sensitive personal data

### Safety Handling

Unsafe prompts are checked locally before being sent to Gemini. Prompts related to malware, phishing, credential theft, bypassing login systems, illegal activity, or exploit instructions are rejected with a safety notice.

---

## Android Compatibility

The app is designed for modern Android compatibility.

Recommended testing:

- API 35 emulator or device
- API 36 emulator or device

Compatibility features:

- Modern Fragment-based navigation
- BottomNavigationView
- WebView support
- Android date picker
- Android time picker
- Android 16-compatible back navigation approach using OnBackPressedDispatcher

---

## UI and UX Improvements

The UI was redesigned to create a more polished HD-level prototype.

Design improvements include:

- Premium gradient header
- Rounded modern cards
- Consistent spacing
- Floating bottom navigation style
- Improved typography hierarchy
- Scrollable layouts
- Icon-based cards
- Improved AI response display
- User-friendly Hub structure
- Teams-style calendar interface

---

## Technologies Used

Android development:

- Java
- Android Studio
- Android SDK
- Fragments
- Material Components
- BottomNavigationView
- ScrollView
- WebView
- DatePickerDialog
- TimePickerDialog
- AlertDialog

AI and networking:

- Google Gemini API
- JSON request and response handling
- HTTP networking
- Prompt safety handling

---

## Project Structure

```text
app/
└── src/
    └── main/
        ├── java/
        │   └── com/example/sit708_40hd_deakin_oneai_app/
        │       ├── adapters/
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
        │       ├── models/
        │       ├── utils/
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

If the app uses `local.properties`, add:

GEMINI_API_KEY=PASTE_YOUR_GEMINI_API_KEY_HERE


Do not commit the real API key to GitHub.

---

## 3. Sync Gradle

In Android Studio, select:

```text
File > Sync Project with Gradle Files
```

---

## 4. Run the App

Run the app using:

- Android emulator
- Physical Android device

Recommended emulator versions:

- API 35
- API 36

---

# Suggested Demo Flow

1. Open the app and show the Home dashboard.
2. Explain the premium UI redesign and productivity dashboard.
3. Open OneAI Studio.
4. Enter an academic prompt and generate a Gemini response.
5. Demonstrate copy and clear response buttons.
6. Open Study Planner and generate a local study plan using the date picker.
7. Open Deakin Hub and show Deakin Resources.
8. Open STAR Timetable from Deakin Hub.
9. Open Browser Workspace and search for an academic resource.
10. Open Local Study Files and simulate file upload.
11. Open Weekly Calendar and add a new event using date and time pickers.
12. Open About screen and explain privacy, safety, compatibility, and future work.

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

# Development Evidence

The application demonstrates iterative development through:

- Original 4.0HD prototype extension
- Premium UI redesign
- Gemini API integration
- Deakin Hub expansion
- Browser workspace implementation
- Local study files module
- Weekly calendar implementation
- API 35 and API 36 emulator testing
- Git branch-based development workflow

---

# Future Work

Future improvements include:

- Real Deakin authentication integration
- Secure SSO-based login flow
- STAR Timetable data sync
- Google Calendar API support
- Microsoft Teams calendar integration
- Firebase Authentication
- Firebase Firestore storage
- Cloud file upload
- PDF text extraction
- Gemini-powered document summarisation
- Offline AI model support
- Push notification reminders
- Accessibility testing
- UI testing and unit testing
