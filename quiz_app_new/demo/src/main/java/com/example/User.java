package com.example;

public class User {

    private String userName;
    private int score;
    private int correctAnswers;
    private int wrongAnswers;

    // Initializing a new user;
    public User(String userName) {
        this.userName = userName;
        this.score = 0;
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
    }
    
    // Setters and getters
    public void setUserName(String userName) {
        this.userName = userName; 
    }

    public String getUserName() {
        return userName;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void correctAnswer() {
        this.correctAnswers++;
        score++;
    }

    public void wrongAnswer() {
        this.wrongAnswers++;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public int getScore() {
        return score;
    }
}
