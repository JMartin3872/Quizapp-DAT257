package com.example;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
public class QuizGameModelTest {

    @Test
    public void TestgetQuestion() {
        QuizGameModel model = new QuizGameModel();
        int totQuestions = model.getTotalQuestions();
        Assertions.assertEquals(15, totQuestions);


    }

}
