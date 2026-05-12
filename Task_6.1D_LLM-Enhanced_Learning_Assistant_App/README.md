# LLM-Enhanced Learning Assistant App

## Introduction

The LLM-Enhanced Learning Assistant App is an Android mobile learning application developed for SIT708 – Mobile Application Development (Task 6.1D). The purpose of this project is to demonstrate how Large Language Model (LLM) concepts can be integrated into a mobile learning environment to improve educational support and student engagement.

The application combines Android frontend development with Python Flask backend integration to provide personalized quizzes, AI-assisted hints, and AI-generated answer explanations. The application focuses on learning enhancement rather than normal chatbot interaction.

The frontend was developed using Java in Android Studio, while the backend APIs were implemented using Flask Python and integrated with a Llama-based model service structure provided in the T-6.1D backend resources.

---

## Application Features

### 1. User Authentication

The application includes login and registration functionality.

Features:

- User registration
- User login
- Input validation
- Local session persistence using SharedPreferences
- Logout functionality

The authentication system allows users to securely access personalized learning features within the application.

---

### 2. Interest Selection System

After registration, users can select preferred learning interests such as:

- Algorithms
- Data Structures
- Databases
- Web Development
- Mobile Development
- Cloud Computing
- Cybersecurity
- Testing

These interests are used to personalize the quiz generation process and create an adaptive learning experience.

---

### 3. Personalized Learning Dashboard

The Home screen acts as the main learning dashboard.

Features:

- Personalized welcome message
- Dynamically generated learning task
- Quiz launch functionality
- Navigation to learning activities

The dashboard content changes according to the selected learning interests.

---

### 4. Backend API Integration

The application integrates with a Flask backend using Retrofit API communication.

Implemented API endpoints:

- GET /getQuiz
- POST /getHint
- POST /explainAnswer
- GET /test

The Android application communicates with the backend through Retrofit and Gson JSON parsing.

Backend URL for Android Emulator:
http://10.0.2.2:5001/

---

### 5. Quiz Generation Feature

Quiz questions are dynamically retrieved from the backend server based on the selected learning topic.

Features:

- Multiple-choice questions
- Four answer options
- Topic-based quiz generation
- Dynamic question loading
- Navigation between questions
- Score calculation

If the backend or external AI service becomes unavailable, the application automatically loads fallback educational content to maintain usability and prevent application crashes.

---

### 6. LLM-Powered Learning Utilities

The application implements two LLM-powered learning utilities as required by the task sheet.

---

### A. Generate Hint for a Question

The Quiz screen contains a "Generate Hint" feature.

Workflow:

1. The frontend sends the current question and options to the backend /getHint endpoint.
2. The backend creates an educational learning prompt.
3. The generated prompt is displayed in the UI.
4. The learning hint response is displayed below the prompt.

Purpose:

- Encourage critical thinking
- Guide the student without directly revealing answers
- Improve concept understanding

Example:

Prompt:
Generate a short study hint for this question without revealing the final answer.

Response:
Focus on the main concept being tested and eliminate incorrect options first.

---

### B. Explain Why an Answer is Correct or Incorrect

The Result screen contains an "Explain First Answer" feature.

Workflow:

1. The frontend sends the question, selected answer, and correct answer to the backend /explainAnswer endpoint.
2. The backend builds a learning-oriented explanation prompt.
3. The generated prompt is displayed in the UI.
4. he educational explanation is displayed below the prompt.

Purpose:

- Reinforce learning concepts
- Help students understand mistakes
- Improve conceptual understanding

Example:

Prompt:
Explain why the selected answer is incorrect.

Response:
The selected answer does not fully match the expected concept, while the correct answer follows the required behaviour of the data structure.
---

### 7. Prompt and Response Visualization

The application displays:

- Generated prompts
- AI-generated responses
- Educational explanations
- Fallback learning responses

This allows users to understand how the learning interaction is generated internally.

---

### 8. Loading and Failure Handling

The application implements loading and failure-state handling mechanisms.

Features:

- Backend connection validation
- Error message handling
- Fallback educational responses
- Stable navigation during API failures

If the external LLM service is unavailable, the backend returns educational fallback responses instead of crashing the application.

---

### 9. Navigation and User Experience

The application follows consistent screen navigation.

Navigation flow:
Login → Register → Interests → Home → Quiz → Results → Home

Features:

- Back button support
- Logout support
- No dead-end screens
- Readable text sizes
- Meaningful button labels
- User-friendly layouts

## Technical Implementation

### Frontend Technologies

- Android Studio
- Java
- XML Layouts
- RecyclerView
- SharedPreferences
- Retrofit
- Gson

---

### Backend Technologies

- Python
- Flask
- GradientAI / Llama model structure
- REST API endpoints


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
- UserSession

network/
- ApiService
- RetrofitClient

utils/
- PrefManager
- ValidationUtils

backend/

- main.py
- Flask API server
- LLM endpoint handling

---

## How to Run the App

1. Open the project in Android Studio
2. Sync Gradle
3. Start the backend server (python main.py)

Open terminal inside backend folder.

Activate virtual environment:

Mac/Linux:
source venv/bin/activate

Run backend:
python main.py

Backend will run on:
http://127.0.0.1:5001
  
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

The following features were tested successfully:

- Registration validation
- Login validation
- Session persistence
- Interest selection
- Personalized dashboard
- Backend API communication
- Quiz retrieval
- Hint generation
- Explanation generation
- Prompt display
- Response display
- Loading/failure handling
- Score calculation
- Navigation flow
-Logout functionality

---

## Limitations

Current limitations include:

- Backend hosted locally
- External GradientAI service may become unavailable
- Fallback educational responses used during API failures
- Explanation currently demonstrated for first answer only

---

## Future Improvements

Potential future enhancements include:

- Real Gemini/OpenAI API integration
- Firebase authentication
- Cloud-hosted backend deployment
- Improved UI animations and themes

---




