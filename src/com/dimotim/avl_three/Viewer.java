package com.dimotim.avl_three;

import javax.swing.*;

public class Viewer extends JFrame{
    private JPanel mainPanel;
    private JTextField textField;
    private JButton addButton;
    private JButton deleteButton;
    private TreeViewPanel treeViewPanel;
    private final AVLTree<Integer, ?> tree;

    public static void main(String[] args) {
        new Viewer();
    }

    public Viewer(AVLTree<Integer, ?> tree){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        if(tree==null)this.tree=new AVLTree<>();
        else this.tree=tree;
        treeViewPanel.setTree(this.tree);
        setListeners();
        setSize(800,600);
        setVisible(true);
    }

    public Viewer(){
        this(null);
    }

    private void setListeners(){
        addButton.addActionListener(e -> {
            tree.insert(Integer.parseInt(textField.getText()),null);
            treeViewPanel.updateTree();
            textField.setText("");
            textField.grabFocus();
        });
        deleteButton.addActionListener(e -> {
            tree.delete(Integer.parseInt(textField.getText()));
            treeViewPanel.updateTree();
            textField.setText("");
            textField.grabFocus();
        });
    }
}