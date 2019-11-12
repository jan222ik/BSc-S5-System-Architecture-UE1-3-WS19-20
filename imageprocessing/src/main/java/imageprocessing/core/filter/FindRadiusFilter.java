package imageprocessing.core.filter;

import imageprocessing.core.dataobj.Coordinates;
import imageprocessing.core.dataobj.ImgDTO;
import imageprocessing.core.dataobj.Report;
import imageprocessing.core.util.GUI;
import imageprocessing.framework.pmp.filter.DataTransformationFilter1;
import imageprocessing.framework.pmp.interfaces.Readable;
import imageprocessing.framework.pmp.interfaces.Writeable;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FindRadiusFilter extends DataTransformationFilter1<List<Report>> {

    public FindRadiusFilter(Readable<List<Report>> input, Writeable<List<Report>> output) throws InvalidParameterException {
        super(input, output);
    }

    public FindRadiusFilter(Readable<List<Report>> input) throws InvalidParameterException {
        super(input);
    }

    public FindRadiusFilter(Writeable<List<Report>> output) throws InvalidParameterException {
        super(output);
    }

    private int threshold = 100;

    @Override
    protected void process(List<Report> entity) {
        if (entity != null) {
            for (Report report : entity) {
                Mat submatOfDisk = report.getSubmatOfDisk();
                Mat cannyOutput = new Mat();
                Imgproc.Canny(submatOfDisk, cannyOutput, threshold, threshold * 2);

                List<MatOfPoint> contours = new ArrayList<>();
                Mat hierarchy = new Mat();
                Imgproc.findContours(cannyOutput, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

                MatOfPoint2f[] contoursPoly  = new MatOfPoint2f[contours.size()];
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
            }
        }
    }
}
