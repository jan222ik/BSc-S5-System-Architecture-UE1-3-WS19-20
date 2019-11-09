package imageprocessing.a

import imageprocessing.dataobjects.Line
import imageprocessing.framework.pmp.filter.Source
import imageprocessing.framework.pmp.interfaces.Writeable
import java.io.File

class SourceImpl(output: Writeable<Line>, file: File): Source<Line>(output) {

    private lateinit var lines : List<String>;
    private var indexNr: Int = 0;

    init {
        if (file.isFile) {
            lines = file.readLines();
        }
    }

    override fun read(): Line? {
        return if (indexNr < lines.size) {
            Line(indexNr + 1, lines[indexNr]).also { indexNr++ }
        } else {
            null
        }
    }
}
