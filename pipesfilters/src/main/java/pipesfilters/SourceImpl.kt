package pipesfilters

import pipesfilters.dataobjects.Line
import pipesfilters.framework.pmp.filter.Source
import pipesfilters.framework.pmp.interfaces.Writeable
import java.io.File

class SourceImpl(output: Writeable<Line>, private var file: File): Source<Line>(output) {

    private lateinit var lines : List<String>;
    private var indexNr: Int = 0;

    init {
        if (file.isFile) {
            lines = file.readLines();
        }
    }

    override fun read(): Line? {
        return if (indexNr < lines.size) {
            Line(indexNr, lines[indexNr]).also { indexNr++ }
        } else {
            null
        }
    }
}
