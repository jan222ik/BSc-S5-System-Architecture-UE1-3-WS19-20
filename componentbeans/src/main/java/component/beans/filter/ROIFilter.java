package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import component.beans.util.CacheHelper;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class ROIFilter {

    private CacheHelper<ImgDTO> cacheHelper = new CacheHelper<>();
    private int x = 0;
    private int y = 35;
    private int width = 448;
    private int height = 80;

    public ROIFilter() {
    }

    protected ImgDTO process() {
        ImgDTO entity = cacheHelper.getCache();
        if (entity != null) {
            Rect rect = new Rect(x, y, width, height);
            Mat cropped = entity.getMat().submat(rect);
            entity.setMat(cropped);
            entity.setShiftedX(rect.x);
            entity.setShiftedY(rect.y);
        }
        return entity;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
