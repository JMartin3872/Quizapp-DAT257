package com.example;

import java.util.List;

public class MultipleChoiceQuestionModel {
    private String question;
    private List<String> answerAlternatives;
    private String correctAnswer;
    private String additionalInfo;


    public MultipleChoiceQuestionModel (String question, List<String> answerAlternative, String correctAnswer, String additionalInfo) throws IllegalArgumentException {
        this.question = question;
        this.answerAlternatives = answerAlternative;
        this.correctAnswer = correctAnswer;
        this.additionalInfo = additionalInfo;

        isAnswerInList(this.answerAlternatives, this.correctAnswer);
    }

    private void isAnswerInList(List<String> answerAlternatives, String correctAnswer) {
        for (String answer : answerAlternatives) {
            if(answer.equals(correctAnswer)) {
                return;
            }
        }
        throw new IllegalArgumentException("Correct answer is not present in answerAlternatives");
    }

    public String getQuestion() {
        return question;
    }
    public List<String> getAnswerAlternatives() {
        return answerAlternatives;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public String getAdditionalInfo() {
        return additionalInfo;
    }

}
