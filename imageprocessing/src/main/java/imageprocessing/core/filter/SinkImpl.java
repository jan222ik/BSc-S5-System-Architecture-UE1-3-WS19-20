package imageprocessing.core.filter;

import imageprocessing.core.dataobj.Report;
import imageprocessing.framework.pmp.filter.Sink;
import imageprocessing.framework.pmp.interfaces.Readable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;

public class SinkImpl extends Sink<List<Report>> {

    private File output;
    private double accuracy;

    public SinkImpl(File output, double accuracy) {
        super();
        this.output = output;
        this.accuracy = accuracy;
    }

    public SinkImpl(File output, double accuracy, Readable<List<Report>> input) throws InvalidParameterException {
        super(input);
        this.output = output;
        this.accuracy = accuracy;
    }

    @Override
    public void write(List<Report> reports) throws StreamCorruptedException {
        if (reports == null) return;
        if (!output.exists()) {
            try {
                output.getParentFile().mkdirs();
                output.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try(PrintWriter pw = new PrintWriter(new FileWriter(output))) {
            pw.println(LocalDateTime.now() + " - Accuracy:" + accuracy);
            for (Report report : reports) {
                pw.write(report.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
