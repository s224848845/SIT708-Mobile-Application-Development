# LLM-Enhanced Learning Assistant App

## Introduction

The LLM-Enhanced Learning Assistant App is an Android-based educational mobile application developed for SIT708 – Mobile Application Development (Task 10.1D). The project demonstrates how Large Language Model (LLM) concepts and AI-assisted learning workflows can be integrated into a modern mobile learning environment to improve student engagement, adaptive learning, and educational support.

The application combines Android frontend development with Python Flask backend integration to provide personalized quizzes, AI-powered learning hints, AI-generated answer explanations, quiz history tracking, learner profile analytics, sharing functionality, and simulated premium learning upgrades. The focus of the application is educational assistance and concept reinforcement rather than traditional chatbot interaction.

The frontend was developed using Java in Android Studio, while the backend APIs were implemented using Flask Python and integrated with a Llama-based model service structure provided in the SIT708 backend resources.

---

# Application Features

## 1. User Authentication System

The application includes a complete authentication workflow.

### Features

- User registration
- User login
- Input validation
- SharedPreferences session persistence
- Secure logout functionality
- Persistent user profile information

The authentication system allows users to securely access personalized learning content and maintain learning progress across sessions.

---

## 2. Interest Selection and Personalization

Users can select preferred learning domains after registration.

### Available Learning Interests

- Algorithms
- Data Structures
- Databases
- Mobile Development
- Web Development
- Cloud Computing
- Cybersecurity
- Software Testing

The selected interests are used to personalize quiz generation and adaptive learning workflows throughout the application.

---

## 3. Enhanced Learning Dashboard

The Home screen functions as the primary personalized learning dashboard.

### Features

- Personalized welcome message
- Dynamic learning task generation
- Personalized quiz recommendations
- Current subscription plan display
- Navigation to Profile, History, and Upgrade modules
- Enhanced UI with modern gradient design

The dashboard dynamically adapts based on user-selected interests and account activity.

---

## 4. Backend API Integration

The application integrates with a Flask backend server using Retrofit API communication.

### Implemented API Endpoints

- `GET /getQuiz`
- `POST /getHint`
- `POST /explainAnswer`
- `GET /test`

### Emulator Backend URL

```text
http://10.0.2.2:5001/
```

### Features

- Retrofit API communication
- Gson JSON serialization/deserialization
- Backend availability validation
- Fallback educational responses
- Stable API error handling

The frontend communicates with the Flask backend to retrieve quizzes, AI hints, and AI-generated educational explanations.

---

## 5. Dynamic Quiz Generation

Quiz questions are dynamically retrieved from the backend according to the selected topic and learning interests.

### Features

- Multiple-choice questions
- Four answer options
- Topic-based quiz generation
- Dynamic loading from backend APIs
- Question navigation
- Score calculation
- Result persistence

The system supports both live AI-assisted content and educational fallback content when external AI services are unavailable.

---

## 6. LLM-Powered Learning Utilities

The application implements multiple AI-enhanced learning utilities designed to improve conceptual understanding and educational engagement.

---

### A. AI-Powered Hint Generation

The Quiz screen contains a “Generate Hint” learning utility.

#### Workflow

1. The frontend sends the current question and answer options to the `/getHint` endpoint.
2. The backend constructs an educational learning prompt.
3. The generated prompt is displayed in the interface.
4. The AI-generated educational hint is displayed below the prompt.

#### Educational Purpose

- Encourage critical thinking
- Assist without directly revealing answers
- Improve conceptual understanding
- Promote guided learning

#### Example

##### Prompt

```text
Generate a short study hint for this question without revealing the final answer.
```

##### Response

```text
Focus on the main concept being tested and eliminate incorrect options first.
```

---

### B. AI Answer Explanation System

The Result screen contains an AI-generated explanation utility.

#### Workflow

1. The frontend sends the question, selected answer, and correct answer to the `/explainAnswer` endpoint.
2. The backend generates a learning-oriented explanation prompt.
3. The generated prompt is displayed in the UI.
4. The educational explanation is displayed below the prompt.

#### Educational Purpose

- Reinforce learning concepts
- Help students understand mistakes
- Improve retention and conceptual understanding
- Provide reflective learning support

#### Example

##### Prompt

```text
Explain why the selected answer is incorrect.
```

##### Response

```text
The selected answer does not fully match the expected concept, while the correct answer follows the required behaviour of the data structure.
```

---

## 7. Learner Profile System

The application includes a complete learner profile module.

### Features

- Personalized learner profile
- Account plan tracking
- Total questions attempted
- Correct answer statistics
- Incorrect answer statistics
- Learning progress visualization
- Share Profile functionality

The Profile screen acts as a lightweight learner analytics dashboard for monitoring educational progress.

---

## 8. Quiz History Tracking

The application includes persistent quiz history tracking functionality.

### Features

- Quiz result storage
- Topic tracking
- Score tracking
- Timestamp tracking
- Detailed answer review
- Correct vs incorrect answer analysis

The History screen enables learners to review previous quiz attempts and monitor improvement over time.

---

## 9. Upgrade Account Simulation

The application includes an enhanced premium upgrade simulation workflow inspired by modern mobile applications.

### Features

- Multi-tier account plans
- Starter plan
- Intermediate plan
- Advanced plan
- Google Pay–style payment sheet
- Interactive payment fields
- Purchase confirmation workflow
- Account plan persistence

### Educational Purpose

This feature demonstrates advanced Android UI/UX implementation and modern mobile interaction patterns.

---

## 10. Share Profile Functionality

The Profile screen contains a Share Profile feature.

### Features

- Android share sheet integration
- Learner profile summary export
- Quiz statistics sharing
- Account plan sharing
- Native Android sharing workflow

This demonstrates Android Intent integration and real-world mobile application sharing capabilities.

---

## 11. Prompt and Response Visualization

The application displays:

- Generated AI prompts
- AI-generated responses
- Educational explanations
- Learning hints
- Fallback learning responses

This allows users to understand how AI-assisted learning interactions are internally generated.

---

## 12. Loading, Error, and Failure Handling

The application implements stable loading and failure-handling mechanisms.

### Features

- Backend connection validation
- Error handling
- Fallback educational responses
- Stable navigation during failures
- Prevention of application crashes
- Graceful UI recovery

If the external LLM service becomes unavailable, fallback educational content is automatically displayed to maintain usability.

---

## 13. Enhanced UI and User Experience

The application includes significant UI and UX enhancements.

### Features

- Modern gradient UI
- Rounded card layouts
- Interactive payment sheet
- Modern navigation buttons
- Professional mobile styling
- Responsive layouts
- Improved readability
- Smooth navigation flow
- Consistent visual design

### Navigation Flow

```text
Login → Register → Interests → Home → Quiz → Results → Profile / History / Upgrade
```

---

# Technical Implementation

## Frontend Technologies

- Android Studio
- Java
- XML Layouts
- RecyclerView
- SharedPreferences
- Retrofit
- Gson
- Android Intents
- Dialog UI Components

---

## Backend Technologies

- Python
- Flask
- REST APIs
- Llama-based backend structure
- GradientAI service structure

---

# Project Structure

```text
activities/
- LoginActivity
- RegisterActivity
- InterestsActivity
- HomeActivity
- QuizActivity
- ResultActivity
- ProfileActivity
- HistoryActivity
- UpgradeActivity

llm/
- PromptBuilder
- LocalLearningAssistant

models/
- QuizQuestion
- QuizApiResponse
- Interest
- UserSession
- QuizHistory

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
```

---

# How to Run the Application

## 1. Open Project

Open the project using Android Studio.

---

## 2. Sync Gradle

Allow Android Studio to complete Gradle synchronization.

---

## 3. Start Backend Server

Open terminal inside the backend folder.

### Activate Virtual Environment

#### Mac/Linux

```bash
source venv/bin/activate
```

### Run Backend

```bash
python main.py
```

The backend will run on:

```text
http://127.0.0.1:5001
```

---

## 4. Run Android Application

Run the Android application using an emulator or physical Android device.

---

# How to Use the Application

1. Register a new account
2. Login to the application
3. Select learning interests
4. Open the personalized dashboard
5. Start the generated quiz
6. Use “Generate Hint” if needed
7. Submit quiz answers
8. View AI-generated explanations
9. Review Profile statistics
10. Review Quiz History
11. Test Upgrade workflow
12. Share learner profile

---

# Testing Summary

The following features were successfully tested:

- Registration validation
- Login validation
- Session persistence
- Interest selection
- Personalized dashboard
- Backend API communication
- Quiz retrieval
- Hint generation
- AI explanation generation
- Prompt visualization
- Response visualization
- Quiz score calculation
- Quiz history persistence
- Profile statistics tracking
- Share Profile functionality
- Upgrade workflow simulation
- Interactive payment sheet
- Navigation flow
- Logout functionality
- Failure handling
- Fallback educational responses

---

# Limitations

Current limitations include:

- Backend hosted locally
- External AI service availability may vary
- Payment system is a prototype simulation only
- Educational fallback responses used during API failures
- AI explanation demonstration currently limited to selected answers

---

# Future Improvements

Potential future enhancements include:

- Real Gemini/OpenAI API integration
- Firebase Authentication
- Cloud-hosted backend deployment
- Real database integration
- Advanced learner analytics
- Dark mode support
- AI study recommendations
- Real payment gateway integration
- Advanced animations and transitions

---

# Conclusion

The LLM-Enhanced Learning Assistant App successfully demonstrates how AI-assisted educational workflows can be integrated into Android mobile applications to improve student engagement, adaptive learning, and concept understanding. The project combines Android development, backend API integration, AI-assisted learning utilities, persistent local storage, and modern mobile UI/UX practices into a complete educational prototype suitable for intelligent mobile learning environments.
