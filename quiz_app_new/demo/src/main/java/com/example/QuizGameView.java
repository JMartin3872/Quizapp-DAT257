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
    private JButton startButton, exitButton;
    private QuizGameModel model; // Model reference
    private ModelTrueFalse tfmodel;
    private ModelEstimateNumber estModel;
    private MultipleChoicesSummaryView mcsView;
    private MultipleChoicesQuizView mcqView;
    private TrueFalseQuizView tfqView;
    private TrueFalseSummaryView tfsView;
    private EstimateQuizView eqView;
    private EstimateSummaryView esView;

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
        titleLabel = new JLabel("Sustainable quiz!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        //  buttons for the quiz menu
        startButton = new JButton("Start Quiz");
        exitButton = new JButton("Exit");

        // button color etc
        Color buttonColor = new Color(13, 105, 60);
        startButton.setBackground(buttonColor);
        startButton.setForeground(Color.black);
        startButton.setFocusPainted(false);

        exitButton.setBackground(buttonColor);
        exitButton.setForeground(Color.black);
        exitButton.setFocusPainted(false);

        // adding buttons and title to the panel

        gbc.gridx = 0;
        gbc.gridy = 0;
        ImageIcon logoIcon = new ImageIcon("sustainabilityLogo.png");
        menuPanel.add(new JLabel(logoIcon));

        //gbc.gridx = 0;
        gbc.gridy = 1;
        
        menuPanel.add(titleLabel, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        menuPanel.add(startButton, gbc);


        gbc.gridy = 3;
        menuPanel.add(exitButton, gbc);
        

        // creating the "Start Quiz" panel (another page)
        JPanel quizPanel = new JPanel();
        quizPanel.setBackground(new Color(255, 229, 204)); // different background color
        JLabel quizLabel = new JLabel("Quiz Page");
        quizLabel.setFont(new Font("Arial", Font.BOLD, 24));
        quizPanel.add(quizLabel, BorderLayout.NORTH);




        // Add panels to the CardLayout container
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(quizPanel, "Quiz");

        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new GridBagLayout());
        questionPanel.setBackground(new Color(37, 219, 131)); // different background color
        mainPanel.add(questionPanel, "Question");
        gbc.gridy = 0;


        mcqView = new MultipleChoicesQuizView(this); // Create an instance of MultipleChoicesQuizView
        mainPanel.add(mcqView.getPanel(), "MultipleChoice"); // Add the panel to CardLayout
        gbc.gridy = 2;



        // Creating an instance of TrueFalseQuizView
        tfqView = new TrueFalseQuizView(this);
        mainPanel.add(tfqView.getPanel(), "TrueFalse"); // Add the panel to CardLayout
        gbc.gridy = 2;



        // Creating an instance of EstimateQuizView
        eqView = new EstimateQuizView(this);
        mainPanel.add(eqView.getPanel(), "Estimate"); // Add the panel to CardLayout
        gbc.gridy = 2;



        // Creating and adding the panel for Multiple Choice Quiz Info to CardLayout Container
        JPanel mcInfoPanel = new JPanel();
        mcInfoPanel.setLayout(new GridBagLayout());
        mcInfoPanel.setBackground(new Color(232, 136, 216));
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
        mcInfo.setBackground(new Color(232, 136, 216));
        mcInfo.setFont(new Font("Arial", Font.BOLD, 24));
        mcInfo.setWrapStyleWord(true);
        mcInfo.setLineWrap(true);
        mcInfo.setEditable(false);
        mcInfo.setOpaque(false);

        // Button to go back from Multiple Choice Info view to Question view
        JButton toQuestionMenuMC = new JButton("Back");
        toQuestionMenuMC.setFont(new Font("Arial", Font.BOLD, 24));
        toQuestionMenuMC.setBackground(Color.WHITE);

        gbc.gridy = 1;
        mcInfoPanel.add(mcInfo, gbc);
        gbc.gridy = 3;
        mcInfoPanel.add(toMCQ, gbc);
        gbc.gridy = GridBagConstraints.SOUTH;
        mcInfoPanel.add(toQuestionMenuMC, gbc);


        // Creating and adding the panel for True Or False Quiz Info to CardLayout Container
        JPanel tfInfoPanel = new JPanel();
        tfInfoPanel.setLayout(new GridBagLayout());
        tfInfoPanel.setBackground(new Color(255,233,133));
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
        tfInfo.setBackground(new Color(255,233,133));
        tfInfo.setFont(new Font("Arial", Font.BOLD, 24));
        tfInfo.setWrapStyleWord(true);
        tfInfo.setLineWrap(true);
        tfInfo.setEditable(false);
        tfInfo.setOpaque(false);

        // Button to go back from True/False Info view to Question view
        JButton toQuestionMenuTF = new JButton("Back");
        toQuestionMenuTF.setFont(new Font("Arial", Font.BOLD,24));
        toQuestionMenuTF.setBackground(Color.WHITE);

        gbc.gridy = 1;
        tfInfoPanel.add(tfInfo, gbc);
        gbc.gridy = 3;
        tfInfoPanel.add(toTFQ, gbc);
        gbc.gridy = 5;
        tfInfoPanel.add(toQuestionMenuTF, gbc);


        // Creating and adding the panel for Estimate Quiz Info to CardLayout Container
        JPanel estInfoPanel = new JPanel();
        estInfoPanel.setLayout(new GridBagLayout());
        estInfoPanel.setBackground(new Color(204, 229, 255));
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
        estInfo.setBackground(new Color(204, 229, 255));
        estInfo.setFont(new Font("Arial", Font.BOLD, 24));
        estInfo.setWrapStyleWord(true);
        estInfo.setLineWrap(true);
        estInfo.setEditable(false);
        estInfo.setOpaque(false);

        // Button to go back from Estimate Info view to Question view
        JButton toQuestionMenuEst = new JButton("Back");
        toQuestionMenuEst.setFont(new Font("Arial", Font.BOLD, 24));
        toQuestionMenuEst.setBackground(Color.WHITE);



        gbc.gridy = 1;
        estInfoPanel.add(estInfo, gbc);
        gbc.gridy = 3;
        estInfoPanel.add(toEstQ, gbc);
        gbc.gridy= 5;
        estInfoPanel.add(toQuestionMenuEst, gbc);


        // Button to return to main menu from Question view
        gbc.gridy = 10;
        JButton toMainMenu = new JButton("Back");
        toMainMenu.setFont(new Font("Arial", Font.BOLD, 24));
        toMainMenu.setBackground(Color.WHITE);
        questionPanel.add(toMainMenu, gbc);
        


        mcsView = new MultipleChoicesSummaryView(this);
        mainPanel.add(mcsView, "MultipleChoiseSummary");

        tfsView = new TrueFalseSummaryView(this);
        mainPanel.add(tfsView, "TrueFalseSummary");

        esView = new EstimateSummaryView(this);
        mainPanel.add(esView, "EstimateSummary");


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
                model.restartQuiz();
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

        toMainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Menu"); // Switch to Main menu
            }
        });
        toQuestionMenuEst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(mainPanel, "Question"); // Switch to Question view from Estimate Info view
            }
        });

        toQuestionMenuTF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(mainPanel, "Question"); // Switch to Question view from True/False Info view
            }
        });
        
        toQuestionMenuMC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(mainPanel, "Question"); // Switch to Question view from Multiple Choice Info view
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
                exitButton.setFont(newFont);

                //  new button size based on the frame size
                int buttonWidth = frameWidth / 4;
                int buttonHeight = frameHeight / 10;
                Dimension newButtonSize = new Dimension(buttonWidth, buttonHeight);

                // update button sizes
                startButton.setPreferredSize(newButtonSize);
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

    public void showTrueFalseSummaryView(){
        cardLayout.show(mainPanel, "TrueFalseSummary");
    }

    public void showEstimateSummaryView(){
        cardLayout.show(mainPanel, "EstimateSummary");
    }

    public Container backToMenu(JPanel cardPanel){
        cardLayout.show(mainPanel, "Menu");
        mainPanel.remove(mcsView);
        mcsView = new MultipleChoicesSummaryView(this);
        mainPanel.add(mcsView, "MultipleChoiseSummary");
        mainPanel.remove(mcqView.getPanel());
        mcqView = new MultipleChoicesQuizView(this);
        mainPanel.add(mcqView.getPanel(), "MultipleChoice");

        // Reset quiz for the true and false questions
        mainPanel.remove(tfsView);
        tfsView = new TrueFalseSummaryView(this);
        mainPanel.add(tfsView, "TrueFalseSummary");
        mainPanel.remove(tfqView.getPanel());
        tfqView = new TrueFalseQuizView(this);
        mainPanel.add(tfqView.getPanel(), "TrueFalse");

        // Reset quiz for the estimate questions
        mainPanel.remove(esView);
        esView = new EstimateSummaryView(this);
        mainPanel.add(esView, "EstimateSummary");
        mainPanel.remove(eqView.getPanel());
        eqView = new EstimateQuizView(this);
        mainPanel.add(eqView.getPanel(), "Estimate");
        return cardPanel;
    }
}
