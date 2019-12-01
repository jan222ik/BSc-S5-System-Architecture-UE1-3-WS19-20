package component.beans.filter;

import component.beans.dataobj.Report;
import component.beans.util.CacheHelper;
import component.beans.util.SetterHelper;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SinkImpl implements PropertyChangeListener {

    private CacheHelper<List<Report>> cacheHelper = new CacheHelper<>();
    private String outputPath = "Z:\\Users\\jan22\\CodeProjects\\S5---System-Architecture\\componentbeans\\target\\report.txt";
    private double accuracy = 3.0;

    public SinkImpl() {
        System.out.println("Constructor: SinkImpl in Class: SinkImpl");
        SetterHelper.initOpenCV();
    }

    private void write() {
        System.out.println("Method: write in Class: SinkImpl");
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
        System.out.println("Method: propertyChange in Class: SinkImpl");
        System.out.println(evt.getNewValue().getClass());
        SetterHelper.ifNullableClass(evt.getNewValue(), LinkedList.class, () -> { //No Explicit Nullhandle
        }, () -> {
            cacheHelper.setCache((List<Report>) evt.getNewValue(), list -> list.stream().map(Report::cloneReport).collect(Collectors.toList()));
            write();
        });
    }
}
