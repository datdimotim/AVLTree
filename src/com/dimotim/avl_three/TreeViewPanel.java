package com.dimotim.avl_three;

import javax.swing.*;
import java.awt.*;

public class TreeViewPanel extends JPanel {
    @Override
    public void paint(Graphics g) {
        Rectangle bounds=g.getClipBounds();
        Point center=new Point((bounds.x+bounds.width)/2,(bounds.y+bounds.height)/2);
        g.drawOval(center.x-25,center.y-25,50,50);
    }
}
