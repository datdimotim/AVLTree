package com.dimotim.avl_three;

import javax.swing.*;
import java.awt.*;

public class TreeViewPanel extends JPanel {
    private AVLTree<Integer,?> tree=new AVLTree<>();
    private final int sizeOfNode=30;
    private final int verticalOffset=20;

    public void setTree(AVLTree<Integer, ?> tree){
        this.tree=tree;
        updateTree();
    }

    public void updateTree(){
        updateUI();
    }

    @Override
    public void paint(Graphics g) {
        Rectangle bounds=g.getClipBounds();
        g.clearRect(bounds.x,bounds.y,bounds.width,bounds.height);
        Point center=new Point((bounds.x+bounds.width)/2,(bounds.y+bounds.height)/2);
        Point root=new Point(center.x,sizeOfNode);
        g.translate(root.x,root.y);
        paintTree(g,tree.root,new Point(0,0),center.x/2);
    }

    private void paintTree(Graphics g, AVLTree.Node<Integer,?> root, Point current, int interval){
        if(root==null)return;
        paintNode(g,current,root.key+" "+root.height);
        if(root.left!=null){
            Point leftCenter=paintLeft(g,current,interval);
            paintTree(g,root.left,leftCenter,interval/2);
        }
        if(root.right!=null){
            Point rightCenter=paintRight(g,current,interval);
            paintTree(g,root.right,rightCenter,interval/2);
        }
    }

    private void paintNode(Graphics g, Point center, String val){
        Point rightUpCorner=new Point(center.x-sizeOfNode/2,center.y-sizeOfNode/2);
        g.drawRect(rightUpCorner.x,rightUpCorner.y,sizeOfNode,sizeOfNode);
        g.drawString(val,rightUpCorner.x+7,rightUpCorner.y+20);
    }

    private Point paintLeft(Graphics g, Point center, int interval){
        Point from=new Point(center.x-5,center.y+sizeOfNode/2);
        Point to=new Point(center.x-interval,from.y+verticalOffset);
        g.drawLine(from.x,from.y,to.x,to.y);
        return new Point(to.x,to.y+sizeOfNode/2);
    }

    private Point paintRight(Graphics g, Point center, int interval){
        Point from=new Point(center.x+5,center.y+sizeOfNode/2);
        Point to=new Point(center.x+interval,from.y+verticalOffset);
        g.drawLine(from.x,from.y,to.x,to.y);
        return new Point(to.x,to.y+sizeOfNode/2);
    }
}
