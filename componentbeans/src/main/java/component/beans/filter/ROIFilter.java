package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import component.beans.util.BeanMethods;
import component.beans.util.CacheHelper;
import component.beans.util.GUI;
import component.beans.util.SetterHelper;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ROIFilter implements BeanMethods {

    private CacheHelper<ImgDTO> cacheHelper = new CacheHelper<>();
    private PropertyChangeSupport mPcs = new PropertyChangeSupport(this);
    private int x = 0;
    private int y = 35;
    private int width = 448;
    private int height = 80;

    public ROIFilter() {
        OpenCV.loadLocally();
    }

    protected ImgDTO process() {
        ImgDTO entity = cacheHelper.getCache();
        if (entity != null) {
            Rect rect = new Rect(x, y, width, height);
            Mat cropped = entity.getMat().submat(rect);
            entity.setMat(cropped);
            entity.setShiftedX(rect.x);
            entity.setShiftedY(rect.y);
        }
        return entity;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SetterHelper.ifClass(evt.getNewValue(), ImgDTO.class, () -> {
            cacheHelper.setCache((ImgDTO) evt.getNewValue(), ImgDTO::cloneDTO);
            update();
        });
    }

    @Override
    public void update() {
        ImgDTO process = process();
        GUI.displayImage(process.getImage(), "Latest ROI");
        mPcs.firePropertyChange("roiNew", null, process);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        SetterHelper.notNeg(x, () -> {
            this.x = x;
            update();
        });
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        SetterHelper.notNeg(y, () -> {
            this.y = y;
            update();
        });
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        SetterHelper.notNeg(width, () -> {
            this.width = width;
            update();
        });
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        SetterHelper.notNeg(height, () -> {
            this.height = height;
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
