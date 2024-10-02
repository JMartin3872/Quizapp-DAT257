package com.example;
import java.util.*;
import java.io.*;

public class TestReaderQuestions {
    
    
    

    public static void main(String[] args) {
        QuestionReaderTrueFalse reader = new QuestionReaderTrueFalse();
        LinkedList<Question> questions = reader.readQuestionFile("True-False-Questions.txt");
        for(Question question : questions){
            System.out.println(question.toString());
        }
    }
    
}
