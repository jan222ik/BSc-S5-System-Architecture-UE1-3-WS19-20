package component.beans.filter;

import component.beans.dataobj.Report;
import component.beans.util.CacheHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

public class SinkImpl {

    private CacheHelper<List<Report>> cacheHelper = new CacheHelper<>();
    private String outputPath;
    private double accuracy = 3.0;

    public SinkImpl() {
    }

    private void write() {
        List<Report> reports = cacheHelper.getCache();
        if (reports != null) {
            File output = new File(outputPath);
            if (!output.exists()) {
                try {
                    output.getParentFile().mkdirs();
                    output.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try (PrintWriter pw = new PrintWriter(new FileWriter(output))) {
                pw.println(LocalDateTime.now() + " - Accuracy:" + accuracy);
                for (Report report : reports) {
                    pw.write(report.toString() + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
}
