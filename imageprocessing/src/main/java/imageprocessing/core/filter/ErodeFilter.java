package imageprocessing.core.filter;

import imageprocessing.core.dataobj.ImgDTO;
import imageprocessing.framework.pmp.filter.DataTransformationFilter1;
import imageprocessing.framework.pmp.interfaces.Readable;
import imageprocessing.framework.pmp.interfaces.Writeable;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.security.InvalidParameterException;

public class ErodeFilter extends DataTransformationFilter1<ImgDTO> {

    private int shapeType;
    private int kernalSize;

    public ErodeFilter(int shapeType, int kernalSize, Readable<ImgDTO> input, Writeable<ImgDTO> output) throws InvalidParameterException {
        super(input, output);
        this.shapeType = shapeType;
        this.kernalSize = kernalSize;
    }

    public ErodeFilter(int shapeType, int kernalSize, Readable<ImgDTO> input) throws InvalidParameterException {
        super(input);
        this.shapeType = shapeType;
        this.kernalSize = kernalSize;
    }

    public ErodeFilter(int shapeType, int kernalSize, Writeable<ImgDTO> output) throws InvalidParameterException {
        super(output);
        this.shapeType = shapeType;
        this.kernalSize = kernalSize;
    }

    @Override
    protected void process(ImgDTO entity) {
        Mat element = Imgproc.getStructuringElement(shapeType, new Size(2 * kernalSize + 1, 2 * kernalSize + 1), new Point(kernalSize, kernalSize));
        Mat dst = new Mat();
        Imgproc.erode(entity.getMat(), dst, element);
        entity.setMat(dst);
    }
}
