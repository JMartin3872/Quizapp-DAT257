package com.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class QuizGameController {

    private QuizGameModel multipleChoiceModel;
    private ModelTrueFalse trueFalseModel;
    private LoginView loginView;
    private QuizGameView quizGameView;

    public QuizGameController(){
        this.multipleChoiceModel = new QuizGameModel();
        this.trueFalseModel = new ModelTrueFalse();

        this.loginView =new LoginView(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(loginView.getUsername() != null){
                    loginView.setVisible(false);
                    multipleChoiceModel.setUserName(loginView.getUsername());
                    quizGameView = new QuizGameView(multipleChoiceModel);
                    
                }
                
            }
        });
        
    }

    public static void main(String[] args){
        QuizGameController controller = new QuizGameController();
    }
    
}
