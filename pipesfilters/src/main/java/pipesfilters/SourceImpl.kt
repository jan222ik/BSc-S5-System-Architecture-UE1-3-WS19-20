package pipesfilters

import pipesfilters.framework.pmp.filter.Source
import pipesfilters.framework.pmp.interfaces.Writeable
import java.io.File

class SourceImpl(output: Writeable<RawLine>, private var file: File): Source<RawLine>(output) {

    private lateinit var lines : List<String>;
    private var indexNr: Int = 0;

    init {
        if (file.isFile) {
            lines = file.readLines();
        }
    }

    override fun read(): RawLine? {
        return if (indexNr < lines.size) {
            RawLine(indexNr, lines[indexNr]).also { indexNr++ }
        } else {
            null
        }
    }
}
