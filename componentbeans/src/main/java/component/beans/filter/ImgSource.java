package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import component.beans.util.GUI;
import nu.pattern.OpenCV;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;

public class ImgSource {

    private PropertyChangeSupport mPcs = new PropertyChangeSupport(this);

    private String imgPath = "Z:\\Users\\jan22\\CodeProjects\\S5---System-Architecture\\componentbeans\\src\\main\\resources\\loetstellen.jpg";


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
            GUI.displayImage(imgDTO.getImage(), "Source");
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

    private void update() {
        System.out.println("Update source " + this);
        mPcs.firePropertyChange("srcNew", null, read());
    }
}
