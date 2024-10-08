package com.example;
import java.util.ArrayList;
import java.util.Collections;

public class QuestionMultipleChoices implements Question{

   //question identifier
    private final int questionId;
    //The question description
    private final String questionText;
    //An array of strings, containing the three alternative answers.
    private final ArrayList <String> answers = new ArrayList<String>();
    //The only correct answer to the question
    private final String correctAnswer;
    // The aha moment!
    private final String trivia;


    public QuestionMultipleChoices(String questionText,String correctAnswer, String falseAnswer1,String falseAnswer2,String trivia,int questionId){
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        answers.add(correctAnswer);
        answers.add(falseAnswer1);
        answers.add(falseAnswer2);
        this.trivia = trivia;
        this.questionId = questionId;
        Collections.shuffle(answers); // added to shuffle the order of the answers on the buttons in MultipleChoicesQuizView!
    }

    @Override
    public int getQuestionId(){
        return this.questionId;
    }
    @Override
    public String getQuestionText(){
        return this.questionText;
    }
    @Override
    public ArrayList<String> getAnswers(){
        return this.answers;
    }

    public String getTrivia(){
        return this.trivia;
    }

    public String getCorrectAnswer(){
        return this.correctAnswer;
    }

    public String toString(){
        return questionText + "\n" + answers.get(0) + "\n" + answers.get(1) + "\n" + answers.get(2) + "\n" + trivia;
    }

    public void shuffleAnswers(){
        Collections.shuffle(answers);
    }

}
