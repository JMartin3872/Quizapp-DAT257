package com.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizGameModel {
    private Map<Integer, Question> questionMap = new HashMap<>();
    private int currentQuestionId = 0; // Default to the first question

    public QuizGameModel() {
        // temporary solution for multiple choice questions :)
        questionMap.put(1, new QuestionMultipleChoices("What is the capital of France?", "Paris", "London", "Berlin", "Paris is known as the city of love.", 1));
        questionMap.put(2, new QuestionMultipleChoices("What is the largest planet in our solar system?", "Jupiter", "Earth", "Mars", "Jupiter is a gas giant.", 2));
    }

    // Constructor with list of questions
    public QuizGameModel(List<Question> questions) {
        for (Question question : questions) {
            questionMap.put(question.getQuestionId(), question);
        }
    }

    // Fetch current question
    public Question getCurrentQuestion() {
        return questionMap.get(currentQuestionId + 1); // Adjusting to start from questionId 1
    }

    // Trivia for the current question
    public String getCurrentQuestionTrivia() {
        Question currentQuestion = questionMap.get(currentQuestionId + 1);
        if (currentQuestion instanceof QuestionMultipleChoices) {
            return ((QuestionMultipleChoices) currentQuestion).getTrivia();
        }
        return "";
    }

    // Proceed to the next question
    public void nextQuestion() {
        if (questionMap.containsKey(currentQuestionId + 2)) {
            currentQuestionId++;
        }
    }
}
