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


public class MultipleChoicesSummaryView extends JPanel {
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
    private int incorrectAnswers = 6;   
    private int correctAnswers = 4;     
    private int questionsAnswered = 10;  
    private JTextArea questionTextArea;

    private Boolean animation = false;

    public MultipleChoicesSummaryView() {
        setSize(720, 480);
        setBackground(new Color(80,100,230));
        setupUI();
        add(cardPanel);
        setVisible(true);
    }

    private int proportionCorrect(){
        float totalquestions = incorrectAnswers + correctAnswers;
        float procent = (correctAnswers/totalquestions)*100;
        return (int) procent;
    }

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
                
                SwingUtilities.invokeLater(new Runnable() {
                    public void run(){
                    progressPanel.UpdateProgress(proportionCorrect());
                    progressPanel.repaint();

                    startButton.setEnabled(true);

                    feedbackPanel.setVisible(true);
                    topLabel.setForeground(progressPanel.getColor());
                    controlPanel.setVisible(true);
                    cardPanel.revalidate();
                    cardPanel.repaint();
                    }
                });
            }
        }).start();
    }

    private void setupUI() {

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);


        startPanel = createStartPanel();
        menuPanel = createMenuPanel();
        reviewPanel = createReviewPanel();

        cardPanel.add(startPanel, "Start");
        cardPanel.add(menuPanel, "Menu");
        cardPanel.add(reviewPanel, "Review");

        feedbackPanel = createFeedbackPanel();
        if (!animation){
            feedbackPanel.setVisible(false);
        }

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

        showResultButton.addActionListener(e -> {cardLayout.show(cardPanel, "Menu");});
        reviewResultButton.addActionListener(e -> cardLayout.show(cardPanel, "Review"));

        controlPanel.setBackground(new Color(80,100,230));
        controlPanel.setPreferredSize(new Dimension(720,120));

        controlPanel.add(showResultButton);
        controlPanel.add(reviewResultButton);


        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.SOUTH);
        add(cardPanel, BorderLayout.CENTER);
        add(feedbackPanel, BorderLayout.NORTH);


        setSize(720, 480);
        setBackground(new Color(80,100,230));
        setVisible(true);
    }


    private JPanel createFeedbackPanel(){
        String topText;
        int topFontSize = 34;
        int bottomFontSize = 18;
        String bottomText = "You scored " + correctAnswers + " out of " +  questionsAnswered + " ";

        int proportion = proportionCorrect();
        if (proportion > 90){
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

    private DefaultTableModel createTableData(){
        String[] columnNames = {"Question", "Correct Answer", "Your Answer", "Result"};

        // Data for the table, Change to getting data from model

        Object[][] data = {
            {"Train Journey: Stockholm - Gothenburg. A round trip totaling 900 km by high-speed train."
            , "5KG", "5KG", "✔"},
            {"Cheese Sandwiches: Two cheese sandwiches with butter every day for a year."
            , "250KG", "200KG", "✘"},
            {"Watching TV: 4 hours a day. Having your 65-inch TV on for 4 hours a day for a year."
            , "40 KG", "40KG", "✔"},
            {"Package Delivery: By air from China. Shipping a package (25 liters) of, for example, clothes bought online once a month for a year."
            , "250KG", "300KG", "✘"},
            {"Heating an Apartment: Heating a 70 m² apartment in central Sweden with district heating."
            , "600KG", "300KG", "✘"},
            {"Halved Food Waste: Reduced emissions from halving food waste over a year for an average Swede."
            , "200KG", "200KG", "✔"},
            {"Heating with Electric Heaters: Heating a 140 m² house in central Sweden with direct electric heating (Swedish electricity mix)."
            , "700KG", "500KG", "✘"},
            {"Flights: Swedish yearly average. Flying as an average Swede over a year (CO₂ only)."
            , "600KG", "600KG", "✔"},
            {"T-bone Steak: Beef. A meal with 150g of beef every day for a year."
            , "3000KG", "2000KG", "✘"},
            {"Hand Washing Dishes: Using filled water. Washing dishes with filled water from district heating every day for a year."
            , "400KG", "450KG", "✘"}
        };

        return new DefaultTableModel(data, columnNames);
    }

    private JPanel createReviewPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10,10));
        panel.setBackground(new Color(80,100,230));

        DefaultTableModel model = createTableData();
        JTable table = new JTable(model);


        table.setRowHeight(30);  // Set row height
        table.getColumnModel().getColumn(0).setPreferredWidth(100);  // Set column width
        table.getColumnModel().getColumn(1).setPreferredWidth(100);  // Set column width
        table.getColumnModel().getColumn(2).setPreferredWidth(100);  // Set column width
        table.getColumnModel().getColumn(3).setPreferredWidth(50);  // Set column width


        // Set up header font and color
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.setBackground(new Color(80,100,230));
        header.setForeground(Color.WHITE);


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
                if (selectedRow != -1) {
                    String fullQuestion = (String) table.getValueAt(selectedRow, 0);
                    String fullRespons = fullQuestion + "\n" + "You answered " + table.getValueAt(selectedRow, 1) + 
                    "\n" + "The correct answer was: " + table.getValueAt(selectedRow, 2) + 
                    "\n" + "Addition information";
                    if ("✔".equals(table.getValueAt(selectedRow, 3))){
                        fullRespons = fullQuestion + "\n" + "You answered " + table.getValueAt(selectedRow, 1) + 
                        "\n" + "You were correct!" + "\n" + "Addition information";
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


