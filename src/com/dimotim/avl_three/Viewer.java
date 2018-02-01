package com.dimotim.avl_three;

import javax.swing.*;

public class Viewer extends JFrame{
    private JPanel mainPanel;
    private JTextField textField;
    private JButton addButton;
    private JButton deleteButton;
    private TreeViewPanel treeViewPanel;
    private AVLTree<Integer, Void> tree=new AVLTree<>();

    public static void main(String[] args) {
        new Viewer();
    }

    public Viewer(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        treeViewPanel.setTree(tree);
        setListeners();
        setSize(800,600);
        setVisible(true);
    }

    private void setListeners(){
        addButton.addActionListener(e -> {
            tree.insert(Integer.parseInt(textField.getText()),null);
            treeViewPanel.updateTree();
        });
        deleteButton.addActionListener(e -> {
            tree.delete(Integer.parseInt(textField.getText()));
            treeViewPanel.updateTree();
        });
    }
}

