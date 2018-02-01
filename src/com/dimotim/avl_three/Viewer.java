package com.dimotim.avl_three;

import javax.swing.*;

public class Viewer extends JFrame{
    private JPanel mainPanel;
    private JTextField textField;
    private JButton addButton;
    private JButton deleteButton;
    private TreeViewPanel treeViewPanel;

    public static void main(String[] args) {
        new Viewer();
    }

    public Viewer(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setSize(800,600);
        setVisible(true);
    }
}

