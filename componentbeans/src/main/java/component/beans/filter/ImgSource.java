package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.io.StreamCorruptedException;

public class ImgSource {

    private String imgPath;
    private boolean loaded = false;


    public ImgSource(String imgPath) {
        this.imgPath = imgPath;
    }


    private ImgDTO read() throws StreamCorruptedException {
        if (loaded) return null; //Brauchen wir noch ?
        boolean exists = new File(imgPath).exists();
        System.out.println("exists = " + exists);
        if (exists) {
            ImgDTO imgDTO = new ImgDTO();
            Mat matrix = Imgcodecs.imread(imgPath);
            imgDTO.setMat(matrix);
            loaded = true;
            return imgDTO;
        } else return null;
    }
}
