package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import component.beans.util.BeanMethods;
import component.beans.util.GUI;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;

public class ImgSource extends Canvas implements BeanMethods {

    private PropertyChangeSupport mPcs = new PropertyChangeSupport(this);

    private String imgPath = "Z:\\Users\\jan22\\CodeProjects\\S5---System-Architecture\\componentbeans\\src\\main\\resources\\loetstellen.jpg";
    private BufferedImage temp = null;


    public ImgSource() {
        OpenCV.loadLocally();
        System.out.println("New Source");
        update();
    }

    private ImgDTO read() {
        boolean exists = new File(imgPath).exists();
        System.out.println("Source file exists = " + exists);
        if (exists) {
            ImgDTO imgDTO = new ImgDTO();
            Mat matrix = Imgcodecs.imread(imgPath);
            imgDTO.setMat(matrix);
            return imgDTO;
        } else return null;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        mPcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        System.out.println("Setter imgSource " + imgPath);
        this.imgPath = imgPath;
        update();
    }

    public void update() {
        System.out.println("Update source " + this);
        ImgDTO read = read();
        mPcs.firePropertyChange("srcNew", null, read);
        if (read != null) {
            temp = read.getImage();
        }
        print(getGraphics());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Cant update the source");
    }

    @Override
    public void paint(Graphics g) {
        if (g != null) {
            super.paint(g);
            g.drawString("ImgSource",0, 0);
            if (temp != null) {
                g.drawImage(temp, 0, 10, (img, infoflags, x, y, width, height) -> false);
                Dimension dimension = new Dimension(10 + temp.getHeight(), Math.max(temp.getWidth(), 50));
                setMinimumSize(dimension);
                setSize(dimension);
                setPreferredSize(dimension);
                setMaximumSize(dimension);
            } else {
                Dimension dimension = new Dimension(10, 50);
                setMinimumSize(dimension);
                setSize(dimension);
                setPreferredSize(dimension);
                setMaximumSize(dimension);
            }
        }
    }
}
