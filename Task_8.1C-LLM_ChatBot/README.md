# LLM ChatBot Android Application

# Project Overview

LLM ChatBot is an Android mobile application developed using Java and Android Studio. The application integrates Google Gemini AI to provide an intelligent chatbot experience. Users can log in with a username, send messages to the chatbot, receive AI-generated responses, and view persistent chat history stored locally using Room Database (SQLite).


---

# Features

## Implemented Features

* User login screen
* Chat interface using RecyclerView
* Google Gemini AI integration
* Real-time chatbot responses
* Local chat history persistence using Room Database
* Message timestamps
* User and bot chat bubbles
* Modern mobile UI design
* Internet-based AI communication
* Scrollable conversation history

---

# Technologies Used

| Technology        | Purpose                         |
| ----------------- | ------------------------------- |
| Java              | Android application development |
| Android Studio    | Development environment         |
| RecyclerView      | Dynamic chat message display    |
| Room Database     | Local SQLite storage            |
| OkHttp            | API communication               |
| Google Gemini API | AI chatbot responses            |
| XML               | UI layout design                |

---

# Project Structure

```text
app/
 └── src/
     └── main/
         ├── java/com/example/task81c_llmchatbot/
         │   ├── ChatActivity.java
         │   ├── LoginActivity.java
         │   ├── ChatAdapter.java
         │   ├── ChatDatabase.java
         │   ├── Message.java
         │   ├── MessageDao.java
         │   └── LLMService.java
         │
         └── res/
             ├── layout/
             ├── drawable/
             └── values/
```

---

# Application Workflow

## Step 1 – Login Screen

The user enters a username and presses the “Go” button.

## Step 2 – Chat Interface

The user can:

* type messages
* send messages to Gemini AI
* receive chatbot responses

## Step 3 – Local Storage

All messages are automatically saved into the Room Database and reloaded when the application restarts.

---

# Gemini API Integration

The chatbot uses Google Gemini API for AI-generated responses.

## API Used

```text
Google Gemini API
```

## API Communication

The application sends HTTP POST requests using OkHttp and receives JSON responses from Gemini.

---

# How to Run the Application

## Requirements

* Android Studio
* Android SDK 24+
* Internet connection
* Gemini API key

---

## Steps

### 1. Clone the Repository

```bash
git clone https://github.com/s224848845/SIT708-Mobile-Application-Development/tree/main/Task_8.1C-LLM_ChatBot
```

### 2. Open in Android Studio

Open the project folder using Android Studio.

### 3. Add Gemini API Key

Open:

```text
LLMService.java
```

Replace:

```java
private static final String API_KEY = "PASTE_YOUR_GEMINI_API_KEY_HERE";
```

with your actual Gemini API key.

### 4. Run the Application

Connect:

* Android emulator
  OR
* physical Android device

Then press:

```text
Run ▶
```

---

# Database Implementation

The application uses Room Database to persist chat history locally.

## Database Components

| File              | Purpose             |
| ----------------- | ------------------- |
| Message.java      | Entity class        |
| MessageDao.java   | Database operations |
| ChatDatabase.java | Database instance   |

---

# User Interface Design

The UI design includes:

* blue gradient background
* rounded chat bubbles
* modern mobile layout
* responsive chat interface
* timestamps for all messages

---

# Key Functionalities

## Login Validation

The app prevents empty usernames.

## Message Validation

The app prevents sending empty messages.

## Error Handling

The app handles:

* network errors
* API errors
* parsing errors
* timeout errors

---

# Security Note

For academic demonstration purposes, the Gemini API key was temporarily stored in the application source code.

In production applications:

* API keys should never be stored directly in Android apps
* a secure backend server should handle AI communication


---

# Future Improvements

Potential future enhancements include:

* dark mode support
* voice input
* chatbot typing animation
* image generation support
* cloud database synchronization
* chatbot avatars


---

# GitHub Repository

https://github.com/s224848845/SIT708-Mobile-Application-Development/tree/main/Task_8.1C-LLM_ChatBot

---

# Demonstration Link



---


# LLM Conversation Link


---

# Conclusion

The LLM ChatBot Android application successfully demonstrates the integration of a Large Language Model (LLM) into a mobile application environment. The project combines Android UI development, API communication, local database persistence, and intelligent chatbot interaction to create a fully functional AI-powered mobile chat application.
