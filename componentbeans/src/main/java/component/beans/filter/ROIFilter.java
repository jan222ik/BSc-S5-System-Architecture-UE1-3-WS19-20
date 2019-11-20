package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class ROIFilter {

    private int x = 0;
    private int y = 35;
    private int width = 448;
    private int height = 80;


    protected void process(ImgDTO entity) {
        Rect rect = new Rect(x, y, width, height);
        Mat cropped = entity.getMat().submat(rect);
        entity.setMat(cropped);
        entity.setShiftedX(rect.x);
        entity.setShiftedY(rect.y);
    }
}
