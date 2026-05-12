from gradientai import Gradient
import os
from flask import Flask, request, jsonify
import re


# Gradient AI credentials
# For a real production app, these should be stored in environment
# variables instead of being hardcoded in the file.
token = 'LXkZBp5a1nGCP16xM7QieYKNz2Ns0tOW'
workspace_id = 'b5f958d9-ffd4-41bb-a492-704114e02c8e_workspace'

os.environ['GRADIENT_ACCESS_TOKEN'] = token
os.environ['GRADIENT_WORKSPACE_ID'] = workspace_id

app = Flask(__name__)

# Initialize the Gradient client
gradient = Gradient()



# Home route used to confirm that the backend is running.
@app.route('/', methods=['GET'])
def home():
    return jsonify({
        "message": "T-6.1D LLM Learning Assistant Backend is running",
        "available_endpoints": [
            "GET /getQuiz?topic=Algorithms",
            "POST /getHint",
            "POST /explainAnswer",
            "GET /test"
        ]
    }), 200


# Fetch quiz questions from Llama model.
# This endpoint supports the Android app quiz screen.

def fetchQuizFromLlama(student_topic):
    print("Fetching quiz from Llama model...")

    base_model = gradient.get_base_model(base_model_slug="llama3-8b-chat")

    query = (
        "[INST] Generate a quiz with exactly 3 questions to test students on the provided topic. "
        "For each question, generate exactly 4 options where only one option is correct. "
        "Use the exact format below for each question:\n\n"
        "QUESTION: [Your question here]?\n"
        "OPTION A: [First option]\n"
        "OPTION B: [Second option]\n"
        "OPTION C: [Third option]\n"
        "OPTION D: [Fourth option]\n"
        "ANS: [A/B/C/D]\n\n"
        "Do not add extra explanation. Do not add numbering. "
        "Follow the same pattern for all 3 questions.\n\n"
        f"Student topic: {student_topic}\n"
        "[/INST]"
    )

    response = base_model.complete(
        query=query,
        max_generated_token_count=700
    ).generated_output

    print("Raw quiz response:")
    print(response)

    return response



# Convert the raw LLM quiz text into JSON format for Android.
def process_quiz(quiz_text):
    questions = []

    # This pattern extracts question, four options, and answer letter.
    pattern = re.compile(
        r'QUESTION:\s*(.*?)\s*'
        r'OPTION A:\s*(.*?)\s*'
        r'OPTION B:\s*(.*?)\s*'
        r'OPTION C:\s*(.*?)\s*'
        r'OPTION D:\s*(.*?)\s*'
        r'ANS:\s*([A-D])',
        re.DOTALL | re.IGNORECASE
    )

    matches = pattern.findall(quiz_text)

    for match in matches:
        question = match[0].strip()
        options = [
            match[1].strip(),
            match[2].strip(),
            match[3].strip(),
            match[4].strip()
        ]
        correct_ans = match[5].strip().upper()

        question_data = {
            "question": question,
            "options": options,
            "correct_answer": correct_ans
        }

        questions.append(question_data)

    return questions


# Main quiz endpoint used by Android Retrofit.
# Example:
# http://127.0.0.1:5001/getQuiz?topic=Algorithms
# Android emulator uses:
# http://10.0.2.2:5001/getQuiz?topic=Algorithms
@app.route('/getQuiz', methods=['GET'])
def get_quiz():
    print("Quiz request received")

    student_topic = request.args.get('topic')

    if student_topic is None or student_topic.strip() == "":
        return jsonify({'error': 'Missing topic parameter'}), 400

    try:
        quiz_text = fetchQuizFromLlama(student_topic)
        quiz_json = process_quiz(quiz_text)

        if len(quiz_json) == 0:
            return jsonify({
                'error': 'The LLM response could not be parsed into quiz format.',
                'raw_response': quiz_text,
                'quiz': []
            }), 200

        return jsonify({'quiz': quiz_json}), 200

    except Exception as e:
        print("Error generating quiz:", str(e))
        return jsonify({
            'error': 'Failed to generate quiz',
            'details': str(e),
            'quiz': []
        }), 500



# LLM Utility 1: Generate hint for a question.
# This supports the required learning utility:
# "Generate hint for a question"
@app.route('/getHint', methods=['POST'])
def get_hint():
    print("Hint request received")

    data = request.get_json()

    if not data:
        return jsonify({'error': 'Missing JSON body'}), 400

    question = data.get('question')
    options = data.get('options')

    if not question or not options:
        return jsonify({'error': 'Missing question or options'}), 400

    prompt = (
        "[INST] You are a helpful learning assistant for students. "
        "Generate one short hint for the following multiple-choice question. "
        "Do not reveal the final answer. "
        "The hint should guide the student to think about the key concept.\n\n"
        f"Question: {question}\n"
        f"Options: {options}\n"
        "[/INST]"
    )

    try:
        base_model = gradient.get_base_model(base_model_slug="llama3-8b-chat")

        response = base_model.complete(
            query=prompt,
            max_generated_token_count=180
        ).generated_output

        return jsonify({
            'prompt': prompt,
            'response': response
        }), 200

    except Exception as e:
        print("Error generating hint:", str(e))

        fallback_response = (
            "Hint: Focus on the main concept in the question. "
            "Remove the options that clearly do not match the expected behaviour, "
            "then compare the remaining choices carefully."
        )

        return jsonify({
            'prompt': prompt,
            'response': fallback_response,
            'note': 'Fallback learning response used because the external LLM service was unavailable.'
        }), 200



# LLM Utility 2: Explain why an answer is correct or incorrect.
# This supports the required learning utility:
# "Explain why an answer is correct/incorrect"

@app.route('/explainAnswer', methods=['POST'])
def explain_answer():
    print("Explanation request received")

    data = request.get_json()

    if not data:
        return jsonify({'error': 'Missing JSON body'}), 400

    question = data.get('question')
    selected_answer = data.get('selectedAnswer')
    correct_answer = data.get('correctAnswer')

    if not question or not selected_answer or not correct_answer:
        return jsonify({'error': 'Missing required answer details'}), 400

    prompt = (
        "[INST] You are a learning assistant for beginner students. "
        "Explain in simple student-friendly language why the selected answer is correct or incorrect. "
        "Clearly mention the correct concept, but keep the explanation short.\n\n"
        f"Question: {question}\n"
        f"Selected Answer: {selected_answer}\n"
        f"Correct Answer: {correct_answer}\n"
        "[/INST]"
    )

    try:
        base_model = gradient.get_base_model(base_model_slug="llama3-8b-chat")

        response = base_model.complete(
            query=prompt,
            max_generated_token_count=220
        ).generated_output

        return jsonify({
            'prompt': prompt,
            'response': response
        }), 200

    except Exception as e:
        print("Error generating explanation:", str(e))

        fallback_response = (
            "Explanation: Compare the selected answer with the correct answer. "
            "The correct answer matches the key concept being tested, while the selected answer "
            "does not fully follow the expected rule or definition."
        )

        return jsonify({
            'prompt': prompt,
            'response': fallback_response,
            'note': 'Fallback learning response used because the external LLM service was unavailable.'
        }), 200


# Simple test route

@app.route('/test', methods=['GET'])
def run_test():
    return jsonify({'message': "Backend test route working"}), 200



# Start backend server

if __name__ == '__main__':
    port_num = 5001
    print(f"App running on port {port_num}")
    app.run(port=port_num, host="0.0.0.0", debug=True)