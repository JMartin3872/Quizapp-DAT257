package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class QuizGameView {


    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JButton startButton, optionsButton, exitButton;
    private QuizGameModel model; // Model reference

    public QuizGameView(QuizGameModel model) {

        // init. frame and UI components
        frame = new JFrame("Quiz Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 480);

        // Setup the UI
        setupUI();

        // Add the main panel (with CardLayout) to the frame
        frame.add(mainPanel);

        // Center app on the screen
        frame.setLocationRelativeTo(null);

        // make the frame visible
        frame.setVisible(true);
    }

    private void setupUI() {
        // creating a CardLayout to switch between panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout); // This will hold different pages

        // creatin the main menu panel (the first "page")
        JPanel menuPanel = new JPanel();
        Color backgroundColor = new Color(37, 219, 131);
        menuPanel.setBackground(backgroundColor);
        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // padding around elements

        // label for the title
        titleLabel = new JLabel("Best quiz ever");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        //  buttons for the quiz menu
        startButton = new JButton("Start Quiz");
        optionsButton = new JButton("Options");
        exitButton = new JButton("Exit");

        // button color etc
        Color buttonColor = new Color(13, 105, 60);
        startButton.setBackground(buttonColor);
        startButton.setForeground(Color.black);
        startButton.setFocusPainted(false);

        optionsButton.setBackground(buttonColor);
        optionsButton.setForeground(Color.black);
        optionsButton.setFocusPainted(false);

        exitButton.setBackground(buttonColor);
        exitButton.setForeground(Color.black);
        exitButton.setFocusPainted(false);

        // adding buttons and title to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        menuPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        menuPanel.add(startButton, gbc);

        gbc.gridy = 2;
        menuPanel.add(optionsButton, gbc);

        gbc.gridy = 3;
        menuPanel.add(exitButton, gbc);

        // creating the "Start Quiz" panel (another page)
        JPanel quizPanel = new JPanel();
        quizPanel.setBackground(new Color(255, 229, 204)); // different background color
        JLabel quizLabel = new JLabel("Quiz Page");
        quizLabel.setFont(new Font("Arial", Font.BOLD, 24));
        quizPanel.add(quizLabel);


        // creating the "Options" panel (another page)
        JPanel optionsPanel = new JPanel();
        optionsPanel.setBackground(new Color(204, 229, 255)); // different background color
        JLabel optionsLabel = new JLabel("Options Page");
        optionsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        optionsPanel.add(optionsLabel);

        // Add panels to the CardLayout container
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(quizPanel, "Quiz");
        mainPanel.add(optionsPanel, "Options");

        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new GridBagLayout());
        questionPanel.setBackground(new Color(255, 255, 0 )); // different background color

        gbc.gridy = 0;

        JPanel secondPanel = new JPanel(cardLayout);
        secondPanel.add(questionPanel);

        gbc.gridy = 2;

        JButton toMCQ = new JButton("Multiple Choice Questions");
        toMCQ.setFont(new Font("Arial", Font.BOLD, 24));
        questionPanel.add(toMCQ, gbc);

        // Add ActionListeners to the buttons to switch between panels
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(secondPanel, "Quiz"); // switch to the Quiz panel
                //for sprint 1
                frame.remove(mainPanel);
                frame.add(secondPanel);
                
            }
        });

        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Options"); // switch to the Options panel
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });

        toMCQ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MultipleChoicesQuizView();

            }
        });

        // component listener to handle resizing and dynamically update components
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int frameWidth = frame.getWidth();
                int frameHeight = frame.getHeight();

                //  new font size based on frame width
                int newFontSize = Math.max(12, frameWidth / 30);
                Font newFont = new Font("Arial", Font.BOLD, newFontSize);

                // Update stuff
                titleLabel.setFont(newFont);
                startButton.setFont(newFont);
                optionsButton.setFont(newFont);
                exitButton.setFont(newFont);

                //  new button size based on the frame size
                int buttonWidth = frameWidth / 4;
                int buttonHeight = frameHeight / 10;
                Dimension newButtonSize = new Dimension(buttonWidth, buttonHeight);

                // update button sizes
                startButton.setPreferredSize(newButtonSize);
                optionsButton.setPreferredSize(newButtonSize);
                exitButton.setPreferredSize(newButtonSize);

                // reval n repaint the panel to apply changes
                menuPanel.revalidate();
                menuPanel.repaint();
            }
        });
    }
}

