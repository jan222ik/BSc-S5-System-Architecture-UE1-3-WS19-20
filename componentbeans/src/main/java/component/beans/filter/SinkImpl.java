package component.beans.filter;

import component.beans.dataobj.Report;
import component.beans.util.CacheHelper;
import component.beans.util.SetterHelper;
import nu.pattern.OpenCV;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SinkImpl implements PropertyChangeListener {

    private CacheHelper<List<Report>> cacheHelper = new CacheHelper<>();
    private PropertyChangeSupport mPcs = new PropertyChangeSupport(this);
    private String outputPath;
    private double accuracy = 3.0;

    public SinkImpl() {
        OpenCV.loadLocally();
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
        write();
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        SetterHelper.notNeg(accuracy, () -> {
            this.accuracy = accuracy;
            write();
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SetterHelper.ifClass(evt.getNewValue(), List.class, () -> {
            cacheHelper.setCache((List<Report>) evt.getNewValue(), list -> list.stream().map(Report::cloneReport).collect(Collectors.toList()));
            write();
        });
    }
}
