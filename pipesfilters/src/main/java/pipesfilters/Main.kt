package pipesfilters

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import pipesfilters.dataobjects.Line
import pipesfilters.dataobjects.Word
import pipesfilters.framework.pmp.interfaces.Writeable
import pipesfilters.framework.pmp.pipes.SimplePipe
import pipesfilters.util.ArgumentParser
import java.io.File
import java.security.CodeSource

class Main {
    companion object {
        private val logger: Logger = LogManager.getLogger(Main::class.java)
        @JvmStatic
        fun main(a: Array<String>) {
            logger.debug("Main Start")
            val args = ArgumentParser().also { it.parseArgs(a.asList(), '=') }
            val resPath = "pipesfilters/src/main/resources/"
            val inputFile = File(args.getArgValue("-input", resPath + "aliceInWonderland.txt"))
            val freqWordsFile = File(args.getArgValue("-freqWords", resPath + "frequentEnglishWords.txt"))
            val freqWordsLowerBound = args.getArgValue("-freqLower", "400").toInt()
            val defaultOut = File(Main::class.java.protectionDomain.codeSource.location.toURI().path).parentFile.path + "/output.txt";
            val outputFile = File(args.getArgValue("-output", defaultOut)).also { f ->
                if (f.exists() && !args.containsKeyword("-overwrite")) {
                    logger.error("File already exists (use: -overwrite to enable overwrites)")
                }
                f.parentFile.mkdirs()
                f.createNewFile()
            }
            logger.debug("Init Pipe and Filter Objects")
            val simplePipe4 = SimplePipe<Line>(Writeable { logger.info(it) })
            val wordCycle = WordCirclerFilter(simplePipe4)
            val simplePipe3 = SimplePipe<Word>(wordCycle)
            val lines2WordsFilter = Lines2Words(simplePipe3)
            val simplePipe2 = SimplePipe<Line>(lines2WordsFilter)
            val sanitizerFilter = SanitizerFilter(simplePipe2)
            val simplePipe = SimplePipe<Line>(sanitizerFilter as Writeable<Line>)
            val sourceImpl = SourceImpl(simplePipe, inputFile)
            logger.debug("Execution of Pipe & Filters")
            sourceImpl.run()
            logger.debug("End of Execution")
            logger.debug("Output at: Path{${outputFile.absolutePath}}")

            val freqWords = MostFrequentWordsTrie()
                    .also { it.addFromFile(freqWordsFile, freqWordsLowerBound) }
        }
    }
}

