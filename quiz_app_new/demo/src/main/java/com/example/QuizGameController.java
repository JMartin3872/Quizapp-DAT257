package com.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class QuizGameController {

    private QuizGameModel multipleChoiceModel;
    private ModelTrueFalse trueFalseModel;
    private ModelEstimateNumber modelEstimateNumber;
    private QuizGameView quizGameView;

    public QuizGameController(){
        this.multipleChoiceModel = new QuizGameModel();
        this.trueFalseModel = new ModelTrueFalse();
        this.modelEstimateNumber = new ModelEstimateNumber();
        quizGameView = new QuizGameView(multipleChoiceModel, trueFalseModel, modelEstimateNumber);

        
    }

    public static void main(String[] args){
        QuizGameController controller = new QuizGameController();
    }
    
}
