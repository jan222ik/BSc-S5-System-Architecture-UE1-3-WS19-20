package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MedianFilter {

    private int ksize;

    public MedianFilter() {
    }


    private void process(ImgDTO entity) {
        Mat dst = new Mat();
        Imgproc.medianBlur(entity.getMat(), dst, ksize);
        entity.setMat(dst);
    }
}
