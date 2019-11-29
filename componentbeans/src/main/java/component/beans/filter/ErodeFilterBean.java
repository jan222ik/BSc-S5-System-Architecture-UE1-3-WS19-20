package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import component.beans.util.CacheHelper;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class ErodeFilterBean implements PropertyChangeListener, Serializable {

    private CacheHelper<ImgDTO> cacheHelper = new CacheHelper<>();
    private int shapeType = 0;
    private int kernalSize = 2;
    private PropertyChangeSupport mPcs = new PropertyChangeSupport(this);


    public ErodeFilterBean() {
        OpenCV.loadLocally();
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


    private void update() {
        mPcs.firePropertyChange("erodeNew", null, process());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        cacheHelper.setCache((ImgDTO) evt.getNewValue(), ImgDTO::cloneDTO);
        update();
    }

    public int getShapeType() {
        return shapeType;
    }

    public void setShapeType(int shapeType) {
        int oldShapeType = this.shapeType;
        this.shapeType = shapeType;
        if (shapeType < 0 || shapeType > 4) {
            this.shapeType = oldShapeType;
        } else {
            update();
        }
    }

    public int getKernalSize() {
        return kernalSize;
    }

    public void setKernalSize(int kernalSize) {
        if (kernalSize > 0) {
            this.kernalSize = kernalSize;
            update();
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        mPcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }
}

