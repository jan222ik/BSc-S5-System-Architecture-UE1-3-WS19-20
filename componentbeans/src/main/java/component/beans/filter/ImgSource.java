package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import component.beans.util.BeanMethods;
import component.beans.util.SetterHelper;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;

public class ImgSource implements BeanMethods {

    private PropertyChangeSupport mPcs = new PropertyChangeSupport(this);

    private String imgPath = "Z:\\Users\\jan22\\CodeProjects\\S5---System-Architecture\\componentbeans\\src\\main\\resources\\loetstellen.jpg";


    public ImgSource() {
        System.out.println("Constructor: ImgSource in Class: ImgSource");
        SetterHelper.initOpenCV();
        update();
    }

    private ImgDTO read() {
        try {
            boolean exists = new File(imgPath).exists();
            System.out.println("Source file exists = " + exists);
            if (exists) {
                ImgDTO imgDTO = new ImgDTO();
                Mat matrix = Imgcodecs.imread(imgPath);
                imgDTO.setMat(matrix);
                return imgDTO;
            } else return null;
        } catch (NullPointerException npe) {
            return null;
        }
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
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Cant update the source");
    }
}
