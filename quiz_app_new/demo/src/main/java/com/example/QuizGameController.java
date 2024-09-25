package com.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class QuizGameController {

    private QuizGameModel model;
    private LoginView loginView;
    private QuizGameView quizGameView;

    public QuizGameController(){
        this.model = new QuizGameModel();
        this.loginView =new LoginView(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(loginView.getUsername() != null){
                    loginView.setVisible(false);
                    model.setUserName(loginView.getUsername());
                    quizGameView = new QuizGameView(model);
                    
                }
                
            }
        });
        
    }

    public static void main(String[] args){
        QuizGameController controller = new QuizGameController();
    }
    //test
}
