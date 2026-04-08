# 📱 SIT708 Quiz Application – Task 3.1C

## 📌 Overview
This project is an Android-based Quiz Application developed for SIT708 – Mobile Application Development.  
The application allows users to enter their name, attempt a multiple-choice quiz, receive instant feedback, track their progress in real-time, and view final results.

The app also includes a Dark Mode / Light Mode toggle to improve usability and accessibility.

---

## 🎯 Key Features

### User Input & Navigation
- User enters name before starting the quiz
- Smooth navigation across:
    - Main Screen
    - Quiz Screen
    - Result Screen

### Quiz Functionality
- Multiple-choice questions (4 options)
- One question displayed at a time
- Submit button to confirm answer
- Next button to move to next question

### Answer Feedback
- Correct answer → shown in GREEN
- Incorrect selected answer → shown in RED
- Correct answer highlighted even if wrong selected
- Answers cannot be changed after submission

### Progress Tracking
- Horizontal ProgressBar
- Displays:
    - Current question number
    - Completion percentage

### Results Screen
- Displays final score
- Personalized message using user name
- Buttons:
    - Take New Quiz (returns to main screen)
    - Finish (closes app)

### Data Persistence
- User name stored using SharedPreferences
- Name is retained when restarting quiz

### Theme Switching (SIT708 Requirement)
- Light Mode / Dark Mode toggle
- Theme updates instantly
- Theme persists across all screens

---

## 🧱 Application Architecture

MainActivity      → Handles user input and navigation  
QuizActivity      → Handles quiz logic and UI interaction  
ResultActivity    → Displays results and navigation options

Question          → Model class for quiz data  
QuizRepository    → Provides question list

---

## 🛠️ Technologies Used

- Android Studio
- Java
- XML Layouts
- Material Components
- SharedPreferences
- AppCompatDelegate (Theme control)

---

## 📂 Project Structure

app/
├── java/com/example/task_31c_quiz_app/
│   ├── MainActivity.java
│   ├── QuizActivity.java
│   ├── ResultActivity.java
│   ├── Question.java
│   ├── QuizRepository.java
│
├── res/layout/
│   ├── activity_main.xml
│   ├── activity_quiz.xml
│   ├── activity_result.xml
│
├── res/values/
│   ├── strings.xml
│   ├── colors.xml
│   ├── themes.xml
│
├── res/values-night/
│   ├── themes.xml
│
└── AndroidManifest.xml

---

## ▶️ How to Run

1. Open the project in Android Studio
2. Sync Gradle dependencies
3. Connect an emulator or Android device
4. Click Run ▶️
5. Use the app:
    - Enter name
    - Start quiz
    - Submit answers
    - View results

---

## 🧪 Testing Checklist

- Name input validation works
- Quiz loads correctly
- Answer feedback (green/red) works
- Answers cannot be changed after submission
- Progress bar updates correctly
- Score calculation is accurate
- Result screen displays correct data
- Name persists when restarting quiz
- Theme toggle works across all screens

---

## 🤖 AI Usage Declaration

Generative AI tools were used as supplementary learning support to:
- Understand Android architecture
- Design quiz logic
- Debug implementation issues
- Improve documentation clarity

All implementation, testing, and final submission were completed independently in accordance with academic integrity requirements.

---

## 📊 Future Improvements

- Dynamic question generation using AI
- Database integration (Room / Firebase)
- Timer-based quiz mode
- Leaderboard system
- Voice-controlled interaction
- Performance analytics dashboard

---

## 👨‍💻 Author

Name: Ashan Indika Wijayarathne Hewayalage
Unit: SIT708 – Mobile Application Development  
Trimester: T1 2026  
Institution: Deakin University

---

## 📌 Submission Note

This project satisfies all mandatory requirements for Task 3.1C, including:
- Quiz functionality
- Answer validation
- Progress tracking
- Result management
- Theme switching
- Research component on on-device LLM integration