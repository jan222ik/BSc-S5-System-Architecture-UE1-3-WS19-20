package imageprocessing.core.filter;

import imageprocessing.core.dataobj.Report;
import imageprocessing.framework.pmp.filter.DataTransformationFilter2;
import imageprocessing.framework.pmp.img.Coordinate;
import imageprocessing.framework.pmp.interfaces.Readable;
import imageprocessing.framework.pmp.interfaces.Writeable;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

public class QualityCheckFilter extends DataTransformationFilter2<List<Coordinate>, List<Report>> {

    private Coordinate[] expectedCoordinates;
    private double accuracy;

    public QualityCheckFilter(Coordinate[] expectedCoordinates, double accuracy, Readable<List<Coordinate>> input, Writeable<List<Report>> output) throws InvalidParameterException {
        super(input, output);
        this.expectedCoordinates = expectedCoordinates;
        this.accuracy = accuracy;
    }

    public QualityCheckFilter(Coordinate[] expectedCoordinates, double accuracy, Readable<List<Coordinate>> input) throws InvalidParameterException {
        super(input);
        this.expectedCoordinates = expectedCoordinates;
        this.accuracy = accuracy;
    }

    public QualityCheckFilter(Coordinate[] expectedCoordinates, double accuracy, Writeable<List<Report>> output) throws InvalidParameterException {
        super(output);
        this.expectedCoordinates = expectedCoordinates;
        this.accuracy = accuracy;
    }


    @Override
    protected List<Report> process(List<Coordinate> coordinates) {
        LinkedList<Report> reports = new LinkedList<>();
        boolean inRange;
        Coordinate current;
        Coordinate expected;
        for (int i = 0; i < coordinates.size(); i++) {
            current = coordinates.get(i);
            expected = expectedCoordinates[i];

            int deltaX = current._x - expected._x;
            int deltaY = current._y - expected._y;

            inRange = (Math.abs(deltaX) < accuracy && Math.abs(deltaY) < accuracy);

            reports.add(new Report(expected, current, inRange, deltaX, deltaY));
        }

        return reports;
    }
}
