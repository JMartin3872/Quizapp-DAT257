package com.example;
import java.util.*;
import java.io.*;

public class TestReaderQuestions {
    
    
    

    public static void main(String[] args) {
        QuestionReaderEstimateNumber reader = new QuestionReaderEstimateNumber();
        LinkedList<Question> questions = reader.readQuestionFile("EstimateQuiz-Questions.txt");
        for(Question question : questions){
            System.out.println(question.toString());
        }

    }
    
}
