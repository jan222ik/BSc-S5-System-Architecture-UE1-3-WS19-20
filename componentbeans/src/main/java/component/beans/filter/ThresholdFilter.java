package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ThresholdFilter {

    private int thresh = 30;
    private int type = 1;

    public ThresholdFilter() {
    }

    private void process(ImgDTO entity) {
        Mat dst = new Mat();
        Imgproc.threshold(entity.getMat(), dst, thresh, 255, type);
        entity.setMat(dst);
    }
}
