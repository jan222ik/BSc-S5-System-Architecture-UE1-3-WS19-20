package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import component.beans.util.CacheHelper;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ThresholdFilter {

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

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        mPcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }

    public int getThresh() {
        return thresh;
    }

    public void setThresh(int thresh) {
        this.thresh = thresh;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
