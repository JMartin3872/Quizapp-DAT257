package com.example;

import java.util.*;
import java.io.*;


public class QuestionReaderMultipleChoices implements QuestionReader {

    @Override
    public LinkedList<Question> read(String fileName) {
        LinkedList<Question> result = new LinkedList<Question>();

        try {
            File questionFile = new File(fileName);
            Scanner fileReader = new Scanner(questionFile);
            int id = 0;
            while (fileReader.hasNextLine()) {
                String description = fileReader.nextLine();
                String correctAnswer = fileReader.nextLine();
                String wrongAnswer1 = fileReader.nextLine();
                String wrongAnswer2 = fileReader.nextLine();
                String trivia = fileReader.nextLine();

                result.add(new QuestionMultipleChoices(description, correctAnswer, wrongAnswer1, wrongAnswer2, trivia, id));

                id++;
            }

            fileReader.close();

          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }

        return result;
    }

}
