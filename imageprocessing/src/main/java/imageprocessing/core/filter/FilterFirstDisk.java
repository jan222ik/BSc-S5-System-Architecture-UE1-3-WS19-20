package imageprocessing.core.filter;

import imageprocessing.core.dataobj.Coordinates;
import imageprocessing.framework.pmp.filter.DataTransformationFilter2;
import imageprocessing.framework.pmp.interfaces.Readable;
import imageprocessing.framework.pmp.interfaces.Writeable;

public class FilterFirstDisk extends DataTransformationFilter2<Coordinates, Coordinates> {

    private int removeFirstN;

    public FilterFirstDisk(int removeFirstN, Readable<Coordinates> input, Writeable<Coordinates> output) {
        super(input, output);
        this.removeFirstN = removeFirstN;
    }

    public FilterFirstDisk(int removeFirstN, Readable<Coordinates> input) {
        super(input);
        this.removeFirstN = removeFirstN;
    }

    public FilterFirstDisk(int removeFirstN, Writeable<Coordinates> output) {
        super(output);
        this.removeFirstN = removeFirstN;
    }

    @Override
    protected Coordinates process(Coordinates entity) {
        entity.setCoordinates(entity.getCoordinates().subList(removeFirstN, entity.getCoordinates().size()));
        return entity;
    }
}
