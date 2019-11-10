package imageprocessing.core.filter;

import imageprocessing.core.dataobj.ImgDTO;
import imageprocessing.framework.pmp.filter.DataTransformationFilter1;
import imageprocessing.framework.pmp.interfaces.Readable;
import imageprocessing.framework.pmp.interfaces.Writeable;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.security.InvalidParameterException;

public class ROIFilter extends DataTransformationFilter1<ImgDTO> {
    private Rect rect = new Rect(0, 35, 448, 80);

    public ROIFilter(Readable<ImgDTO> input, Writeable<ImgDTO> output) throws InvalidParameterException {
        super(input, output);
    }

    public ROIFilter(Readable<ImgDTO> input) throws InvalidParameterException {
        super(input);
    }

    public ROIFilter(Writeable<ImgDTO> output) throws InvalidParameterException {
        super(output);
    }


    @Override
    protected void process(ImgDTO entity) {
        Mat cropped = entity.getMat().submat(rect);
        entity.setMat(cropped);
        entity.setShiftedX(rect.x);
        entity.setShiftedY(rect.y);
    }
}
