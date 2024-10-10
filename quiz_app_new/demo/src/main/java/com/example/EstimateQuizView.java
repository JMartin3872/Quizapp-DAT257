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

    public EstimateQuizView(QuizGameView quizGameView) {
        this.quizGameView = quizGameView;
        model = new ModelEstimateNumber();

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

        // Setting up the "Next Question" button at the bottom
        nextQuestionButton = new JButton("Submit Answer");  // Initial text for the button
        nextQuestionButton.setFocusPainted(false);
        nextQuestionButton.setBackground(Color.WHITE);
        nextQuestionButton.setFont(new Font("Arial", Font.BOLD, 18));
        nextQuestionButton.setPreferredSize(new Dimension(720, 65)); // Make it full width
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

        mainPanel.add(nextQuestionButton, BorderLayout.SOUTH); // Keep the button at the bottom
    }

    private void loadQuestion() {
        // Check if the quiz is finished
        if (!model.isFinished()) {
            // Load the current question text
            questionTextarea.setText((model.getCurrentQuestionId()+ 1) + "/" + (model.getTotalQuestions()-1) + "\n" + model.getCurrentQuestionText());
            estimateInputField.setEnabled(true);
            estimateInputField.requestFocus();
            estimateInputField.setText("");  // Clear previous input
        } else {
            // Display score and end the quiz
            questionTextarea.setText("Quiz finished! Your score: " + model.getScore());
            estimateInputField.setEnabled(false);  // Disable input and buttons after quiz ends
            nextQuestionButton.setEnabled(false); // Disable the next question button
        }
    }

    private void handleAnswer() {
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
        } else {
            model.wrongAnswer();
            message = "Wrong! The correct estimate was: " + model.getCurrentQuestionCorrectAnswer() + "\n" + model.getCurrentQuestionTrivia();
            questionTextarea.setBackground(Color.red);
        }

        // Show trivia and feedback in the text area
        questionTextarea.setText(message);
        nextQuestionButton.setText("Next Question");  // Change button text to "Next Question"
        estimateInputField.setEnabled(false);  // Disable input field after answering
    }
}
