LLM-Enhanced Learning Assistant App

Overview

This App is an Android-based learning assistant application developed for SIT708 Task 6.1D. The app demonstrates how Large Language Model (LLM) concepts can be integrated into a mobile learning environment to support personalised learning, adaptive assessments, and intelligent tutoring.

The application allows users to register, select learning interests, attempt quizzes generated via a backend API, and receive AI-supported hints and explanations to improve understanding.

Key Features
1. User Authentication
Login and registration system
Local session management using SharedPreferences
2. Interest-Based Personalisation
Users select learning interests (e.g., Algorithms, Data Structures)
Dashboard generates tasks based on selected interests
3. Backend Quiz Generation
Quiz questions are fetched from the provided Flask backend API
Endpoint used:
GET /getQuiz?topic={topic}
Retrofit is used for API communication
4. LLM-Enhanced Learning Utilities
Generate Hint
Available in Quiz screen
Provides guidance without revealing the answer
Displays both prompt and response

Example Prompt:
Generate a short study hint for this question without revealing the final answer.
Question: Which data structure follows FIFO order?
Options: [Stack, Queue, Tree, Graph]

Example Response:
Think about how elements are processed in order. FIFO means the first element added is the first removed.

Explain Answer
Available in Results screen
Explains why an answer is correct or incorrect
Displays both prompt and response

Example Prompt:
Explain in simple student-friendly language why the selected answer is correct or incorrect.
Question: Which data structure follows FIFO order?
Selected Answer: Stack
Correct Answer: Queue

Example Response:
Your answer is incorrect because a stack follows LIFO order. A queue follows FIFO, where the first element added is removed first.

5. Fallback Learning Mode
If backend fails, demo quiz questions are loaded
Ensures the app remains functional during testing and demonstration
6. Clean UI and Navigation
Readable layout and consistent design
Clear button labels
Proper navigation flow
Loading indicators and error handling
Screens
Login Screen
Register / Setup Screen
Interests Selection Screen
Home Dashboard
Quiz Screen
Results Screen
Backend Integration

Backend runs locally using Flask:

http://localhost:5000/

Android emulator connects via:

http://10.0.2.2:5000/

Retrofit Base URL:

private static final String BASE_URL = "http://10.0.2.2:5000/
";

Project Structure

app/src/main/java/com/example/llm_enhancedlearningassistantapp/

activities/
LoginActivity.java
RegisterActivity.java
InterestsActivity.java
HomeActivity.java
QuizActivity.java
ResultActivity.java

adapters/
InterestAdapter.java

data/
DummyDataProvider.java

llm/
LocalLearningAssistant.java
PromptBuilder.java

models/
Interest.java
QuizApiResponse.java
QuizQuestion.java
UserSession.java

network/
ApiService.java
RetrofitClient.java

utils/
PrefManager.java
ValidationUtils.java

Setup Instructions
Clone the repository
git clone YOUR_REPO_LINK
Open in Android Studio
Open project folder
Sync Gradle
Run Backend API
python main.py
Run the App
Start Android emulator
Click Run in Android Studio
How to Use
Register a new account
Select interests
Go to Home dashboard
Start quiz
Answer questions
Use "Generate Hint"
Submit quiz
View results
Use "Explain Answer"
Continue learning or logout
Testing Checklist
Login and registration works
Interest selection required
Dashboard shows selected topic
Quiz loads from backend
Fallback quiz loads if backend fails
Hint button works
Prompt + response displayed
Submit works correctly
Score calculation correct
Explanation button works
Navigation works
Logout works
Permissions

INTERNET
ACCESS_NETWORK_STATE

Network Configuration

res/xml/network_security_config.xml

Allows HTTP access to local backend during development.

Known Limitations
Login is local (no secure authentication server)
AI responses are simulated (not real LLM API)
Only first question explanation is shown
Backend reliability depends on API
Future Improvements
Real LLM API integration (OpenAI, Gemini)
Explanation for all questions
Flashcards and summaries
Study plan generation
Persistent cloud storage
Improved UI and animations
Responsible Use of AI

AI tools were used to assist with:

understanding task requirements
designing app architecture
structuring code and debugging guidance
improving documentation

All development, implementation, testing, and debugging were completed by the student. AI was used only as a support tool.

Author

Name: Ashan Indika Wijayarathne Hewayalage
Student ID: 224848845
