package imageprocessing.a

import imageprocessing.dataobjects.Line
import imageprocessing.dataobjects.Word
import imageprocessing.framework.pmp.filter.DataTransformationFilter2
import imageprocessing.framework.pmp.interfaces.Writeable
import java.util.*

class Lines2WordsFilter(output: Writeable<ArrayList<Word>>) : DataTransformationFilter2<Line, ArrayList<Word>>(output) {
    override fun process(line: Line?): ArrayList<Word>? {
        val content = line?.content
        @Suppress("LiftReturnOrAssignment")
        if (content != null) {
            val aList = ArrayList<Word>()
            if (content.isNotEmpty()) {
                val stringTokenizer = StringTokenizer(content)
                while (stringTokenizer.hasMoreElements()) {
                    aList.add(Word(line.number, stringTokenizer.nextToken()))
                }
            }
            return aList
        } else {
            return null
        }
    }
}
