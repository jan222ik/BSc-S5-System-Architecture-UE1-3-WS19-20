package component.beans.dataobj;

import org.opencv.core.Mat;

import java.io.Serializable;
import java.util.Arrays;

public class Report implements Serializable {
    private Coordinate expected;
    private Coordinate actual;
    private boolean inRange;
    private double deviationX;
    private double deviationY;
    private Mat submatOfDisk;
    private float[][] radii;

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
                ", radii=" + Arrays.deepToString(radii) +
                '}';
    }

    public Mat getSubmatOfDisk() {
        return submatOfDisk;
    }

    public void setRadii(float[][] radii) {
        this.radii = radii;
    }

    public Report cloneReport() {
        Report report = new Report(expected.cloneCoord(), actual.cloneCoord(), inRange, deviationX, deviationY, submatOfDisk.clone());
        report.setRadii(radii.clone());
        return report;
    }
}
