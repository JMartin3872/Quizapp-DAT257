package com.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultipleChoicesQuizView {

    private JPanel mainPanel; // JPanel instead of JFrame
    private JLabel questionLabel;
    private JButton[] answerButtons = new JButton[3];
    private JButton submitButton;
    private QuizGameModel model;
    private int selectedAnswer = -1; // track the selected answer
    private QuizGameView quizGameView;
    private boolean questionsAnswered = false;

    public MultipleChoicesQuizView() {
        model = new QuizGameModel();

        // Create the main panel instead of a JFrame
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(720, 480)); // Set preferred size for consistency

        // Set up the UI
        setupUI();

        // Load the first question
        loadQuestion();
    }

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
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        questionPanel.add(questionLabel);
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

        // Create submit button
        submitButton = new JButton("Submit Answer");
        submitButton.setFont(new Font("Arial", Font.BOLD, 20));
        submitButton.setPreferredSize(new Dimension(150, 50));

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSubmit(); // Soon to be added! responsible for everything that happens after the user answers the question
            }
        });
        mainPanel.add(submitButton, BorderLayout.SOUTH);
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

        questionLabel.setText(currentQuestion.getQuestionText());

        // add answer buttons text
        for (int i = 0; i < currentQuestion.getAnswers().size(); i++) {
            answerButtons[i].setText(currentQuestion.getAnswers().get(i));
        }
    }

    private void handleSubmit() { // when the answer is submitted
        // if user doesn't choose an answer and tries to click submit:
        if (selectedAnswer == -1) {
            JOptionPane.showMessageDialog(mainPanel, "Please select an answer.");
            return;
        }

        // see if chosen answer is correct
        QuestionMultipleChoices currentQuestion = (QuestionMultipleChoices) model.getCurrentQuestion();

        String message; // used when printing correct! or wrong! answer

        if (currentQuestion.getAnswers().get(selectedAnswer).equals(currentQuestion.getCorrectAnswer())) {
            message = "Correct!" + "\n" + currentQuestion.getTrivia();}

        else {
            message = "Wrong! The correct answer is: " + currentQuestion.getCorrectAnswer() + "\n" + currentQuestion.getTrivia();
        }

        // Display answer correctness, name the frame "Result"
        JOptionPane.showMessageDialog(mainPanel, message, "Result", JOptionPane.INFORMATION_MESSAGE);

        Question q = model.getCurrentQuestion();

        model.nextQuestion();
        loadQuestion();
        selectedAnswer = -1; // reset selected answer

        if (q.equals(model.getCurrentQuestion())){          // When all question has been answered open SummaryView
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    questionsAnswered = true;
                    checkAllQuestionsAnswered();
                }
            });
        }

        resetButtonBackgrounds(); // make all button backgrounds white when new question
    }

    private void checkAllQuestionsAnswered(){
        if (questionsAnswered){
            quizGameView.showMultipleChoiseSummaryView();
        }
    }
}
