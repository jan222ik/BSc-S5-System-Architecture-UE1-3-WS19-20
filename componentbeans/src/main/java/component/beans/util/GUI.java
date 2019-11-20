package component.beans.util;

import javax.swing.*;
import java.awt.*;

public class GUI {
    //Code from StackOverflow: https://stackoverflow.com/questions/26515981/display-image-using-mat-in-opencv-java
    public static void displayImage(Image img2, String title) {
        ImageIcon icon = new ImageIcon(img2);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(img2.getWidth(null) + 200, img2.getHeight(null) + 50);
        JLabel lbl = new JLabel("Size: " + icon.getIconWidth() + " x  " + icon.getIconHeight());
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setTitle(title);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
