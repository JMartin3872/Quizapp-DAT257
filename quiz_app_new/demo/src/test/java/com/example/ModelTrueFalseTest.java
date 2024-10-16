package com.example;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ModelTrueFalseTest {
    @Test
    public void testGetScore() {
        ModelTrueFalse modelTrueFalse = new ModelTrueFalse();
        int intialScore = modelTrueFalse.getScore();
        Assertions.assertEquals(0, intialScore);        
    }

    @Test
    public void testGetScoreAfterCorrectIncrease() {
        ModelTrueFalse modelTrueFalse = new ModelTrueFalse();
        modelTrueFalse.correctAnswer();
        int afterScore = modelTrueFalse.getScore();
        Assertions.assertEquals(1, afterScore);      
    }

}
