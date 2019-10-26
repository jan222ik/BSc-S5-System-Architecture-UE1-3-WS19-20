package pipesfilters

import pipesfilters.framework.pmp.filter.DataTransformationFilter3
import pipesfilters.framework.pmp.interfaces.Writeable
import java.util.*

class Lines2Words(output: Writeable<Word>) : DataTransformationFilter3<RawLine, Word>(output) {
    override fun process(line: RawLine?): ArrayList<Word>? {
        val content = line?.content;
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
