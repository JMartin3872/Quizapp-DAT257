package com.example;

import java.util.ArrayList;

public interface Question {
    public int getQuestionId();
    public String getQuestionText();
    public ArrayList<String> getAnswers();
    public void shuffleAnswers();
}
