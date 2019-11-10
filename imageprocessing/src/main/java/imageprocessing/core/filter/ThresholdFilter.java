package imageprocessing.core.filter;

import imageprocessing.core.dataobj.ImgDTO;
import imageprocessing.framework.pmp.filter.DataTransformationFilter1;
import imageprocessing.framework.pmp.interfaces.Readable;
import imageprocessing.framework.pmp.interfaces.Writeable;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.security.InvalidParameterException;

public class ThresholdFilter extends DataTransformationFilter1<ImgDTO> {
    public ThresholdFilter(Readable<ImgDTO> input, Writeable<ImgDTO> output) throws InvalidParameterException {
        super(input, output);
    }

    public ThresholdFilter(Readable<ImgDTO> input) throws InvalidParameterException {
        super(input);
    }

    public ThresholdFilter(Writeable<ImgDTO> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected void process(ImgDTO entity) {
        Mat dst = new Mat();
        Imgproc.threshold(entity.getMat(), dst, 30, 255, 1);
        entity.setMat(dst);
    }
}
