package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import component.beans.util.CacheHelper;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;

public class ImgSource {

    private CacheHelper<String> cacheHelper = new CacheHelper<>();

    private String imgPath;


    public ImgSource() {

    }

    private ImgDTO read() {
        boolean exists = new File(imgPath).exists();
        System.out.println("exists = " + exists);
        if (exists) {
            ImgDTO imgDTO = new ImgDTO();
            Mat matrix = Imgcodecs.imread(imgPath);
            imgDTO.setMat(matrix);
            return imgDTO;
        } else return null;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
