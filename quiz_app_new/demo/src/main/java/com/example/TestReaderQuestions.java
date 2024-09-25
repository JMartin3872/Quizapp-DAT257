package com.example;
import java.util.*;
import java.io.*;

public class TestReaderQuestions {
    
    
    

    public static void main(String[] args) {
        QuestionReaderMultipleChoices reader = new QuestionReaderMultipleChoices();
        LinkedList<Question> questions = reader.read("Questions.txt");
        for(Question question : questions){
            System.out.println(question.toString());
        }
    }
    
}
