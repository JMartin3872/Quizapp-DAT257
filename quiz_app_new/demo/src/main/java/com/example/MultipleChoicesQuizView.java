package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultipleChoicesQuizView {

    private JFrame frame;
    private JLabel questionLabel;
    private JButton[] answerButtons = new JButton[3];
    private JButton submitButton;
    private QuizGameModel model;
    private int selectedAnswer = -1; // track the selected answer

    public MultipleChoicesQuizView() {
        model = new QuizGameModel();

        // start JFrame
        frame = new JFrame("Multiple Choice Quiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 480);

        setupUI();

        // Center JFrame on the computer screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Load the first question
        loadQuestion();
    }

    private void setupUI() { // component setup
        frame.setLayout(new BorderLayout());

        // question label in the center of the JFrame
        JPanel questionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        questionPanel.add(questionLabel);
        frame.add(questionPanel, BorderLayout.NORTH); // Add the panel to the top

        questionPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
        questionPanel.setBackground(new Color(232, 136, 216));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        frame.add(centerPanel, BorderLayout.CENTER);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        centerPanel.setBackground(new Color(232, 136, 216));

        // Create answer buttons!
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
                // handleSubmit(); // Soon to be added! responsible for everything that happens after the user answers the question
            }
        });
        frame.add(submitButton, BorderLayout.SOUTH);
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

        questionLabel.setText("Question " + currentQuestion.getQuestionId() + ": " + currentQuestion.getQuestionText());

        // add answer buttons text
        for (int i = 0; i < currentQuestion.getAnswers().size(); i++) {
            answerButtons[i].setText(currentQuestion.getAnswers().get(i));
        }
    }
    // add handleSubmit() here!!
}