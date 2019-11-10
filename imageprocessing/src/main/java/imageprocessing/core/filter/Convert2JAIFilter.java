package imageprocessing.core.filter;

import imageprocessing.core.dataobj.ImgDTO;
import imageprocessing.framework.pmp.filter.DataTransformationFilter2;
import imageprocessing.framework.pmp.img.CalcCentroidsFilter;
import imageprocessing.framework.pmp.img.Coordinate;
import imageprocessing.framework.pmp.interfaces.Readable;
import imageprocessing.framework.pmp.interfaces.Writeable;

import javax.media.jai.PlanarImage;
import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;
import java.util.List;

public class Convert2JAIFilter extends DataTransformationFilter2<ImgDTO, List<Coordinate>> {
    private final CalcCentroidsFilter filter = new CalcCentroidsFilter(() -> null);

    public Convert2JAIFilter(Readable<ImgDTO> input, Writeable<List<Coordinate>> output) throws InvalidParameterException {
        super(input, output);
    }

    public Convert2JAIFilter(Readable<ImgDTO> input) throws InvalidParameterException {
        super(input);
    }

    public Convert2JAIFilter(Writeable<List<Coordinate>> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected List<Coordinate> process(ImgDTO entity) {
        BufferedImage image = entity.getImage();
        PlanarImage planarImage = PlanarImage.wrapRenderedImage(image);
        planarImage.setProperty("offsetX", entity.getShiftedX());
        planarImage.setProperty("offsetY", entity.getShiftedY());
        return filter.process(planarImage);
    }
}
