package imageprocessing.core.filter;

import imageprocessing.core.dataobj.ImgDTO;
import imageprocessing.framework.pmp.filter.Source;
import imageprocessing.framework.pmp.interfaces.Writeable;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

public class ImgSource extends Source<ImgDTO> {

    private String imgPath;
    private boolean loaded = false;

    public ImgSource(String imgPath, Writeable<ImgDTO> output) throws InvalidParameterException {
        super(output);
        this.imgPath = imgPath;
    }

    public ImgSource(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public ImgDTO read() throws StreamCorruptedException {
        if (loaded) return null;
        boolean exists = new File(imgPath).exists();
        System.out.println("exists = " + exists);
        Mat matrix = Imgcodecs.imread(imgPath);
        ImgDTO imgDTO = new ImgDTO();
        imgDTO.setMat(matrix);
        loaded = true;
        return imgDTO;
    }
}
