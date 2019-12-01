package component.beans.filter;

import component.beans.dataobj.ImgDTO;
import component.beans.dataobj.Report;
import component.beans.util.BeanMethods;
import component.beans.util.CacheHelper;
import component.beans.util.GUI;
import component.beans.util.SetterHelper;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FindRadiusFilter implements BeanMethods {

    private CacheHelper<List<Report>> cacheHelper = new CacheHelper<>();
    private int threshold = 100;
    private PropertyChangeSupport mPcs = new PropertyChangeSupport(this);

    public FindRadiusFilter() {
        System.out.println("Constructor: FindRadiusFilter in Class: FindRadiusFilter");
        SetterHelper.initOpenCV();
    }

    private List<Report> process() {
        List<Report> entity = cacheHelper.getCache();
        if (entity != null) {
            entity = entity.stream().map(Report::cloneReport).collect(Collectors.toList());
            for (Report report : entity) {
                Mat submatOfDisk = report.getSubmatOfDisk();
                Mat cannyOutput = new Mat();
                Imgproc.Canny(submatOfDisk, cannyOutput, threshold, threshold * 2);

                List<MatOfPoint> contours = new ArrayList<>();
                Mat hierarchy = new Mat();
                Imgproc.findContours(cannyOutput, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

                MatOfPoint2f[] contoursPoly = new MatOfPoint2f[contours.size()];
                //Rect[] boundRect = new Rect[contours.size()];
                Point[] centers = new Point[contours.size()];
                float[][] radius = new float[contours.size()][1];
                for (int i = 0; i < contours.size(); i++) {
                    contoursPoly[i] = new MatOfPoint2f();
                    Imgproc.approxPolyDP(new MatOfPoint2f(contours.get(i).toArray()), contoursPoly[i], 3, true);
                    //boundRect[i] = Imgproc.boundingRect(new MatOfPoint(contoursPoly[i].toArray()));
                    centers[i] = new Point();
                    Imgproc.minEnclosingCircle(contoursPoly[i], centers[i], radius[i]);
                }
                Mat drawing = Mat.zeros(cannyOutput.size(), CvType.CV_8UC3);
                List<MatOfPoint> contoursPolyList = new ArrayList<>(contoursPoly.length);
                for (MatOfPoint2f poly : contoursPoly) {
                    contoursPolyList.add(new MatOfPoint(poly.toArray()));
                }
                Random rng = new Random();
                for (int i = 0; i < contours.size(); i++) {
                    Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
                    Imgproc.drawContours(drawing, contoursPolyList, i, color);
                    //Imgproc.rectangle(drawing, boundRect[i].tl(), boundRect[i].br(), color, 2);
                    Imgproc.circle(drawing, centers[i], (int) radius[i][0], color, 2);
                }
                ImgDTO imgDTO = new ImgDTO();
                imgDTO.setMat(drawing);
                GUI.displayImage(imgDTO.getImage(), "Enclosing Circle");
                System.out.println("radius = " + Arrays.deepToString(radius));
                report.setRadii(radius);
            }
        }
        return entity;
    }

    @Override
    public void update() {
        System.out.println("Method: update in Class: FindRadiusFilter");
        List<Report> process = process();
        System.out.println(Arrays.toString(process.toArray(new Report[0])));
        mPcs.firePropertyChange("newRadii", null, new LinkedList<>(process));
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        mPcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        SetterHelper.notNeg(threshold, () -> {
            this.threshold = threshold;
            update();
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Method: propertyChange in Class: FindRadiusFilter");
        System.out.println(evt.getNewValue().getClass());
        SetterHelper.ifNullableClass(evt.getNewValue(), LinkedList.class,() -> {
            mPcs.firePropertyChange("newRadii", null, null);
        }, () -> {
            cacheHelper.setCache((List<Report>) evt.getNewValue(), list -> list.stream().map(Report::cloneReport).collect(Collectors.toList()));
            update();
        });
    }
}
