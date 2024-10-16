package com.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultipleChoicesQuizView {

    private JPanel mainPanel; // JPanel instead of JFrame
    private JTextArea questionTextarea;
    private JButton[] answerButtons = new JButton[3];
    private JButton submitButton;
    private QuizGameModel model;
    private int selectedAnswer = -1; // track the selected answer
    private QuizGameView quizGameView;
    private boolean questionsAnswered = false;
    private boolean isAnswerSubmitted = false; // Track whether the answer has been submitted
    private CardLayout cardLayout;


    public MultipleChoicesQuizView(QuizGameView quizGameView) {
        this.quizGameView = quizGameView;
        model = new QuizGameModel();

        // Create the main panel instead of a JFrame
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(720, 480)); // Set preferred size for consistency

        // Set up the UI
        setupUI();

        // Load the first question
        loadQuestion();
    }

    // Method to return the main panel, which can be added to another JFrame or JPanel
    public JPanel getPanel() {
        return mainPanel;
    }

    private void setupUI() { // component setup
        // question label in the center of the panel
        JPanel questionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //changed from label to Textarea so that the entire question-text is visible without resizing the window
        questionTextarea = new JTextArea();
        questionTextarea.setFont(new Font("Arial", Font.BOLD, 24));
        questionTextarea.setEditable(false);
        questionTextarea.setLineWrap(true);
        questionTextarea.setWrapStyleWord(true);
        questionTextarea.setColumns(30);
        questionTextarea.setRows(5);

        questionPanel.add(questionTextarea);
        mainPanel.add(questionPanel, BorderLayout.NORTH); // Add the panel to the top

        questionPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
        questionPanel.setBackground(new Color(232, 136, 216));

        // Center panel for answer buttons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        centerPanel.setBackground(new Color(232, 136, 216));

        // Create answer buttons
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i] = new JButton();
            answerButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            answerButtons[i].setPreferredSize(new Dimension(300, 65));
            answerButtons[i].setBackground(Color.WHITE);
            answerButtons[i].setFocusPainted(false);
            final int index = i; // for use in action listener
            answerButtons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    selectedAnswer = index; // track the selected answer
                    highlightSelectedButton(index); // sets selected button background color to green
                }
            });
            gbc.gridx = 0;
            gbc.gridy = i;
            centerPanel.add(answerButtons[i], gbc);
        }

        // Creating a new panel to contain the main menu and next question buttons
        JPanel backOrNextPanel = new JPanel(new FlowLayout());
        backOrNextPanel.setBackground(new Color(232, 136, 216));
        backOrNextPanel.setPreferredSize(new Dimension(720, 100));

        // Create submit button
        submitButton = new JButton("Submit Answer");
        submitButton.setFont(new Font("Arial", Font.BOLD, 20));
        submitButton.setBackground(Color.WHITE);
        submitButton.setPreferredSize(new Dimension(300, 55));
        submitButton.setFocusPainted(false);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (submitButton.getText().equals("Next Question")){
                    handleNextQ();
                }else{
                handleSubmit(); // Soon to be added! responsible for everything that happens after the user answers the question
                }
            }
        });

        // Create Main menu button
        JButton toMainMenuButton = new JButton("Main Menu");
        toMainMenuButton.setFont(new Font("Arial", Font.BOLD, 20));
        toMainMenuButton.setBackground(Color.WHITE);
        toMainMenuButton.setPreferredSize(new Dimension(300, 55));
        toMainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(quizGameView.backToMenu(mainPanel), "Main Menu");
            }
        });

        gbc.gridy = 0;
        gbc.gridx = 0;
        backOrNextPanel.add(toMainMenuButton, gbc);
        gbc.gridy = 0;
        gbc.gridx = 1;
        backOrNextPanel.add(submitButton, gbc);

        mainPanel.add(backOrNextPanel, BorderLayout.SOUTH);
        // Keep buttons at the bottom
    }

    private void highlightSelectedButton(int index) {
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setBackground(Color.WHITE);

            if (i == index) {
                answerButtons[i].setBackground(new Color(40, 225, 61));
            }
        }
    }

    private void resetButtonBackgrounds() {
        for (JButton button : answerButtons) {
            button.setBackground(Color.WHITE); // reset background of alternative buttons when new question
        }
    }

    private void loadQuestion() {
        // get current question from model if there is one
        Question currentQuestion = model.getCurrentQuestion();
        if (currentQuestion == null) return;

        questionTextarea.setText((model.getCurrentQuestionId()+ 1) + "/" + (model.getTotalQuestions()) + "\n" +currentQuestion.getQuestionText());

        // add answer buttons text
        for (int i = 0; i < currentQuestion.getAnswers().size(); i++) {
            answerButtons[i].setText(currentQuestion.getAnswers().get(i));
        }
    }

    private void handleSubmit() { // when the answer is submitted
        String userName = model.getUserName();
        User user = User.getInstance(userName);
        // if user doesn't choose an answer and tries to click submit:
        if (selectedAnswer == -1) {
            JOptionPane.showMessageDialog(mainPanel, "Please select an answer.");
            return;
        }

        // see if chosen answer is correct
        QuestionMultipleChoices currentQuestion = (QuestionMultipleChoices) model.getCurrentQuestion();

        String message; // used when printing correct! or wrong! answer

        if (currentQuestion.getAnswers().get(selectedAnswer).equals(currentQuestion.getCorrectAnswer())) {
            message = "Correct!" + "\n\n" + currentQuestion.getTrivia();
            questionTextarea.setBackground(Color.green);
            user.addToHistory(model.getCurrentQuestionId(), currentQuestion.getAnswers().get(selectedAnswer));
            user.correctAnswer();
        } else {
            message = "Wrong! The correct answer is: " + currentQuestion.getCorrectAnswer() + "\n\n" + currentQuestion.getTrivia();
            questionTextarea.setBackground(Color.red);
            user.addToHistory(model.getCurrentQuestionId(), currentQuestion.getAnswers().get(selectedAnswer));
            user.wrongAnswer();
        }

        // Display the message in the text area
        questionTextarea.setText(message);

        // Change submit button to "Next Question"
        submitButton.setText("Next Question");

        //Disable buttons after answering
        for (JButton button : answerButtons) {

            button.setEnabled(false);

        };

        isAnswerSubmitted = true; // Mark that the answer was submitted

    }
    private void handleNextQ(){
        questionTextarea.setBackground(Color.white);
        // Check if the current question was the last one before advancing.
        Question currentQuestion = model.getCurrentQuestion();
        model.nextQuestion(); // Move to the next question

        if (model.getCurrentQuestion() == currentQuestion) {
            // If the current question remains the same, it means we're at the end of the quiz.
            questionsAnswered = true;
            checkAllQuestionsAnswered();
            model.restartQuiz();
            return; // Exit the method to avoid looping back to the same question.
        }

        // Load the next question if available
        loadQuestion();
        resetButtonBackgrounds(); // Reset button backgrounds for new question
        selectedAnswer = -1; // Reset selected answer
        isAnswerSubmitted = false; // Reset submission state
        submitButton.setText("Submit Answer"); // Revert button text to "Submit Answer"

        //turn buttons back on
        for (JButton button : answerButtons) {

            button.setEnabled(true);

        };

    }

    private void checkAllQuestionsAnswered(){
        if (questionsAnswered){
            quizGameView.showMultipleChoiseSummaryView();
            questionsAnswered = false;
        }
    }
}
