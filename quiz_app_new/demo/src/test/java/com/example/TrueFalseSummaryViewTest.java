package com.example;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TrueFalseSummaryViewTest {
    @Test
    public void testFeedbackMessage() {
        TrueFalseSummaryView trueFalseSummaryView = new TrueFalseSummaryView();
        String returnString = trueFalseSummaryView.feedbackMessage(25);
        Assertions.assertEquals("You should try again!", returnString);  

        returnString = trueFalseSummaryView.feedbackMessage(50);
        Assertions.assertEquals("Good Try", returnString); 

        returnString = trueFalseSummaryView.feedbackMessage(100);
        Assertions.assertEquals("Excellent Job!", returnString); 
    }
}
