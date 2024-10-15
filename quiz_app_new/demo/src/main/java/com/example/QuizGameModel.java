package com.example;

import java.util.*;

public class QuizGameModel {
    private Map<Integer, Question> questionMap = new HashMap<>();
    private int currentQuestionId = 0; // Default to the first question
    private String userName;
    private int score = 0;
    private int correctAnswers = 0;


    private String multipleChoiceInfo = "In this quiz you are tasked with selecting the correct amount of carbon dioxide equivalents (kg) the following emit from the answer alternatives.\n\n"
        + "Select your answer and press \"Submit Answer\".";

    public QuizGameModel() {
        // QuestionReaderMultipleChoices used to read questions from  testQeustions.txt
        QuestionReaderMultipleChoices reader = new QuestionReaderMultipleChoices();
        LinkedList<Question> questions = reader.readQuestionFile("Multiplechoices-Questions.txt");

        for (Question question : questions) {
            questionMap.put(question.getQuestionId(), question);
        }
        multipleChoiceInfo += "\n\n There are " + getTotalQuestions() + " questions in this quiz.";
    }


    public Question getQuestion(int questionId) {
        // returns null if question doesn't exist
        return questionMap.get(questionId);
    }

    // Fetch the current question
    public Question getCurrentQuestion() {
        return questionMap.get(currentQuestionId);
    }

    // Get current question ID
    public int getCurrentQuestionId() {
        return getCurrentQuestion().getQuestionId();
    }

    public String getQuestionText(int questionId) {
        return getQuestion(questionId).getQuestionText();
    }


    public String getQuestionCorrectAnswer(int questionId) {
        Question q = getQuestion(questionId);
        if (q instanceof QuestionMultipleChoices) {
            return ((QuestionMultipleChoices) q).getCorrectAnswer();
        }
        return "";
    }


    // Proceed to the next question
    public void nextQuestion() {
        if (questionMap.containsKey(currentQuestionId + 1)) {
            currentQuestionId++;
        }
        else {
            currentQuestionId = getTotalQuestions();
        }
    }

    // Returns the total amount of questions in the quiz
    public int getTotalQuestions() {
        return questionMap.size();
    }


    // Gets info about Multiple Choice quiz
    public String getMCQuizInfo() {
        return multipleChoiceInfo;
    }


    public String getUserName(){
        return this.userName;
    }

    public void restartQuiz(){
        this.currentQuestionId = 0;
    }


    // For now, these functions reside in the model. Will be moved to User class later
    public void correctAnswer() {
        this.correctAnswers++;
        score++;
    }
    
}

