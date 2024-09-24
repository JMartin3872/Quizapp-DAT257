package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class LoginView extends JFrame {
    private JPanel bgPanel;
    private JPanel textPanel;
    private JPanel buttonPanel;
    private JLabel usernameLabel;
    private JTextField textFieldUserId;
    private JButton buttonSignIn;
    private JButton buttonQuit;
    private String username;

    public LoginView(ActionListener signInListener) {
       
        setTitle("Sign in to the Quiz");
        setSize(400, 400);
        setLocation(((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - getWidth() / 2,
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 4);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        bgPanel = new JPanel(new BorderLayout());
        bgPanel.setSize(400, 400);

        textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 100));
        textPanel.setSize(100, 100);

        usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(new Font("Comic Sans", Font.BOLD, 20));

        textFieldUserId = new JTextField();
        textFieldUserId.setPreferredSize(new Dimension(150, 50));
        textFieldUserId.setFont(new Font("Comic Sans", Font.BOLD, 20));


        textPanel.add(usernameLabel);
        textPanel.add(textFieldUserId);
        

        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        buttonPanel.setPreferredSize(new Dimension(400,100));

        buttonSignIn = new JButton("Sign in");
        buttonSignIn.setFont(new Font("Comic Sans", Font.BOLD, 20));
        buttonSignIn.setPreferredSize(new Dimension(100,50));
        buttonSignIn.addActionListener(signInListener);
        buttonSignIn.setBackground(Color.GRAY);

        buttonQuit = new JButton("Quit");
        buttonQuit.setPreferredSize(new Dimension(100,50));
        buttonQuit.setFont(new Font("Comic Sans", Font.BOLD, 20));
        buttonQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        username = null;
        textFieldUserId.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

            }


            @Override
            public void keyPressed(KeyEvent e) {
                // do nothing
            }

            @Override
            public void keyReleased(KeyEvent e) {
                    if(textFieldUserId.getText().length() > 2){
                    username  = textFieldUserId.getText();
                    buttonSignIn.setBackground(Color.WHITE);
                }
                else{
                    username = null;
                    buttonSignIn.setBackground(Color.GRAY);
                }
            }
            });

        buttonPanel.add(buttonSignIn);
        buttonPanel.add(buttonQuit);
        bgPanel.add(textPanel, BorderLayout.NORTH);
        bgPanel.add(buttonPanel, BorderLayout.SOUTH);
        this.add(bgPanel);

        //this.setBackground(Color.GREEN);
        bgPanel.setBackground(new Color(37, 219, 131));
        buttonPanel.setBackground(new Color(37, 219, 131));
        textPanel.setBackground(new Color(37, 219, 131));

        bgPanel.setVisible(true);
        textPanel.setVisible(true);
        buttonPanel.setVisible(true);
        this.setVisible(true);

  }

  public String getUsername(){
    return this.username;
  }

    
       
}
