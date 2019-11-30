package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import component.beans.util.BeanMethods;
import component.beans.util.CacheHelper;
import component.beans.util.SetterHelper;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class ErodeFilterBean implements BeanMethods {

    private CacheHelper<ImgDTO> cacheHelper = new CacheHelper<>();
    private int shapeType = 0;
    private int kernalSize = 2;
    private PropertyChangeSupport mPcs = new PropertyChangeSupport(this);


    public ErodeFilterBean() {
        OpenCV.loadLocally();
    }

    private ImgDTO process() {
        ImgDTO entity = cacheHelper.getCache();
        if (entity != null) {
            Mat element = Imgproc.getStructuringElement(shapeType, new Size(2 * kernalSize + 1D, 2 * kernalSize + 1D), new Point(kernalSize, kernalSize));
            Mat dst = new Mat();
            Imgproc.erode(entity.getMat(), dst, element);
            entity.setMat(dst);
        }
        return entity;
    }

    @Override
    public void update() {
        mPcs.firePropertyChange("erodeNew", null, process());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SetterHelper.ifClass(evt.getNewValue(), ImgDTO.class, () -> {
            cacheHelper.setCache((ImgDTO) evt.getNewValue(), ImgDTO::cloneDTO);
            update();
        });
    }

    public int getShapeType() {
        return shapeType;
    }

    public void setShapeType(int shapeType) {
        SetterHelper.between(shapeType, 0, 4, () -> {
            this.shapeType = shapeType;
            update();
        });
    }

    public int getKernalSize() {
        return kernalSize;
    }

    public void setKernalSize(int kernalSize) {
        SetterHelper.notNeg(kernalSize, () -> {
            this.kernalSize = kernalSize;
            update();
        });

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        mPcs.addPropertyChangeListener(listener);
    }
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }
}

