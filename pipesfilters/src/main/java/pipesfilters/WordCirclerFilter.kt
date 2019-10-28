package pipesfilters

import pipesfilters.dataobjects.Line
import pipesfilters.dataobjects.Word
import pipesfilters.framework.pmp.filter.DataCompositionFilter
import pipesfilters.framework.pmp.filter.DataTransformationFilter3
import pipesfilters.framework.pmp.interfaces.Writeable
import java.util.*

class WordCirclerFilter(output: Writeable<Line>, private val trie: MostFrequentWordsTrie) : DataTransformationFilter3<ArrayList<Word>, Line>(output) {
    override fun process(words: ArrayList<Word>?): ArrayList<Line>? {
        if (words != null) {
            val lineList = ArrayList<Line>()
            for (i in words.indices) {
                val cur = words[i]
                if (!trie.isWord(cur.word.toLowerCase())) {
                    lineList.add(Line(cur.lineNr, list2Line(words, i)))
                }
            }
            return lineList
        }
        return null
    }

    private fun list2Line(words: ArrayList<Word>, index: Int): String {
        val stringBuilder = StringBuilder()
        for (i in index until words.size) {
            stringBuilder.append(words[i].word).append(' ')
        }
        for (i in 0 until index) {
            stringBuilder.append(words[i].word).append(' ')
        }
        return stringBuilder.toString()
    }
}
