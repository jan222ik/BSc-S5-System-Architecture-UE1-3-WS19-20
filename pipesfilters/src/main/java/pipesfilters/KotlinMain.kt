package pipesfilters

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import pipesfilters.framework.pmp.interfaces.Writeable

class KotlinMain {
    private val logger: Logger = LogManager.getLogger(KotlinMain::class.java)
    fun main(args: Array<String>) {
        logger.debug("Main Start")
        SourceImpl(Writeable {string -> logger.info(string) }).run()
    }
}
