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
    private ModelTrueFalse tfmodel;
    private ModelEstimateNumber estModel;

    public QuizGameView(QuizGameModel model, ModelTrueFalse tfmodel, ModelEstimateNumber estModel) {

        this.model = model;
        this.tfmodel = tfmodel;
        this.estModel = estModel;

        // init. frame and UI components
        frame = new JFrame("Quiz Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 720);

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
        mainPanel.add(questionPanel, "Question");
        gbc.gridy = 0;


        MultipleChoicesQuizView mcqView = new MultipleChoicesQuizView(this); // Create an instance of MultipleChoicesQuizView
        mainPanel.add(mcqView.getPanel(), "MultipleChoice"); // Add the panel to CardLayout
        gbc.gridy = 2;



        // Creating an instance of TrueFalseQuizView
        TrueFalseQuizView tfqView = new TrueFalseQuizView(this);
        mainPanel.add(tfqView.getPanel(), "TrueFalse"); // Add the panel to CardLayout
        gbc.gridy = 2;


        // TODO Uncomment when EstimateQuizView class has been implemented

        // Creating an instance of EstimateQuizView
        EstimateQuizView eqView = new EstimateQuizView(this);
        mainPanel.add(eqView.getPanel(), "Estimate"); // Add the panel to CardLayout
        gbc.gridy = 2;



        // Creating and adding the panel for Multiple Choice Quiz Info to CardLayout Container
        JPanel mcInfoPanel = new JPanel();
        mcInfoPanel.setLayout(new GridBagLayout());
        mcInfoPanel.setBackground(Color.ORANGE);
        mainPanel.add(mcInfoPanel, "MultipleChoiceInfo");
        gbc.gridy = 2;

        // Button for switching to Multiple Choice Quiz Info
        JButton toMCQInfo = new JButton("Multiple Choice Questions");
        toMCQInfo.setFont(new Font("Arial", Font.BOLD, 24));
        toMCQInfo.setBackground(Color.WHITE);
        questionPanel.add(toMCQInfo, gbc); // Creating the Multiple Choice Info Panel

        // Text area and button for switching to Multiple Choice Quiz View
        JButton toMCQ = new JButton("Next");
        toMCQ.setFont(new Font("Arial", Font.BOLD, 24));
        toMCQ.setBackground(Color.WHITE);
        JTextArea mcInfo = new JTextArea(model.getMCQuizInfo());
        mcInfo.setPreferredSize(new Dimension(500, 300));
        mcInfo.setBackground(Color.ORANGE);
        mcInfo.setFont(new Font("Arial", Font.BOLD, 24));
        mcInfo.setWrapStyleWord(true);
        mcInfo.setLineWrap(true);
        mcInfo.setEditable(false);
        mcInfo.setOpaque(false);

        gbc.gridy = 1;
        mcInfoPanel.add(mcInfo, gbc);
        gbc.gridy = 3;
        mcInfoPanel.add(toMCQ, gbc);


        // Creating and adding the panel for True Or False Quiz Info to CardLayout Container
        JPanel tfInfoPanel = new JPanel();
        tfInfoPanel.setLayout(new GridBagLayout());
        tfInfoPanel.setBackground(Color.ORANGE);
        mainPanel.add(tfInfoPanel, "TrueFalseInfo");
        gbc.gridy = 4;

        // Button for switching to True or False Quiz Info
        JButton toTFQInfo = new JButton("True Or False Questions");
        toTFQInfo.setFont(new Font("Arial", Font.BOLD, 24));
        toTFQInfo.setBackground(Color.WHITE);
        questionPanel.add(toTFQInfo, gbc); // Creating the True Or False Info Panel

        // Text area and button for switching to True Or False Quiz View
        JButton toTFQ = new JButton("Next");
        toTFQ.setFont(new Font("Arial", Font.BOLD, 24));
        toTFQ.setBackground(Color.WHITE);
        JTextArea tfInfo = new JTextArea(tfmodel.getTFQuizInfo());
        tfInfo.setPreferredSize(new Dimension(500, 300));
        tfInfo.setBackground(Color.ORANGE);
        tfInfo.setFont(new Font("Arial", Font.BOLD, 24));
        tfInfo.setWrapStyleWord(true);
        tfInfo.setLineWrap(true);
        tfInfo.setEditable(false);
        tfInfo.setOpaque(false);

        gbc.gridy = 1;
        tfInfoPanel.add(tfInfo, gbc);
        gbc.gridy = 3;
        tfInfoPanel.add(toTFQ, gbc);



        // Creating and adding the panel for Estimate Quiz Info to CardLayout Container
        JPanel estInfoPanel = new JPanel();
        estInfoPanel.setLayout(new GridBagLayout());
        estInfoPanel.setBackground(Color.ORANGE);
        mainPanel.add(estInfoPanel, "EstimateInfo");
        gbc.gridy = 6;

        // Button for switching to Estimate Quiz Info
        JButton toEstQInfo = new JButton("Estimation Questions");
        toEstQInfo.setFont(new Font("Arial", Font.BOLD, 24));
        toEstQInfo.setBackground(Color.WHITE);
        questionPanel.add(toEstQInfo, gbc); // Creating the Estimation Info Panel

        // Text area and button for switching to Estimation Quiz View
        JButton toEstQ = new JButton("Next");
        toEstQ.setFont(new Font("Arial", Font.BOLD, 24));
        toEstQ.setBackground(Color.WHITE);
        JTextArea estInfo = new JTextArea(estModel.getEstimateNumberQuizInfo());
        estInfo.setPreferredSize(new Dimension(500, 300));
        estInfo.setBackground(Color.ORANGE);
        estInfo.setFont(new Font("Arial", Font.BOLD, 24));
        estInfo.setWrapStyleWord(true);
        estInfo.setLineWrap(true);
        estInfo.setEditable(false);
        estInfo.setOpaque(false);

        gbc.gridy = 1;
        estInfoPanel.add(estInfo, gbc);
        gbc.gridy = 3;
        estInfoPanel.add(toEstQ, gbc);




        MultipleChoicesSummaryView mcsView = new MultipleChoicesSummaryView(this);
        mainPanel.add(mcsView, "MultipleChoiseSummary");


        // Add ActionListeners to the buttons to switch between panels
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Question"); // switch to the Quiz panel
                //for sprint 1
                //frame.remove(mainPanel);
                //frame.add(secondPanel);

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

        toMCQInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "MultipleChoiceInfo"); // Switch to the Multiple Choice Info panel
            }
        });

        toMCQ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "MultipleChoice"); // Switch to the Multiple Choice Questions panel
            }
        });

        toTFQInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "TrueFalseInfo"); // Switch to the True Or False Info Panel
            }
        });

        toTFQ.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "TrueFalse"); // Switch to the True Or False Question Panel
            }
        });

        toEstQInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "EstimateInfo"); // Switch to the Estimation Info Panel
            }
        });

        toEstQ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Estimate"); // Switch to Estimation Quiz Panel
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

    public void showMultipleChoiseSummaryView(){
        cardLayout.show(mainPanel, "MultipleChoiseSummary");
    }

    public Container backToMenu(JPanel cardPanel){
        cardLayout.show(mainPanel, "Menu");
        return cardPanel;
    }
}
