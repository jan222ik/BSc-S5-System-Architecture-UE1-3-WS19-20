package component.beans.filter;

import component.beans.dataobj.Report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.time.LocalDateTime;
import java.util.List;

public class SinkImpl {

    private String outputPath;
    private double accuracy;

    public SinkImpl() {
    }

    private void write(List<Report> reports) throws StreamCorruptedException {
        File output = new File(outputPath);
        if (reports == null) return;
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
