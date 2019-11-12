package imageprocessing.core;

import imageprocessing.core.dataobj.Coordinates;
import imageprocessing.core.dataobj.ImgDTO;
import imageprocessing.core.dataobj.Report;
import imageprocessing.core.filter.Convert2JAIFilter;
import imageprocessing.core.filter.ErodeFilter;
import imageprocessing.core.filter.FilterFirstDisk;
import imageprocessing.core.filter.FindRadiusFilter;
import imageprocessing.core.filter.ImgSource;
import imageprocessing.core.filter.MedianFilter;
import imageprocessing.core.filter.QualityCheckFilter;
import imageprocessing.core.filter.ROIFilter;
import imageprocessing.core.filter.SinkImpl;
import imageprocessing.core.filter.ThresholdFilter;
import imageprocessing.core.util.GUI;
import imageprocessing.core.util.ImgPipe;
import imageprocessing.framework.pmp.img.Coordinate;
import imageprocessing.framework.pmp.interfaces.Readable;
import imageprocessing.framework.pmp.interfaces.Writeable;
import imageprocessing.framework.pmp.pipes.SimplePipe;
import nu.pattern.OpenCV;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class MainPipes {
    public static void main(String[] args) {
        OpenCV.loadLocally();
        boolean doPush = true;
        File disksImgOut = new File("imageprocessing/target/disks.png");
        File diskReports = new File("imageprocessing/target/report.txt");
        System.out.println(disksImgOut.getAbsolutePath());
        Coordinate[] expCoordinates = {
                new Coordinate(73, 77),
                new Coordinate(110, 80),
                new Coordinate(202, 80),
                new Coordinate(265, 79),
                new Coordinate(330, 81),
                new Coordinate(396, 81)};
        double accuracy = 3D;
        if (doPush) {
            ImgSource sourcePicture = new ImgSource("imageprocessing/src/main/resources/loetstellen.jpg",
                    new ImgPipe(
                            displayImgWritable("SourcePicture"),
                            (Writeable<ImgDTO>) new ROIFilter(
                                    (Writeable<ImgDTO>) new ImgPipe(
                                            displayImgWritable("Cropped Image to ROI"),
                                            (Writeable<ImgDTO>) new ThresholdFilter(
                                                    (Writeable<ImgDTO>) new ImgPipe(
                                                            displayImgWritable("Threshold"),
                                                            (Writeable<ImgDTO>) new MedianFilter(
                                                                    19,
                                                                    (Writeable<ImgDTO>) new ImgPipe(
                                                                            displayImgWritable("Median"),
                                                                            (Writeable<ImgDTO>) new ErodeFilter(
                                                                                    0, 2,
                                                                                    (Writeable<ImgDTO>) new ImgPipe(
                                                                                            write2FileAndDisplay(disksImgOut, "Erode"),
                                                                                            new Convert2JAIFilter(
                                                                                                    new SimplePipe<Coordinates>(
                                                                                                            (Writeable<Coordinates>) new FilterFirstDisk(
                                                                                                                    1,
                                                                                                                    (Writeable<Coordinates>) new SimplePipe<>(
                                                                                                                            (Writeable<Coordinates>) new QualityCheckFilter(
                                                                                                                                    expCoordinates, accuracy,
                                                                                                                                    new SimplePipe<List<Report>>(
                                                                                                                                            (Writeable<List<Report>>) new FindRadiusFilter(
                                                                                                                                                   (Writeable<List<Report>>) new SimplePipe<>(
                                                                                                                                                           new SinkImpl(diskReports)
                                                                                                                                                   )
                                                                                                                                           )
                                                                                                                                    )
                                                                                                                            )
                                                                                                                    )
                                                                                                            )
                                                                                                    )
                                                                                            )
                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            );
            sourcePicture.run();
        } else {
            SinkImpl sink = new SinkImpl(
                    diskReports,
                    new SimplePipe<List<Report>>(
                            new QualityCheckFilter(
                                    expCoordinates, accuracy,
                                    new SimplePipe<>(
                                            (Readable<Coordinates>) new FilterFirstDisk(
                                                    1,
                                                    (Readable<Coordinates>) new SimplePipe<>(
                                                            (Readable<Coordinates>) new Convert2JAIFilter(
                                                                    new ImgPipe(
                                                                            write2FileAndDisplay(disksImgOut, "Erode"),
                                                                            (Readable<ImgDTO>) new ErodeFilter(
                                                                                    0, 2,
                                                                                    (Readable<ImgDTO>) new ImgPipe(
                                                                                            displayImgWritable("Median"),
                                                                                            (Readable<ImgDTO>) new MedianFilter(
                                                                                                    19,
                                                                                                    (Readable<ImgDTO>) new ImgPipe(
                                                                                                            displayImgWritable("Threshold"),
                                                                                                            (Readable<ImgDTO>) new ThresholdFilter(
                                                                                                                    (Readable<ImgDTO>) new ImgPipe(
                                                                                                                            displayImgWritable("Cropped Image to ROI"),
                                                                                                                            (Readable<ImgDTO>) new ROIFilter(
                                                                                                                                    (Readable<ImgDTO>) new ImgPipe(
                                                                                                                                            displayImgWritable("SourcePicture"),
                                                                                                                                            new ImgSource("imageprocessing/src/main/resources/loetstellen.jpg")
                                                                                                                                    )
                                                                                                                            )
                                                                                                                    )
                                                                                                            )
                                                                                                    )
                                                                                            )
                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            );
            sink.run();
        }
        //value -> Optional.ofNullable(value).ifPresent(w -> w.forEach(report -> System.out.println("report = " + report)))
    }

    //If org pic background needed: https://stackoverflow.com/questions/40527769/removing-black-background-and-make-transparent-from-grabcut-output-in-python-ope
    //Draw Rect: Imgproc.rectangle(matrix, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 255, 255, 255), 3);
    public static Writeable<ImgDTO> displayImgWritable(String title) {
        return imageDTO -> Optional.ofNullable(imageDTO).ifPresent(img -> GUI.displayImage(img.getImage(), title));
    }

    public static Writeable<ImgDTO> write2FileAndDisplay(File file, String title) {
        return imageDTO -> Optional.ofNullable(imageDTO).ifPresent(img -> {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                ImageIO.write(img.getImage(), "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GUI.displayImage(img.getImage(), title);
        });
    }
}
