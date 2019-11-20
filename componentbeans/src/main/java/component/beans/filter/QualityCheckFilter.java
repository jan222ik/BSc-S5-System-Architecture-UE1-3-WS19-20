package component.beans.filter;

import component.beans.dataobj.Coordinate;
import component.beans.dataobj.Coordinates;
import component.beans.dataobj.Report;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.util.LinkedList;
import java.util.List;

public class QualityCheckFilter {

    private Coordinate[] expectedCoordinates;
    private double accuracy;


    private List<Report> process(Coordinates coordinates) {
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

            int deltaX = current.x - expected.x;
            int deltaY = current.y - expected.y;

            inRange = (Math.abs(deltaX) < accuracy && Math.abs(deltaY) < accuracy);

            int x = Math.max(0, current.x - size / 2);
            int y = Math.max(0, current.y - size / 2 - 30);
            Rect roi = new Rect(x, y, Math.min(size, imgWidth - x), Math.min(size, imgHeight - y));

            System.out.println(i + " current = " + current + " -> roi = " + roi);
            Mat submatOfDisk = coordinates.getImgDTO().getMat().submat(roi);

            reports.add(new Report(expected, current, inRange, deltaX, deltaY, submatOfDisk));
        }

        return reports;
    }
}
