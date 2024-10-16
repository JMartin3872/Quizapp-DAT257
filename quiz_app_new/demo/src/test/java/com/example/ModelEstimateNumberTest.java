package com.example;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ModelEstimateNumberTest {
    
    @Test
    public void testGetScore() {
        ModelEstimateNumber modelEstimateNumber = new ModelEstimateNumber();
        int intialScore = modelEstimateNumber.getScore();
        Assertions.assertEquals(0, intialScore);  
    }

    @Test
    public void testCorrectAnswer() {
        ModelEstimateNumber modelEstimateNumber = new ModelEstimateNumber();
        modelEstimateNumber.correctAnswer();
        int afterScore = modelEstimateNumber.getScore();
        Assertions.assertEquals(1, afterScore);
    }

}
