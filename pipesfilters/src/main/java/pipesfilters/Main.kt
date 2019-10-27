package pipesfilters

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import pipesfilters.framework.pmp.interfaces.Writeable
import pipesfilters.framework.pmp.pipes.SimplePipe
import java.io.File

class Main {
    private val logger: Logger = LogManager.getLogger(Main::class.java)

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val main: Main = Main()
            main.logger.debug("Main Start")
            val simplePipe4 = SimplePipe<RawLine>(Writeable { main.logger.info(it) })
            val wordCycle = WordCirclerFilter(simplePipe4)
            val simplePipe3 = SimplePipe<Word>(wordCycle)
            val lines2WordsFilter = Lines2Words(simplePipe3)
            val simplePipe2 = SimplePipe<RawLine>(lines2WordsFilter)
            val sanitizerFilter = SanitizerFilter(simplePipe2)
            val simplePipe = SimplePipe<RawLine>(sanitizerFilter as Writeable<RawLine>)
            val sourceImpl = SourceImpl(simplePipe, File("Z:\\Users\\jan22\\CodeProjects\\S5---System-Architecture\\pipesfilters\\src\\main\\resources\\aliceInWonderland.txt"))
            sourceImpl.run()

            val freqWords = MostFrequentWordsTrie()
                    .also { it.addFromFile(File("pipesfilters/src/main/resources/frequentEnglishWords.txt"),  400) }

        }
    }


}

