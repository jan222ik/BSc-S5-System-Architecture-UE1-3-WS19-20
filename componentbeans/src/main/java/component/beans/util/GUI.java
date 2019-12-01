package component.beans.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class GUI {
    //Code from StackOverflow: https://stackoverflow.com/questions/26515981/display-image-using-mat-in-opencv-java
    public static GUIView displayImage(Image img2, String title) {
        System.out.println("Method: displayImage in Class: GUI");
        GUIView frame = new GUIView();
        displayImage(frame, img2, title);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        return frame;
    }

    public static GUIView displayImage(GUIView frame, Image img2, String title) {
        System.out.println("Method: displayImage for existing frame in Class: GUI");
        String text = "";
        if (img2 != null) {
            ImageIcon icon = new ImageIcon(img2);
            frame.setSize(img2.getWidth(null) + 200, img2.getHeight(null) + 50);
            text = "Size: " + icon.getIconWidth() + " x  " + icon.getIconHeight();
        } else {
            frame.setSize(300, 50);
            text = "Null value for the image - please fire property change event this view listens to";
        }
        frame.updateViewData(img2, text, title);
        return frame;
    }

    public static GUIView displayImage(String title, String text) {
        System.out.println("Method: displayImage in Class: GUI");
        GUIView frame = new GUIView();
        frame.updateViewData(null,"Obj.toString: " + text, title);
        frame.setSize(300, 50);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        return frame;
    }

    public static GUIView displayImage(GUIView frame, String title, String text) {
        System.out.println("Method: displayImage for existing frame in Class: GUI");
        frame.updateViewData(null,"Obj.toString: " + text, title);
        return frame;
    }

    public static class GUIView extends JFrame {
        private JLabel label;
        public GUIView() throws HeadlessException {
            super();
            setLayout(new FlowLayout());
            label = new JLabel("", SwingConstants.CENTER);
            add(label);
        }

        public void updateViewData(Image image, String text, String title) {
            label.setText(text);
            if (image != null) {
                ImageIcon icon = new ImageIcon(image);
                label.setIcon(icon);
            }
            setTitle(title);
        }
    }

}
