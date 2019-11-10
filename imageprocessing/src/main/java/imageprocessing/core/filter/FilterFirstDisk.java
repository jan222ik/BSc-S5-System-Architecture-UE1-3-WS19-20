package imageprocessing.core.filter;

import imageprocessing.framework.pmp.filter.DataTransformationFilter2;
import imageprocessing.framework.pmp.img.Coordinate;
import imageprocessing.framework.pmp.interfaces.Readable;
import imageprocessing.framework.pmp.interfaces.Writeable;

import java.util.List;

public class FilterFirstDisk extends DataTransformationFilter2<List<Coordinate>, List<Coordinate>> {

    private int removeFirstN;

    public FilterFirstDisk(int removeFirstN, Readable<List<Coordinate>> input, Writeable<List<Coordinate>> output) {
        super(input, output);
        this.removeFirstN = removeFirstN;
    }

    public FilterFirstDisk(int removeFirstN, Readable<List<Coordinate>> input) {
        super(input);
        this.removeFirstN = removeFirstN;
    }

    public FilterFirstDisk(int removeFirstN, Writeable<List<Coordinate>> output) {
        super(output);
        this.removeFirstN = removeFirstN;
    }

    @Override
    protected List<Coordinate> process(List<Coordinate> entity) {
        return entity.subList(removeFirstN, entity.size() - 1);
    }
}
