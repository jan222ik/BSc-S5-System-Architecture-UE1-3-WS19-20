package pipesfilters

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import pipesfilters.dataobjects.Line
import pipesfilters.dataobjects.Word
import pipesfilters.framework.pmp.filter.DataCompositionFilter
import pipesfilters.framework.pmp.interfaces.Writeable
import pipesfilters.framework.pmp.pipes.SimplePipe
import pipesfilters.util.ArgumentParser
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

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
            val freqWordsLowerBound = args.getArgValue("-freqLower", "100").toInt()
            val defaultOut = File(Main::class.java.protectionDomain.codeSource.location.toURI().path).parentFile.path + "/output.txt"
            val outputFile = File(args.getArgValue("-output", defaultOut)).also { f ->
                if (f.exists() && !args.containsKeyword("-overwrite")) {
                    logger.error("File already exists (use: -overwrite to enable overwrites)")
                    return@main
                }
                f.parentFile.mkdirs()
                f.createNewFile()
            }
            var count: Int
            val freqWords = MostFrequentWordsTrie()
                    .also { count = it.addFromFile(freqWordsFile, freqWordsLowerBound) }
            freqWords.isWord("the")
            val tabs = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"
            logger.info("Input \n$tabs File{${inputFile.absolutePath}}")
            logger.info("Most Frequent Words \n" +
                    "$tabs Frequency > {$freqWordsLowerBound} \n" +
                    "$tabs File {${freqWordsFile.absolutePath}}\n" +
                    "$tabs Words: {$count}")
            logger.info("Output \n$tabs Overwrite:{${args.containsKeyword("-overwrite")}}" +
                    "\n$tabs File {${outputFile.absolutePath}}")
            logger.debug("Init Pipe and Filter Objects")
            val sourceImpl = SourceImpl(
                    LinePipe(
                            SanitizerFilter(
                                    LinePipe(
                                            Lines2WordsFilter(
                                                    SimplePipe<ArrayList<Word>>(
                                                            WordCirclerFilter(
                                                                    LinePipe(
                                                                            DataCompositionFilterImpl(
                                                                                    SimplePipe<LinkedList<Line>>(
                                                                                            SinkImpl(outputFile)
                                                                                    )
                                                                            ), "cyl > sort"
                                                                    ), freqWords
                                                            )
                                                    )
                                            ),
                                            "San > Words"
                                    )
                            ) as Writeable<Line>,
                            "Raw > San"
                    ),
                    inputFile
            )
            logger.debug("Execution of Pipe & Filters")
            sourceImpl.run()
            logger.debug("End of Execution")
            logger.debug("Output at: Path{${outputFile.absolutePath}}")
        }
    }
}

