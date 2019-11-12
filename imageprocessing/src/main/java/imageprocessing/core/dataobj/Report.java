package imageprocessing.core.dataobj;

import imageprocessing.framework.pmp.img.Coordinate;
import org.opencv.core.Mat;

public class Report {
    private Coordinate expected;
    private Coordinate actual;
    private boolean inRange;
    private double deviationX;
    private double deviationY;
    private Mat submatOfDisk;

    public Report(Coordinate expected, Coordinate actual, boolean inRange, double deviationX, double deviationY, Mat submatOfDisk) {
        this.expected = expected;
        this.actual = actual;
        this.inRange = inRange;
        this.deviationX = deviationX;
        this.deviationY = deviationY;
        this.submatOfDisk = submatOfDisk;
    }

    public Coordinate getExpected() {
        return expected;
    }

    public Coordinate getActual() {
        return actual;
    }

    public boolean isInRange() {
        return inRange;
    }

    public double getDeviationX() {
        return deviationX;
    }

    public double getDeviationY() {
        return deviationY;
    }

    @Override
    public String toString() {
        return "Report{" +
                "expected=" + expected +
                ", actual=" + actual +
                ", inRange=" + inRange +
                ", deviationX=" + deviationX +
                ", deviationY=" + deviationY +
                '}';
    }

    public Mat getSubmatOfDisk() {
        return submatOfDisk;
    }
}
