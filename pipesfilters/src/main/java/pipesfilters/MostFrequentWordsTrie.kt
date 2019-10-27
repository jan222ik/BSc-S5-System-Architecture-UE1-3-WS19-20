package pipesfilters

import java.io.File

class MostFrequentWordsTrie : Trie() {
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
                        //println("Added line $i with content {$word}")
                    }
                } else {
                    println("Line $i does not match! - lines[i] = ${lines[i]}")
                }
            }
            println("Added $wordCount words.")
        } else {
            println("File not found: File{AbsolutePath = ${file.absolutePath}}")
        }
    }
}
