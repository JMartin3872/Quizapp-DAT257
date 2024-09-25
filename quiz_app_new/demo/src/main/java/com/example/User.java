package com.example;

public class User {

    private String userName;
    private int score;

    // Initializing a new user with user name and score = 0;
    public User(String userName) {
        this.userName = userName;
        this.score = 0;
    }

    public void setUserName(String userName) {
        this.userName = userName; 
    }

    public String getUserName() {
        return userName;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }
}
