# LLM-Enhanced Learning Assistant App

## Introduction

This App is an Android mobile application developed for SIT708 Task 6.1D. The aim of this project is to demonstrate how AI (Large Language Model concepts) can be integrated into a learning application to support students in understanding topics more effectively.

The app allows users to create an account, select their learning interests, attempt quizzes, and receive AI-style hints and explanations to improve their understanding.

---

## Application Features

### 1. Login and Registration

The app includes a simple login and registration system where users can:
- Create an account by entering basic details
- Log in using a username and password
- Store user information locally using SharedPreferences

---

### 2. Interest Selection

After registration, users select their learning interests such as:
- Algorithms
- Data Structures
- Web Development
- Testing
- Databases

These interests are used to personalise the learning experience.

---

### 3. Home Dashboard

The home screen displays:
- A welcome message with the user’s name
- A generated learning task based on selected interests
- A button to start the quiz

---

### 4. Quiz Feature (Backend Integration)

The quiz questions are fetched from the provided backend API using Retrofit.

API used:
GET /getQuiz?topic={topic}

If the backend is not available or returns empty data, the app automatically loads a fallback demo quiz to ensure the application continues to work.

---

### 5. AI Learning Support (LLM Features)

This application includes two AI-supported learning features as required by the task.

#### a) Generate Hint

Users can tap the **"Generate Hint"** button during a quiz.

The app:
- Creates a learning prompt based on the current question
- Displays the prompt in the UI
- Shows a helpful hint response

Example:

Prompt:
Generate a short study hint for this question without revealing the final answer.

Response:
Think about how the concept works and eliminate incorrect options first.

---

#### b) Explain Answer

After completing the quiz, users can tap **"Explain First Answer"**.

The app:
- Builds a prompt using the selected and correct answers
- Displays the prompt in the UI
- Shows an explanation to help the student understand the concept

Example:

Prompt:
Explain why the selected answer is incorrect.

Response:
The selected answer does not follow the correct concept. The correct answer aligns with the expected behaviour of the data structure.

---

### 6. Results Screen

The results screen shows:
- Total score
- Summary message
- Option to generate explanation
- Continue button to return to home screen

---

### 7. Navigation

The app includes proper navigation:
- Login → Register → Interests → Home → Quiz → Results → Home
- Back button works correctly
- Logout returns to login screen

---

## Technical Implementation

### Technologies Used

- Android Studio (Java)
- XML layouts
- Retrofit (API communication)
- Gson (JSON parsing)
- SharedPreferences (local storage)

---

### Backend Configuration

The backend runs locally using Flask.

Backend URL:
http://localhost:5000/

For Android Emulator:
http://10.0.2.2:5000/

---

### Project Structure

activities/
- LoginActivity
- RegisterActivity
- InterestsActivity
- HomeActivity
- QuizActivity
- ResultActivity

llm/
- PromptBuilder
- LocalLearningAssistant

models/
- QuizQuestion
- QuizApiResponse
- Interest

network/
- ApiService
- RetrofitClient

utils/
- PrefManager
- ValidationUtils

---

## How to Run the App

1. Open the project in Android Studio
2. Sync Gradle
3. Start the backend server (python main.py)
4. Run the app on emulator or device

---

## How to Use the App

1. Register a new account
2. Select your interests
3. Open the dashboard
4. Start the quiz
5. Answer questions
6. Use "Generate Hint" if needed
7. Submit the quiz
8. View results
9. Use "Explain Answer"
10. Continue learning

---

## Testing Summary

The following functionalities were tested:

- User registration and login
- Interest selection validation
- Dashboard loading
- Quiz loading from backend
- Fallback quiz loading
- Answer selection and navigation
- Hint generation
- Explanation generation
- Score calculation
- Navigation between screens
- Logout functionality

---

## Limitations

- Login system uses local storage only
- AI responses are simulated (not real LLM API)
- Only the first question explanation is shown
- Backend reliability depends on API

---

## Future Improvements

- Integrate real LLM APIs (OpenAI / Gemini)
- Add explanations for all questions
- Add flashcards and summaries
- Add study plan generation
- Improve UI with animations

---

## AI Usage Declaration

AI tools were used to assist in:
- understanding the task requirements
- designing the app structure
- generating code guidance
- improving documentation

All implementation, testing, and debugging were completed by the student.

---

## Author

Name: Ashan Indika Wijayarathne Hewayalage
Student ID: 224848845



