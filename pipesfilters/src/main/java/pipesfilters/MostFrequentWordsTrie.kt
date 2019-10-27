package pipesfilters

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import pipesfilters.util.Trie
import java.io.File

class MostFrequentWordsTrie : Trie() {
    private val logger: Logger = LogManager.getLogger(MostFrequentWordsTrie::class.java)
    @Suppress("kotlin:S125")
    public fun addFromFile(file: File, lowerFrequencyBound: Int) {
        if (file.isFile) {
            var wordCount = 0
            val regexPattern = """^\s*?(?<word>\w+?)\s*?(?<frequency>\d+?)\s*?\([\w, \t]*+\)$""".toRegex().toPattern()
            val lines = file.readLines();
            for (i: Int in 1 until lines.size) {
                val matcher = regexPattern.matcher(lines[i])
                if (matcher.find()) {
                    val frequency = matcher.group("frequency")
                    if (frequency.toInt() > lowerFrequencyBound) {
                        val word = matcher.group("word")
                        this.addWord(word);
                        wordCount++
                        logger.trace("Added line $i with content {$word}")
                    }
                } else {
                    logger.trace("Line $i does not match! - lines[i] = ${lines[i]}")
                }
            }
            logger.info("Added $wordCount words.")
        } else {
            logger.error("File not found: File{AbsolutePath = ${file.absolutePath}}")
        }
    }
}
