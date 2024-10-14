package com.example;

import java.util.ArrayList;

public class QuestionEstimateNumber implements Question{
    //question identifier
    private final int questionId;
    //The question description
    private final String questionText;
    //The only correct answer to the question
    private final String correctAnswer;
    // The aha moment!
    private final String trivia;

    public QuestionEstimateNumber(String questionText, String correctAnswer, String trivia, int questionId){
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.trivia = trivia;
        this.questionId = questionId;
    }

    public int getQuestionId(){
        return this.questionId;
    }
    public String getQuestionText(){
        return this.questionText;
    }

    public String getTrivia(){
        return this.trivia;

    }
    public String getCorrectAnswer(){
        return this.correctAnswer;
    }

    public void shuffleAnswers(){}
    public ArrayList<String> getAnswers(){return null;}

    public String toString(){
        return questionText + "\n" + correctAnswer + "\n" + trivia;
    }
}
