package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EstimateQuizView {
    private JPanel mainPanel;
    private JTextArea questionTextarea;
    private JTextField estimateInputField;
    private JButton nextQuestionButton;  // This button will serve dual purposes
    private ModelEstimateNumber model;
    private QuizGameView quizGameView;
    private CardLayout cardLayout;
    private QuizGameModel modelq;
    private boolean quizDone = false;

    public EstimateQuizView(QuizGameView quizGameView) {
        this.quizGameView = quizGameView;
        model = new ModelEstimateNumber();
        modelq = new QuizGameModel();

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
        // Setting up the question panel
        JPanel questionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        questionPanel.setBackground(new Color(204, 229, 255));
        questionTextarea = new JTextArea();
        questionTextarea.setFont(new Font("Arial", Font.BOLD, 24));
        questionTextarea.setEditable(false);
        questionTextarea.setLineWrap(true);
        questionTextarea.setWrapStyleWord(true);
        questionTextarea.setColumns(20);
        questionTextarea.setRows(7);
        questionPanel.add(questionTextarea);
        mainPanel.add(questionPanel, BorderLayout.NORTH);

        // Setting up the answer panel
        JPanel answerPanel = new JPanel(new GridBagLayout());
        answerPanel.setBackground(new Color(204, 229, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 0, 30, 0);

        JLabel estimateLabel = new JLabel("Your estimate: ");
        estimateLabel.setFont(new Font("Arial", Font.BOLD, 25));
        estimateInputField = new JTextField();
        estimateInputField.setPreferredSize(new Dimension(300, 50));
        estimateInputField.setFont(new Font("Arial", Font.PLAIN, 18));

        // Add input verifier to allow only numeric input
        estimateInputField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                String text = estimateInputField.getText();
                if (!text.matches("\\d*")) { // allow only digits
                    JOptionPane.showMessageDialog(mainPanel, "Please enter a valid number.");
                    return false; // Input is invalid
                }
                return true; // Input is valid
            }
        });

        gbc.gridy = 0;
        gbc.gridx = 0;
        answerPanel.add(estimateLabel, gbc);
        gbc.gridy = 0;
        gbc.gridx = 1;
        answerPanel.add(estimateInputField, gbc);
        mainPanel.add(answerPanel, BorderLayout.CENTER);
        
        // Creating a new panel to contain the Main menu and Submit/Next buttons
        JPanel backOrNextPanel = new JPanel(new FlowLayout());
        backOrNextPanel.setBackground(new Color(204, 229, 255));
        backOrNextPanel.setPreferredSize(new Dimension(720, 100));
        
        // Creating Submit/Next button
        nextQuestionButton = new JButton("Submit Answer");  // Initial text for the button
        nextQuestionButton.setFocusPainted(false);
        nextQuestionButton.setBackground(Color.WHITE);
        nextQuestionButton.setFont(new Font("Arial", Font.BOLD, 20));
        nextQuestionButton.setPreferredSize(new Dimension(300, 55));
        nextQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the input is valid before proceeding
                if (estimateInputField.getInputVerifier().verify(estimateInputField)) {
                    if (nextQuestionButton.getText().equals("Submit Answer")) {
                        handleAnswer();  // Handle answer submission
                    } else {
                        model.nextQuestion();  // Move to the next question
                        loadQuestion();
                        nextQuestionButton.setText("Submit Answer");  // Reset button text
                        questionTextarea.setBackground(Color.WHITE);  // Reset background
                    }
                }
            }
        });

        // Creating Main menu button with Action Listener
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
        backOrNextPanel.add(nextQuestionButton, gbc);

        mainPanel.add(backOrNextPanel, BorderLayout.SOUTH);
         // Keep the buttons at the bottom
    }

    private void loadQuestion() {
        // Check if the quiz is finished
        if (!model.isFinished()) {
            // Load the current question text
            questionTextarea.setText((model.getCurrentQuestionId()+ 1) + "/" + (model.getTotalQuestions()) + "\n" + model.getCurrentQuestionText());
            estimateInputField.setEnabled(true);
            estimateInputField.requestFocus();
            estimateInputField.setText("");  // Clear previous input
        } else {
            // Display score and end the quiz
            questionTextarea.setText("Quiz finished! Your score: " + model.getScore());
            estimateInputField.setEnabled(false);  // Disable input and buttons after quiz ends
            nextQuestionButton.setEnabled(false); // Disable the next question button
            quizDone = true;
            showSummaryView();
        }
    }

    private void showSummaryView(){
        if (quizDone){
            model.restartQuiz();
            quizGameView.showEstimateSummaryView();
            quizDone = false;
        }
    }

    private void handleAnswer() {
        String userName = modelq.getUserName();
        User user = User.getInstance(userName);
        String userAnswer = estimateInputField.getText().trim();
        if (userAnswer.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Please enter a number estimate.");
            return;
        }

        boolean isCorrect = model.checkAnswer(userAnswer);
        String message;
        if (isCorrect) {
            model.correctAnswer();  // Update score for correct answer
            message = "Correct! \n" + model.getCurrentQuestionTrivia();
            questionTextarea.setBackground(Color.green);
            user.addToHistory(model.getCurrentQuestionId(), userAnswer);
            user.correctAnswer();
        } else {
            model.wrongAnswer();
            message = "Wrong! The correct estimate was: " + model.getCurrentQuestionCorrectAnswer() + "\n" + model.getCurrentQuestionTrivia();
            questionTextarea.setBackground(Color.red);
            user.addToHistory(model.getCurrentQuestionId(), userAnswer);
            user.wrongAnswer();
        }

        // Show trivia and feedback in the text area
        questionTextarea.setText(message);
        nextQuestionButton.setText("Next Question");  // Change button text to "Next Question"
        estimateInputField.setEnabled(false);  // Disable input field after answering
    }
}
