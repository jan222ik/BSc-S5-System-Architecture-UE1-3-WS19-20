package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import component.beans.util.BeanMethods;
import component.beans.util.CacheHelper;
import component.beans.util.GUI;
import component.beans.util.SetterHelper;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ThresholdFilter implements BeanMethods {

    private CacheHelper<ImgDTO> cacheHelper = new CacheHelper<>();
    private PropertyChangeSupport mPcs = new PropertyChangeSupport(this);

    private int thresh = 30;
    private int type = 1;

    public ThresholdFilter() {
        OpenCV.loadLocally();
    }

    private ImgDTO process() {
        ImgDTO entity = cacheHelper.getCache();
        if (entity != null) {
            Mat dst = new Mat();
            Imgproc.threshold(entity.getMat(), dst, thresh, 255, type);
            entity.setMat(dst);
        }
        return entity;
    }

    @Override
    public void update() {
        ImgDTO process = process();
        if (process != null) {
            GUI.displayImage(process.getImage(), "Latest Threshold");
        }
        mPcs.firePropertyChange("thresholdNew", null, process);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        mPcs.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }

    public int getThresh() {
        return thresh;
    }

    public void setThresh(int thresh) {
        SetterHelper.notNeg(thresh, () -> {
            this.thresh = thresh;
            update();
        });
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        SetterHelper.between(type, 0, 4, () -> {
            this.type = type;
            update();
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        cacheHelper.setCache((ImgDTO) evt.getNewValue(), ImgDTO::cloneDTO);
        update();
    }
}
