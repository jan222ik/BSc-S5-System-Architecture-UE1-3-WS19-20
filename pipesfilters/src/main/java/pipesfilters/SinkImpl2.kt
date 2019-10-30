package pipesfilters

import pipesfilters.dataobjects.Line
import pipesfilters.framework.pmp.filter.Sink
import java.io.File
import java.io.PrintWriter
import java.util.*

class SinkImpl2(outputFile: File) : Sink<Line>() {

    private val writer: PrintWriter

    init {
        outputFile.printWriter().write("")
        writer = outputFile.printWriter()
    }

    override fun write(value: Line?) {
        if (value != null) {
                writer.println("${value.content}")
                writer.flush()

        }
    }
}
