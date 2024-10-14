package com.example;
import java.util.HashMap;
import java.util.Map;

public class User {

    private Map<Integer, String> userHistory = new HashMap<>();
    private static Map<String,User> instances = new HashMap<>();

    private String userName;
    private int score;
    private int correctAnswers;
    private int wrongAnswers;

    // Initializing a new user;
    private User(String userName) {
        this.userName = userName;
        this.score = 0;
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
    }

    // Ensures a single instance per key(userName)
    public static synchronized User getInstance(String userName){
        if (!instances.containsKey(userName)){
            instances.put(userName, new User(userName));
        }
        return instances.get(userName);
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

    public void addToHistory(int questionId, String Answer) {
        userHistory.put(questionId, Answer);
    }

    public Map<Integer, String> getHistory(){
        return userHistory;
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

    public void reset(){
        this.score = 0;
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
        this.userHistory.clear();
    }
}
