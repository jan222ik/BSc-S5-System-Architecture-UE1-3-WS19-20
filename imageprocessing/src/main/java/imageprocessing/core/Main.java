package imageprocessing.core;



import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

public class Main {

    public static void main(String... args) {
        //Loading the OpenCV core library
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        OpenCV.loadLocally();

        //Instantiating the Imagecodecs class
        Imgcodecs imageCodecs = new Imgcodecs();

        //Reading the Image from the file
        String file = "imageprocessing/src/main/resources/loetstellen.jpg";
        boolean exists = new File(file).exists();
        System.out.println("exists = " + exists);
        Mat matrix = imageCodecs.imread(file);
        System.out.println("matrix = " + matrix);
        Rect rect = new Rect(0 , 35, 448, 80);
        Mat croped = matrix.submat(rect);
        BufferedImage bufferedImage2 = Mat2BufferedImage(croped);
        displayImage(bufferedImage2, "Croped");
        Imgproc.rectangle(matrix, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 255, 255, 255), 3);
        Mat dst = new Mat();
        Imgproc.threshold(croped, dst, 30, 255, 1);
        BufferedImage bufferedImage = Mat2BufferedImage(matrix);
        displayImage(bufferedImage, "ROI");
        BufferedImage bufferedImage3 = Mat2BufferedImage(dst);
        displayImage(bufferedImage3, "Thresch");

        System.out.println("Image Loaded ");

    }



    //Code from StackOverflow: https://stackoverflow.com/questions/26515981/display-image-using-mat-in-opencv-java
    public static BufferedImage Mat2BufferedImage(Mat m) {
        // Fastest code
        // output can be assigned either to a BufferedImage or to an Image

        int type = BufferedImage.TYPE_BYTE_GRAY;
        if ( m.channels() > 1 ) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels()*m.cols()*m.rows();
        byte [] b = new byte[bufferSize];
        m.get(0,0,b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }
    //Code from StackOverflow: https://stackoverflow.com/questions/26515981/display-image-using-mat-in-opencv-java
    public static void displayImage(Image img2, String title) {
        ImageIcon icon=new ImageIcon(img2);
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(img2.getWidth(null)+200, img2.getHeight(null)+50);
        JLabel lbl=new JLabel("Size: "+ icon.getIconWidth() + " x  " + icon.getIconHeight());
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setTitle(title);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
