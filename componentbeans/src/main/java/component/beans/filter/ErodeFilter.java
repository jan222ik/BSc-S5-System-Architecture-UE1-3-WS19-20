package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ErodeFilter {

    private int shapeType;
    private int kernalSize;

    public ErodeFilter() {
    }

    private void process(ImgDTO entity) {
        Mat element = Imgproc.getStructuringElement(shapeType, new Size(2 * kernalSize + 1, 2 * kernalSize + 1), new Point(kernalSize, kernalSize));
        Mat dst = new Mat();
        Imgproc.erode(entity.getMat(), dst, element);
        entity.setMat(dst);
    }
}
