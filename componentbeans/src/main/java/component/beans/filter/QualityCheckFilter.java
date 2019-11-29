package component.beans.filter;

import component.beans.dataobj.Coordinate;
import component.beans.dataobj.Coordinates;
import component.beans.dataobj.Report;
import component.beans.util.BeanMethods;
import component.beans.util.CacheHelper;
import component.beans.util.ExptCoordReader;
import component.beans.util.SetterHelper;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class QualityCheckFilter implements BeanMethods {

    private CacheHelper<Coordinate[]> cacheHelperExpected = new CacheHelper<>();
    private CacheHelper<Coordinates> cacheHelperCoords = new CacheHelper<>();
    private PropertyChangeSupport mPcs = new PropertyChangeSupport(this);
    private String expectedCoordinatesFile = "Z:\\Users\\jan22\\CodeProjects\\S5---System-Architecture\\componentbeans\\src\\main\\resources\\expectedCentroids.txt";
    private double accuracy = 3.0;

    public QualityCheckFilter() {
        OpenCV.loadLocally();
    }

    private void readFile() {
        File file = new File(expectedCoordinatesFile);
        if (file.exists()) {
            List<Coordinate> parse = ExptCoordReader.parse(file);
            cacheHelperExpected.setCache(parse.toArray(new Coordinate[0]), it -> it);
            update();
        }
    }

    private List<Report> process() {
        Coordinate[] expectedCoordinates = cacheHelperExpected.getCache();
        Coordinates coordinates = cacheHelperCoords.getCache();
        LinkedList<Report> reports = new LinkedList<>();
        if (expectedCoordinates != null) {
            final int size = 40;
            final int imgWidth = coordinates.getImgDTO().getImage().getWidth();
            final int imgHeight = coordinates.getImgDTO().getImage().getHeight();
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
        }
        return reports;
    }

    @Override
    public void update() {
        List<Report> process = process();
        System.out.println(Arrays.toString(process.toArray(new Report[0])));
        mPcs.firePropertyChange("qaNew", null, process);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        mPcs.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }

    public String getExpectedCoordinatesFile() {
        return expectedCoordinatesFile;
    }

    public void setExpectedCoordinatesFile(String expectedCoordinatesFile) {
        SetterHelper.notNull(expectedCoordinatesFile, () -> {
            this.expectedCoordinatesFile = expectedCoordinatesFile;
            readFile();
        });
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        SetterHelper.notNeg(accuracy, () -> {
            this.accuracy = accuracy;
            update();
        });

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SetterHelper.ifClass(evt.getNewValue(), Coordinates.class, () -> {
            cacheHelperCoords.setCache((Coordinates) evt.getNewValue(), Coordinates::cloneCoords);
            update();
        });
    }
}
