package pipesfilters.a

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.MarkerManager
import pipesfilters.dataobjects.Line
import pipesfilters.framework.pmp.interfaces.Writeable
import pipesfilters.framework.pmp.pipes.SimplePipe

class LinePipe(output: Writeable<Line>, name: String) : SimplePipe<Line>(output) {
    private val logger: Logger = LogManager.getLogger(LinePipe::class.java)
    private val nameMarker = MarkerManager.Log4jMarker(name)

    override fun write(input: Line?) {
        logger.trace(nameMarker, "Line: {}", input)
        super.write(input)
    }
}
