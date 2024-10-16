package com.example;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class QuestionTrueFalseTest {
    @Test
    public void testGetQuestionText() {
        String description = "Question text?";
        String correctAnswer = "True";
        String wrongAnswer = "false";
        String trivia = "Additional info.";
        Question q = new QuestionTrueFalse(description, correctAnswer, wrongAnswer, trivia, 0);

        String questionText = q.getQuestionText();
        Assertions.assertEquals("Question text?", questionText);    
    }

    @Test
    public void testGetCorrectAnswer() {
        String description = "Question text?";
        String correctAnswer = "True";
        String wrongAnswer = "false";
        String trivia = "Additional info.";
        Question q = new QuestionTrueFalse(description, correctAnswer, wrongAnswer, trivia, 0);

        String correctQuestionAnswer = q.getCorrectAnswer();
        Assertions.assertEquals("True", correctQuestionAnswer);
    }

}
