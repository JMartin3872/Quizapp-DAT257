public class QuestionMultipleChoices implements Question{
    private int questionId;
    private String questionText;
    //Save answers in an array instead?
    private String correctAnswer;
    private String falseAnswer1;
    private String falseAnswer2;

    public int getQuestionId(){
        return this.questionId;
    }
    
    public String getQuestion(){
        return this.questionText;
    }
    public String getAnswer(){
        return this.correctAnswer;
    }
}
