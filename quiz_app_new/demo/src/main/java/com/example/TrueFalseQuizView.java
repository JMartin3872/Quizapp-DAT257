package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrueFalseQuizView {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JTextArea questionTextarea;
    private JButton trueButton, falseButton, nextQuestionButton, toMainMenuButton;
    private QuizGameModel model;
    private ModelTrueFalse modeltf;
    private QuizGameView quizGameView;
    private String selectedAnswer = "";
    private int score = 0;
    private boolean quizDone = false;

    public TrueFalseQuizView(QuizGameView quizGameView) {
        this.quizGameView = quizGameView;
        model = new QuizGameModel();
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
        questionPanel.setBackground(new Color(255,233,133));
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
        answerPanel.setPreferredSize(new Dimension(720, 80));
        answerPanel.setBackground(new Color(255,233,133));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(130, 30, 130, 30);
        //gbc.fill = GridBagConstraints.BOTH;
       //gbc.weightx = 0.5;
        //gbc.weighty = 0.5;

        trueButton = new JButton("True");
        falseButton = new JButton("False");
        trueButton.setFocusPainted(false);
        falseButton.setFocusPainted(false);
        trueButton.setBackground(Color.WHITE);
        trueButton.setFont(new Font("Arial", Font.PLAIN, 18));
        falseButton.setBackground(Color.WHITE);
        falseButton.setFont(new Font("Arial", Font.PLAIN, 18));

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
        
        // Creating a new panel to contain Main menu and next question buttons
        JPanel backOrNextPanel = new JPanel(new FlowLayout());
        backOrNextPanel.setBackground(new Color(255,233,133));
        backOrNextPanel.setPreferredSize(new Dimension(720, 80));
        
        // Creating next question button
        nextQuestionButton = new JButton("Next Question");
        nextQuestionButton.setEnabled(false);  // Initially disabled
        nextQuestionButton.setFocusPainted(false);
        nextQuestionButton.setBackground(Color.WHITE);
        nextQuestionButton.setFont(new Font("Arial", Font.BOLD, 20));
        nextQuestionButton.setPreferredSize(new Dimension(300, 55));
        nextQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modeltf.nextQuestion();
                loadQuestion();
                questionTextarea.setBackground(Color.WHITE);
                nextQuestionButton.setEnabled(false);  // Disable next question button until answered
            }
        });

        // Creating Main menu button
        toMainMenuButton = new JButton("Main Menu");
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
        backOrNextPanel.add(nextQuestionButton, gbc);

        mainPanel.add(backOrNextPanel, BorderLayout.SOUTH);
        // Keep the buttons at the bottom
    }

    private void loadQuestion() {
        if (!modeltf.isFinished()) {
            questionTextarea.setText((modeltf.getCurrentQuestionId()+ 1) + "/" + (modeltf.getTotalQuestions()) + "\n" + modeltf.getCurrentQuestionText());
            trueButton.setEnabled(true);   // Enable answer buttons for new question
            falseButton.setEnabled(true);
        } else {
            // Display score and end the quiz
            questionTextarea.setText("Quiz finished! Your score: " + modeltf.getScore());
            trueButton.setEnabled(false);  // Disable buttons after quiz ends
            falseButton.setEnabled(false);
            nextQuestionButton.setEnabled(false);
            quizDone = true;
            showSummaryView();
        }
    }
    private void showSummaryView(){
        if (quizDone){
            modeltf.restartQuiz();
            this.score = 0;
            quizGameView.showTrueFalseSummaryView();
            quizDone = false;
        }
    }

    private void handleAnswer() {
        String userName = model.getUserName();
        User user = User.getInstance(userName);
        // Disable answer buttons after selecting an answer
        trueButton.setEnabled(false);
        falseButton.setEnabled(false);

        boolean isCorrect = modeltf.checkAnswer(selectedAnswer);
        String message;
        if (isCorrect) {
            modeltf.correctAnswer();  // Update score for correct answer
            message = "Correct! \n" + modeltf.getCurrentQuestionTrivia();
            questionTextarea.setBackground(Color.green);
            user.addToHistory(modeltf.getCurrentQuestionId(), selectedAnswer);
            user.correctAnswer();
        } else {
            modeltf.wrongAnswer();
            message = "Wrong! The correct answer is: " + modeltf.getCurrentQuestionCorrectAnswer() + "\n" + modeltf.getCurrentQuestionTrivia();
            questionTextarea.setBackground(Color.red);
            user.addToHistory(modeltf.getCurrentQuestionId(), selectedAnswer);
            user.wrongAnswer();
        }

        // Show trivia and feedback in a message dialog
        questionTextarea.setText(message);

        // Enable next question button after answering
        nextQuestionButton.setEnabled(true);
    }
}