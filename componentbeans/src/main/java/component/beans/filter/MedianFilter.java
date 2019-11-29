package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import component.beans.util.CacheHelper;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MedianFilter {

    private CacheHelper<ImgDTO> cacheHelper = new CacheHelper<>();
    private int ksize = 19;

    public MedianFilter() {
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

    public int getKsize() {
        return ksize;
    }

    public void setKsize(int ksize) {
        this.ksize = ksize;
    }
}
