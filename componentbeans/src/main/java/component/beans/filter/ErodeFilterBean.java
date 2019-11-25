package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class ErodeFilterBean implements Serializable {

    private int shapeType = 0;
    private int kernalSize = 12;
    private PropertyChangeSupport mPcs =
            new PropertyChangeSupport(this);


    public ErodeFilterBean() {
    }


    public void
    addPropertyChangeListener(PropertyChangeListener listener) {
        mPcs.addPropertyChangeListener(listener);
    }

    public void
    removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }

    private void process(ImgDTO entity) {
        Mat element = Imgproc.getStructuringElement(shapeType, new Size(2 * kernalSize + 1, 2 * kernalSize + 1), new Point(kernalSize, kernalSize));
        Mat dst = new Mat();
        Imgproc.erode(entity.getMat(), dst, element);
        entity.setMat(dst);
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
        mPcs.firePropertyChange("kernalSize",
                oldKernalSize, kernalSize);
    }

}
