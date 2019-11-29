package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import component.beans.util.CacheHelper;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MedianFilter {

    private PropertyChangeSupport mPcs = new PropertyChangeSupport(this);
    private CacheHelper<ImgDTO> cacheHelper = new CacheHelper<>();
    private int ksize = 19;

    public MedianFilter() {
        OpenCV.loadLocally();
    }


    private ImgDTO process() {
        ImgDTO entity = cacheHelper.getCache();
        if (entity != null) {
            Mat dst = new Mat();
            Imgproc.medianBlur(entity.getMat(), dst, ksize);
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

    public int getKsize() {
        return ksize;
    }

    public void setKsize(int ksize) {
        this.ksize = ksize;
    }
}
