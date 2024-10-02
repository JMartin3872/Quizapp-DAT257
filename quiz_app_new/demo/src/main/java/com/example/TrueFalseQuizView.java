package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrueFalseQuizView {
    private JPanel mainPanel;
    private JTextArea questionTextarea;
    private JButton trueButton, falseButton, nextQuestionButton;
    private QuizGameModel model;
    private ModelTrueFalse modeltf;
    private QuizGameView quizGameView;
    private String selectedAnswer = "";
    private int score = 0;

    public TrueFalseQuizView(QuizGameView quizGameView) {
        this.quizGameView = quizGameView;
        modeltf = new ModelTrueFalse();

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(720, 480));
        mainPanel.setVisible(true);

        setupUI();
        loadQuestion();  // Load the first question
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    private void setupUI() {
        JPanel questionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        questionPanel.setBackground(Color.pink);
        questionTextarea = new JTextArea();
        questionTextarea.setFont(new Font("Arial", Font.BOLD, 24));
        questionTextarea.setEditable(false);
        questionTextarea.setLineWrap(true);
        questionTextarea.setWrapStyleWord(true);
        questionTextarea.setColumns(20);
        questionTextarea.setRows(7);
        questionPanel.add(questionTextarea);
        mainPanel.add(questionPanel, BorderLayout.NORTH);

        JPanel answerPanel = new JPanel(new GridBagLayout());
        answerPanel.setBackground(Color.pink);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(140, 40, 140, 40);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        trueButton = new JButton("True");
        falseButton = new JButton("False");
        trueButton.setBackground(Color.WHITE);
        falseButton.setBackground(Color.WHITE);

        gbc.gridy = 0;
        gbc.gridx = 0;
        answerPanel.add(trueButton, gbc);
        gbc.gridy = 0;
        gbc.gridx = 1;
        answerPanel.add(falseButton, gbc);
        trueButton.setPreferredSize(new Dimension(300, 80));
        falseButton.setPreferredSize(new Dimension(300, 80));
        mainPanel.add(answerPanel, BorderLayout.CENTER);

        trueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedAnswer = "True";
                handleAnswer();  // Handle answer after selection
            }
        });

        falseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedAnswer = "False";
                handleAnswer();  // Handle answer after selection
            }
        });

        nextQuestionButton = new JButton("Next Question");
        nextQuestionButton.setEnabled(false);  // Initially disabled
        nextQuestionButton.setBackground(Color.WHITE);
        nextQuestionButton.setPreferredSize(new Dimension(400, 65));
        nextQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modeltf.nextQuestion();
                loadQuestion();
                questionTextarea.setBackground(Color.WHITE);
                nextQuestionButton.setEnabled(false);  // Disable next question button until answered
            }
        });
        mainPanel.add(nextQuestionButton, BorderLayout.SOUTH);
    }

    private void loadQuestion() {
        if (!modeltf.isFinished()) {
            questionTextarea.setText(modeltf.getCurrentQuestionText());
            trueButton.setEnabled(true);   // Enable answer buttons for new question
            falseButton.setEnabled(true);
        } else {
            // Display score and end the quiz
            questionTextarea.setText("Quiz finished! Your score: " + modeltf.getScore());
            trueButton.setEnabled(false);  // Disable buttons after quiz ends
            falseButton.setEnabled(false);
            nextQuestionButton.setEnabled(false);
        }
    }

    private void handleAnswer() {
        // Disable answer buttons after selecting an answer
        trueButton.setEnabled(false);
        falseButton.setEnabled(false);

        boolean isCorrect = modeltf.checkAnswer(selectedAnswer);
        String message;
        if (isCorrect) {
            modeltf.correctAnswer();  // Update score for correct answer
            message = "Correct! \n" + modeltf.getCurrentQuestionTrivia();
            questionTextarea.setBackground(Color.green);
        } else {
            modeltf.wrongAnswer();
            message = "Wrong! The correct answer is: " + modeltf.getCurrentQuestionCorrectAnswer() + "\n" + modeltf.getCurrentQuestionTrivia();
            questionTextarea.setBackground(Color.red);
        }

        // Show trivia and feedback in a message dialog
        questionTextarea.setText(message);

        // Enable next question button after answering
        nextQuestionButton.setEnabled(true);
    }
}