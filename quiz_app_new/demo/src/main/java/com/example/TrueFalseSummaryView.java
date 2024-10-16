package com.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TrueFalseSummaryView extends JPanel{
    private ProgressPanel progressPanel;
    private JButton startButton;
    private CardLayout cardLayout;
    private JLabel topLabel;
    private JLabel bottomLabel;
    private JPanel feedbackPanel;
    private JPanel controlPanel;
    private JPanel startPanel;
    private JPanel menuPanel;
    private JPanel cardPanel;
    private JPanel reviewPanel; 
    private JTextArea questionTextArea;


    private DefaultTableModel dataModel;

    private ModelTrueFalse m;
    private QuizGameView quizGameView;

    private Boolean animation = false;

    public TrueFalseSummaryView(){

    }

    public TrueFalseSummaryView(QuizGameView quizGameView) {
        this.quizGameView = quizGameView;
        setSize(720, 480);
        setBackground(new Color(80,100,230));
        // Set up the UI
        setupUI();
        add(cardPanel);
        setVisible(true);
    }

    // fetch the correct amount of answers the user got
    private int getCorrectAnswers(){
        ModelTrueFalse m = new ModelTrueFalse();
        String userName = m.getUserName();
        User user = User.getInstance(userName);
        return user.getCorrectAnswers();
    }

    // fetch the incorrect amount of answers the user got
    private int getIncorrectAnswers(){
        ModelTrueFalse m = new ModelTrueFalse();
        String userName = m.getUserName();
        User user = User.getInstance(userName);
        return user.getWrongAnswers();
    }

    // returns the proportion of correct answers
    private int proportionCorrect(){
        float totalquestions = getCorrectAnswers() + getIncorrectAnswers();
        float procent = (getCorrectAnswers()/totalquestions)*100;
        return (int) procent;
    }

    // runs the animation and updates the data to match the final result
    private void startProgress() { 
        new Thread(new Runnable() {
            @Override
            public void run() {
                progressPanel.setTargetProgress(proportionCorrect());
                for (int i = 0; i <= proportionCorrect(); i++) {
                    progressPanel.UpdateProgress(i);
                    progressPanel.repaint();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
                // Updates the view
                SwingUtilities.invokeLater(new Runnable() {
                    public void run(){
                    progressPanel.UpdateProgress(proportionCorrect());
                    progressPanel.repaint();

                    startButton.setEnabled(true);

                    feedbackPanel.setVisible(true);
                    topLabel.setForeground(progressPanel.getColor());
                    topLabel.setText(feedbackMessage(proportionCorrect()));
                    int total = getCorrectAnswers() + getIncorrectAnswers();
                    bottomLabel.setText("You scored " + getCorrectAnswers() + " out of " +  total + " ");
                    updateTable();
                    controlPanel.setVisible(true);
                    cardPanel.revalidate();
                    cardPanel.repaint();
                    }
                });
            }
        }).start();
    }

    private void setupUI() {

        ModelTrueFalse m = new ModelTrueFalse();  // Have to apply this every time I want to get username or it will give exception
        String userName = m.getUserName();
        User user = User.getInstance(userName);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);


        startPanel = createStartPanel();
        menuPanel = createMenuPanel();
        reviewPanel = createReviewPanel();

        cardPanel.add(startPanel, "Start");
        cardPanel.add(menuPanel, "Menu");
        cardPanel.add(reviewPanel, "Review");

        feedbackPanel = createFeedbackPanel();

        // Makes sure the animation only runs once
        if (!animation){
            feedbackPanel.setVisible(false);
        }

        // create the button panel below to navigate through the different panels
        controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,30));
        JButton showResultButton = new JButton("Show Result");
        showResultButton.setFont(new Font("Verdana", Font.BOLD, 14));
        showResultButton.setBackground(Color.RED);
        showResultButton.setForeground(Color.WHITE);
        JButton reviewResultButton = new JButton("Review answers");
        reviewResultButton.setFont(new Font("Verdana", Font.BOLD, 14));
        reviewResultButton.setBackground(Color.RED);
        reviewResultButton.setForeground(Color.WHITE);
        if (!animation){
            controlPanel.setVisible(false);
        }

        JButton returnToMenuButton = new JButton("Return to Menu");
        returnToMenuButton.setBackground(Color.RED);
        returnToMenuButton.setForeground(Color.WHITE);
        returnToMenuButton.setFont(new Font("Verdana", Font.BOLD, 14));

        showResultButton.addActionListener(e -> cardLayout.show(cardPanel, "Menu"));
        reviewResultButton.addActionListener(e -> cardLayout.show(cardPanel, "Review"));
        returnToMenuButton.addActionListener(e -> {cardLayout.show(quizGameView.backToMenu(cardPanel), "Return to Menu"); 
            // Reset UI 
            user.reset();
            animation = false;
            

        });


        controlPanel.setBackground(new Color(80,100,230));
        controlPanel.setPreferredSize(new Dimension(720,120));

        controlPanel.add(showResultButton);
        controlPanel.add(reviewResultButton);
        controlPanel.add(returnToMenuButton);


        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.SOUTH);
        add(cardPanel, BorderLayout.CENTER);
        add(feedbackPanel, BorderLayout.NORTH);


        setSize(720, 480);
        setBackground(new Color(80,100,230));
        setVisible(true);
    }

    public String feedbackMessage(int proportion){
        String topText;
        if (proportion >= 90){
            topText = "Excellent Job!";
        } else if (proportion >= 80 && proportion < 90){
            topText = "Well done!";
        } else if (proportion < 80 && proportion >= 60){
            topText = "Great Effort";
        } else if (proportion < 60 && proportion >= 40){
            topText = "Good Try";
        } else if (proportion < 40){
            topText = "You should try again!";
        } else {
            topText = "Unkown";
        }
        return topText;
    }

    // Create the top panel that displays a text and how many correct answers you got
    private JPanel createFeedbackPanel(){
        String topText;
        int topFontSize = 34;
        int bottomFontSize = 18;
        int correct = getCorrectAnswers();
        int total = getCorrectAnswers() + getIncorrectAnswers();
        String bottomText = "You scored " + correct + " out of " +  total + " ";

        topText = feedbackMessage(proportionCorrect());

        topLabel = new JLabel(topText, JLabel.CENTER);
        topLabel.setForeground(progressPanel.getColor());
        topLabel.setFont(new Font("Verdana", Font.BOLD, topFontSize));
        topLabel.setPreferredSize(new Dimension(getWidth(), 80));

        bottomLabel = new JLabel(bottomText, JLabel.CENTER);
        bottomLabel.setForeground(Color.WHITE);
        bottomLabel.setFont(new Font("Verdana", Font.BOLD, bottomFontSize));
        bottomLabel.setPreferredSize(new Dimension(getWidth(), 20));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        panel.setBackground(new Color(80,100,230));
        panel.setPreferredSize(new Dimension(getWidth(), 100));
        panel.add(topLabel, BorderLayout.NORTH);
        panel.add(bottomLabel, BorderLayout.SOUTH);

        return panel;

    }

    // Create the first panel that just shows view result button to start the animation
    private JPanel createStartPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(80,100,230));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagCon = new GridBagConstraints();
        gridBagCon.insets = new Insets(10,10,10,10);

        startButton = new JButton("View result");
        startButton.setFont(new Font("Verdana", Font.BOLD, 24));
        startButton.setBackground(Color.RED);
        startButton.setForeground(Color.WHITE);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gridBagCon.gridx = 0;
        gridBagCon.gridy = 0;

        panel.add(startButton, gridBagCon);


        // Button action to start progress
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);
                cardLayout.show(cardPanel, "Menu");
                startProgress();
            }
        });

        return panel;
    }

    // Create the panel that just shows the propersition of correct answers by a round progressbar
    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        progressPanel = new ProgressPanel();
        panel.setBackground(new Color(80,100,230));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagCon = new GridBagConstraints();
        gridBagCon.insets = new Insets(10,10,10,10);


        gridBagCon.gridx = 0;
        gridBagCon.gridy = 0;
        gridBagCon.weightx = 1.0;
        gridBagCon.weighty = 1.0;
        gridBagCon.fill= GridBagConstraints.BOTH;

        panel.add(progressPanel, gridBagCon);

        panel.revalidate();
        panel.repaint();

        return panel;
    }

    private void updateTable(){
        ModelTrueFalse m = new ModelTrueFalse();

        String userName = m.getUserName();
        User user = User.getInstance(userName);
        dataModel.setRowCount(0); // removes all rows
        for (int i = 0; i < m.getTotalQuestions() - 1; i++){
            String userAnswer;
            String correctAnswer = m.getQuestionCorrectAnswer(i);
            if (user.getHistory().get(i) == null){
                userAnswer = "No Answer";
            } else {
                userAnswer = user.getHistory().get(i);
            }
            Object[] rowData = {m.getQuestionText(i), correctAnswer, userAnswer, userAnswer.equals(correctAnswer) ? "✔" : "✘"};
            dataModel.addRow(rowData);
        }
    }

    private DefaultTableModel createTableData(){
        String[] columnNames = {"Question", "Correct Answer", "Your Answer", "Result"};

        ModelTrueFalse m = new ModelTrueFalse();

        String userName = m.getUserName();
        User user = User.getInstance(userName);

        Object[][] data = new Object[m.getTotalQuestions()-1][4];

        for (int i = 0; i < data.length; i++){
            String userAnswer;
            String correctAnswer = m.getQuestionCorrectAnswer(i);
            data[i][0] = m.getQuestionText(i);
            data[i][1] = correctAnswer;
            if (user.getHistory().get(i) == null){
                userAnswer = "No Answer";
            } else {
                userAnswer = user.getHistory().get(i);
            }
            data[i][2] = userAnswer;
            if (userAnswer.equals(correctAnswer)){
                data[i][3] = "✔";
            } else {
                data[i][3] = "✘";
            }
        }
        return new DefaultTableModel(data, columnNames);
    }

    private JPanel createReviewPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10,10));
        panel.setBackground(new Color(80,100,230));

        dataModel = createTableData();
        JTable table = new JTable(dataModel) {
            public boolean editCellAt(int row, int coloum, java.util.EventObject e){
                return false;
            }
        };


        table.setRowHeight(50);  // Set row height
        table.getColumnModel().getColumn(0).setPreferredWidth(100);  // Set column width
        table.getColumnModel().getColumn(1).setPreferredWidth(100);  // Set column width
        table.getColumnModel().getColumn(2).setPreferredWidth(100);  // Set column width
        table.getColumnModel().getColumn(3).setPreferredWidth(50);  // Set column width


        // Set up header font and color
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.setBackground(new Color(80,100,230));
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false); // Disable reordering headers


        // Paints every other row darker for better visability. 
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                cell.setForeground(Color.WHITE);
                if (row % 2 == 0) {
                    cell.setBackground(Color.GRAY);
                } else {
                    cell.setBackground(Color.LIGHT_GRAY);
                }
                return cell;
            }
        });
        
        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Check the value in the "Result" column
                if ("✔".equals(value)) {
                    cell.setForeground(Color.GREEN);  // Green for correct
                } else if ("✘".equals(value)) {
                    cell.setForeground(Color.RED);    // Red for incorrect
                } else {
                    cell.setForeground(Color.BLACK);  // Default color
                }
                // Paints every other cell darker for better visability.
                if (row % 2 == 0) {
                    cell.setBackground(Color.GRAY);
                } else {
                    cell.setBackground(Color.LIGHT_GRAY);
                }

                return cell;
            }
        });

        // Add mouse listener to the table to update the text area when a row is clicked
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                ModelTrueFalse m = new ModelTrueFalse();
                if (selectedRow != -1) {
                    String fullQuestion = (String) table.getValueAt(selectedRow, 0);
                    QuestionTrueFalse selectedQuestion = (QuestionTrueFalse) m.getQuestion(selectedRow);
                    String trivia = selectedQuestion.getTrivia();
                    String fullRespons = fullQuestion + "\n" + "You answered " + table.getValueAt(selectedRow, 1) + 
                    "\n" + "The correct answer was: " + table.getValueAt(selectedRow, 2) + 
                    "\n\n" + trivia;
                    if ("✔".equals(table.getValueAt(selectedRow, 3))){
                        fullRespons = fullQuestion + "\n" + "You answered " + table.getValueAt(selectedRow, 1) + 
                        "\n" + "You were correct!" + "\n\n" + trivia;
                    }
                    questionTextArea.setText(fullRespons);  // Adds the question + correct answer + your answer in text
                }
            }
        });

        // Create a text area to display the full question
        questionTextArea = new JTextArea(10, 30);
        questionTextArea.setWrapStyleWord(true);
        questionTextArea.setLineWrap(true);
        questionTextArea.setEditable(false);  // Read-only
        questionTextArea.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        questionTextArea.setBackground(Color.LIGHT_GRAY);
        questionTextArea.setBorder(BorderFactory.createTitledBorder("Full Question"));

        // Panel to hold the table and the text area
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        tablePanel.setBackground(new Color(80,100,230));
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Add both the table and text area to the main panel
        panel.add(tablePanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(questionTextArea), BorderLayout.EAST);
        return panel;


    }

}




