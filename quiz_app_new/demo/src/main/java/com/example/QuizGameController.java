package com.example;

import java.util.ArrayList;
import java.util.List;

public class QuizGameController {

    private QuizGameModel model;

    public QuizGameController(){
        new QuizGameView(model);
    }

    public static void main(String[] args){
        QuizGameController controller = new QuizGameController();
    }
    //test
}