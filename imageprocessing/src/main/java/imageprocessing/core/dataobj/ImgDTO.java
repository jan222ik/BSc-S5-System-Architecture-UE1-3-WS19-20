package imageprocessing.core.dataobj;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ImgDTO {
    private int shiftedX = 0;
    private int shiftedY = 0;
    private Mat mat = null;

    public ImgDTO() {
    }

    public BufferedImage getImage() {
        //Code from StackOverflow: https://stackoverflow.com/questions/26515981/display-image-using-mat-in-opencv-java
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = mat.channels() * mat.cols() * mat.rows();
        byte[] b = new byte[bufferSize];
        mat.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }

    public void setImage(Image image) {
        throw new NotImplementedException();
    }

    public int getShiftedX() {
        return shiftedX;
    }

    public void setShiftedX(int shiftedX) {
        this.shiftedX = shiftedX;
    }

    public int getShiftedY() {
        return shiftedY;
    }

    public void setShiftedY(int shiftedY) {
        this.shiftedY = shiftedY;
    }

    public Mat getMat() {
        return mat;
    }

    public void setMat(Mat mat) {
        this.mat = mat;
    }
}
