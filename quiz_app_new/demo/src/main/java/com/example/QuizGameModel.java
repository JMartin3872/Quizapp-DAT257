package com.example;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class QuizGameModel {
    private Map<Integer, Question> questionMap = new HashMap<>();
    private int currentQuestionId = 0; // Default to the first question
    private String username;

    public QuizGameModel() {
        // QuestionReaderMultipleChoices used to read questions from  testQeustions.txt
        QuestionReaderMultipleChoices reader = new QuestionReaderMultipleChoices();
        LinkedList<Question> questions = reader.read("testQuestions.txt");

        for (Question question : questions) {
            questionMap.put(question.getQuestionId(), question);
        }
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

    public void setUsername(String username){
        this.username = username;
    }
    }
}

