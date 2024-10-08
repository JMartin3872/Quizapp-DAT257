package com.example;
    
import java.util.*;
import java.io.*;


public class QuestionReaderEstimateNumber implements QuestionReader {

    @Override
    public LinkedList<Question> readQuestionFile(String fileName) {
      
      //Create an empty list which will hold the read questions
      LinkedList<Question> result = new LinkedList<Question>();

      try {
          // Open a new file and scanner
          File questionFile = new File(fileName);
          Scanner fileReader = new Scanner(questionFile);
          
          //While the file still has more questions, iterate over 3 lines at a time 
          // and create a new question from it and add it to the list.
          int id = 0;
          while (fileReader.hasNextLine()) {
              String description = fileReader.nextLine();
              String correctAnswer = fileReader.nextLine();
              String trivia = fileReader.nextLine();

              Question q = new QuestionEstimateNumber(description, correctAnswer, trivia, id);

              //Add to list
              result.add(q);

              //Increment id-value so the next question will get a unique id.
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

