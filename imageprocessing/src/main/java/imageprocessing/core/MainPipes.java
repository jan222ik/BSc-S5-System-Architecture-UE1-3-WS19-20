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
import imageprocessing.core.util.ArgumentParser;
import imageprocessing.core.util.ExptCoordReader;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MainPipes {
    public static void main(String... args) {
        ArgumentParser argumentParser = new ArgumentParser();
        argumentParser.parseArgs(Arrays.asList(args), '=');
        if (argumentParser.containsKeyword("-h")) {
            System.out.println(
                    "Name              | Definition                                         | Default\n" +
                            "-src              | Source image                                       | Path: imageprocessing/src/main/resources/loetstellen.jpg\n" +
                            "-acc              | Accuracy                                           | 3\n" +
                            "-pull             | Switch to Pullpipe                                 | false\n" +
                            "-exptCoords       | File with expected Coords                          | Path: imageprocessing/src/main/resources/expectedCentroids.txt\n" +
                            "-disksImgOut      | Output for created DiskImg after Erosion           | Path: imageprocessing/target/disks.png\n" +
                            "-diskReportOut    | Output for created Report                          | Path: imageprocessing/target/report.txt\n" +
                            "-shapeTypeErode   | Shape type for erode filter Range[0 - 3]           | 0\n" +
                            "-kernelSizeErode  | Kernel size for erode filter                       | 2\n" +
                            "-kernelSizeMedian | Kernel size for median filter                      | 19\n" +
                            "-removeFirstN     | Removes the first n found disks in the given image | 1\n"
            );
        } else {
            String accString = argumentParser.getArgValue("-acc", "3.0");
            double accuracy = Double.parseDouble(accString);
            boolean doPush = !argumentParser.containsKeyword("-pull");
            String  imageSource = argumentParser.getArgValue("-src", "imageprocessing/src/main/resources/loetstellen.jpg");
            File disksImgOut = new File(argumentParser.getArgValue("-disksImgOut", "imageprocessing/target/disks.png"));
            File diskReports = new File(argumentParser.getArgValue("-diskReportOut", "imageprocessing/target/report.txt"));
            File expectedCoors = new File(argumentParser.getArgValue("-exptCoords", "imageprocessing/src/main/resources/expectedCentroids.txt"));
            OpenCV.loadLocally();
            Coordinate[] expCoordinates = ExptCoordReader.parse(expectedCoors).toArray(new Coordinate[0]);
            int shapeTypeErode = Integer.parseInt(argumentParser.getArgValue("-shapeTypeErode", "0"));
            int kernelSizeErode = Integer.parseInt(argumentParser.getArgValue("-kernelSizeErode", "2"));
            int kernelSizeMedian = Integer.parseInt(argumentParser.getArgValue("-kernelSizeMedian", "19"));
            int removeFirstN = Integer.parseInt(argumentParser.getArgValue("-removeFirstN", "1"));
            if (doPush) {
                ImgSource sourcePicture = new ImgSource(imageSource,
                        new ImgPipe(
                                displayImgWritable("SourcePicture"),
                                (Writeable<ImgDTO>) new ROIFilter(
                                        (Writeable<ImgDTO>) new ImgPipe(
                                                displayImgWritable("Cropped Image to ROI"),
                                                (Writeable<ImgDTO>) new ThresholdFilter(
                                                        (Writeable<ImgDTO>) new ImgPipe(
                                                                displayImgWritable("Threshold"),
                                                                (Writeable<ImgDTO>) new MedianFilter(
                                                                        kernelSizeMedian,
                                                                        (Writeable<ImgDTO>) new ImgPipe(
                                                                                displayImgWritable("Median"),
                                                                                (Writeable<ImgDTO>) new ErodeFilter(
                                                                                        shapeTypeErode, kernelSizeErode,
                                                                                        (Writeable<ImgDTO>) new ImgPipe(
                                                                                                write2FileAndDisplay(disksImgOut, "Erode"),
                                                                                                new Convert2JAIFilter(
                                                                                                        new SimplePipe<Coordinates>(
                                                                                                                (Writeable<Coordinates>) new FilterFirstDisk(
                                                                                                                        removeFirstN,
                                                                                                                        (Writeable<Coordinates>) new SimplePipe<>(
                                                                                                                                (Writeable<Coordinates>) new QualityCheckFilter(
                                                                                                                                        expCoordinates, accuracy,
                                                                                                                                        new SimplePipe<List<Report>>(
                                                                                                                                                (Writeable<List<Report>>) new FindRadiusFilter(
                                                                                                                                                        (Writeable<List<Report>>) new SimplePipe<>(
                                                                                                                                                                new SinkImpl(diskReports, accuracy)
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
                        diskReports, accuracy,
                        new SimplePipe<>(
                                (Readable<List<Report>>) new FindRadiusFilter(
                                        (Readable<List<Report>>) new SimplePipe<List<Report>>(
                                                new QualityCheckFilter(
                                                        expCoordinates, accuracy,
                                                        new SimplePipe<>(
                                                                (Readable<Coordinates>) new FilterFirstDisk(
                                                                        removeFirstN,
                                                                        (Readable<Coordinates>) new SimplePipe<>(
                                                                                (Readable<Coordinates>) new Convert2JAIFilter(
                                                                                        new ImgPipe(
                                                                                                write2FileAndDisplay(disksImgOut, "Erode"),
                                                                                                (Readable<ImgDTO>) new ErodeFilter(
                                                                                                        shapeTypeErode, kernelSizeErode,
                                                                                                        (Readable<ImgDTO>) new ImgPipe(
                                                                                                                displayImgWritable("Median"),
                                                                                                                (Readable<ImgDTO>) new MedianFilter(
                                                                                                                        kernelSizeMedian,
                                                                                                                        (Readable<ImgDTO>) new ImgPipe(
                                                                                                                                displayImgWritable("Threshold"),
                                                                                                                                (Readable<ImgDTO>) new ThresholdFilter(
                                                                                                                                        (Readable<ImgDTO>) new ImgPipe(
                                                                                                                                                displayImgWritable("Cropped Image to ROI"),
                                                                                                                                                (Readable<ImgDTO>) new ROIFilter(
                                                                                                                                                        (Readable<ImgDTO>) new ImgPipe(
                                                                                                                                                                displayImgWritable("SourcePicture"),
                                                                                                                                                                new ImgSource(imageSource)
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
                sink.run();
            }
            //value -> Optional.ofNullable(value).ifPresent(w -> w.forEach(report -> System.out.println("report = " + report)))
        }

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
