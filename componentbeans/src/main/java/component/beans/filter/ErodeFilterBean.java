package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import component.beans.util.CacheHelper;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class ErodeFilterBean implements Serializable {

    private CacheHelper<ImgDTO> cacheHelper = new CacheHelper<>();
    private int shapeType;
    private int kernalSize;
    private PropertyChangeSupport mPcs = new PropertyChangeSupport(this);


    public ErodeFilterBean() {
        PropertyChangeListener listener = evt -> {
                if (evt.getOldValue() != evt.getNewValue()) {
                    mPcs.firePropertyChange("erodeNew", null, process());
                }
        };
        mPcs.addPropertyChangeListener("shapeType", listener);
        mPcs.addPropertyChangeListener("kernalSize", listener);
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


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        mPcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }

    public int getShapeType() {
        return shapeType;
    }

    public void setShapeType(int shapeType) {
        int oldShapeType = this.shapeType;
        this.shapeType = shapeType;
        mPcs.firePropertyChange("shapeType",
                oldShapeType, shapeType);
    }

    public int getKernalSize() {
        return kernalSize;
    }

    public void setKernalSize(int kernalSize) {
        int oldKernalSize = this.kernalSize;
        this.kernalSize = kernalSize;
        mPcs.firePropertyChange("kernalSize", oldKernalSize, kernalSize);
    }

}

