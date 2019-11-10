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

    public SinkImpl(File output) {
        super();
        this.output = output;
    }

    public SinkImpl(File output, Readable<List<Report>> input) throws InvalidParameterException {
        super(input);
        this.output = output;
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
            pw.println(LocalDateTime.now());
            for (Report report : reports) {
                pw.write(report.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
