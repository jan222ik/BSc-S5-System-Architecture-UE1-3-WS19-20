package imageprocessing.core.filter;

import imageprocessing.core.dataobj.ImgDTO;
import imageprocessing.framework.pmp.filter.DataTransformationFilter1;
import imageprocessing.framework.pmp.interfaces.Readable;
import imageprocessing.framework.pmp.interfaces.Writeable;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.security.InvalidParameterException;

public class MedianFilter extends DataTransformationFilter1<ImgDTO> {

    private int ksize;

    public MedianFilter(int ksize, Readable<ImgDTO> input, Writeable<ImgDTO> output) throws InvalidParameterException {
        super(input, output);
        this.ksize = ksize;
    }

    public MedianFilter(int ksize, Readable<ImgDTO> input) throws InvalidParameterException {
        super(input);
        this.ksize = ksize;
    }

    public MedianFilter(int ksize, Writeable<ImgDTO> output) throws InvalidParameterException {
        super(output);
        this.ksize = ksize;
    }

    @Override
    protected void process(ImgDTO entity) {
        Mat dst = new Mat();
        Imgproc.medianBlur(entity.getMat(), dst, ksize);
        entity.setMat(dst);
    }
}
