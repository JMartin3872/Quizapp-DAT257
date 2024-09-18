package com.example;
import java.util.List;
import java.util.*;

public class QuizGameModel {

    // A HashMap to hold different types of questions, for now just of type multiple choice
    // Each question has it's questionId as the key and the Question object as the value
    private Map<Integer, Question> questionMap = new HashMap<>();
    private int currentQuestionId;
    private int score = 0;

    // Constructor to initialize the quiz with a list of questions
    public QuizGameModel(List<Question> questions) {
        for (Question question : questions) {
            questionMap.put(question.getQuestionId(), question);
        }

        // Set the first question as the current question based on its ID
        if (!questions.isEmpty()) {
            this.currentQuestionId = questions.get(0).getQuestionId();
        }
    }

    // Returns the current question based on the currentQuestionId
    public Question getCurrentQuestion() {
        return questionMap.get(currentQuestionId);
    }

    // Handles the user's answer and returns true if correct, false otherwise
    public boolean answerCurrentQuestion(String userAnswer) {
        Question currentQuestion = getCurrentQuestion();

        if (currentQuestion == null) {
            return false; // No question to answer
        }

        // For now, we only have multiple choice questions
        if (currentQuestion instanceof QuestionMultipleChoices) {
            QuestionMultipleChoices mcq = (QuestionMultipleChoices) currentQuestion;
            if (mcq.getCorrectAnswer().equals(userAnswer)) {
                score++; // Increment score for correct answer
                return true;
            }
        }
        return false; // Incorrect answer
    }

    // Moves to the next question, if there is a next question
    public void nextQuestion() {
        if(questionMap.containsKey(currentQuestionId + 1)) {
            currentQuestionId++;
        }
    }

    // Returns the current score
    public int getScore() {
        return score;
    }

    // Returns true if the quiz is finished, false otherwise
    // If there is no next question after the current question, the quiz is finished
    public boolean isFinished() {
        return !questionMap.containsKey(currentQuestionId + 1);
    }

    // Returns the total number of questions
    public int getTotalQuestions() {
        return questionMap.size();
    }
}