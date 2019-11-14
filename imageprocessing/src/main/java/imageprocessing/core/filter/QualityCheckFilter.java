package imageprocessing.core.filter;

import imageprocessing.core.dataobj.Coordinates;
import imageprocessing.core.dataobj.Report;
import imageprocessing.framework.pmp.filter.DataTransformationFilter2;
import imageprocessing.framework.pmp.img.Coordinate;
import imageprocessing.framework.pmp.interfaces.Readable;
import imageprocessing.framework.pmp.interfaces.Writeable;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

public class QualityCheckFilter extends DataTransformationFilter2<Coordinates, List<Report>> {

    private Coordinate[] expectedCoordinates;
    private double accuracy;

    public QualityCheckFilter(Coordinate[] expectedCoordinates, double accuracy, Readable<Coordinates> input, Writeable<List<Report>> output) throws InvalidParameterException {
        super(input, output);
        this.expectedCoordinates = expectedCoordinates;
        this.accuracy = accuracy;
    }

    public QualityCheckFilter(Coordinate[] expectedCoordinates, double accuracy, Readable<Coordinates> input) throws InvalidParameterException {
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
    protected List<Report> process(Coordinates coordinates) {
        final int size = 40;
        final int imgWidth = coordinates.getImgDTO().getImage().getWidth();
        final int imgHeight = coordinates.getImgDTO().getImage().getHeight();
        LinkedList<Report> reports = new LinkedList<>();
        boolean inRange;
        Coordinate current;
        Coordinate expected;
        for (int i = 0; i < Math.min(coordinates.getCoordinates().size(), expectedCoordinates.length); i++) {
            current = coordinates.getCoordinates().get(i);
            expected = expectedCoordinates[i];

            int deltaX = current._x - expected._x;
            int deltaY = current._y - expected._y;

            inRange = (Math.abs(deltaX) < accuracy && Math.abs(deltaY) < accuracy);

            int x = Math.max(0, current._x - size / 2);
            int y = Math.max(0, current._y - size / 2 - 30);
            Rect roi = new Rect(x, y, Math.min(size, imgWidth - x), Math.min(size, imgHeight - y));

            System.out.println(i + " current = " + current + " -> roi = " + roi);
            Mat submatOfDisk = coordinates.getImgDTO().getMat().submat(roi);

            reports.add(new Report(expected, current, inRange, deltaX, deltaY, submatOfDisk));
        }

        return reports;
    }
}
