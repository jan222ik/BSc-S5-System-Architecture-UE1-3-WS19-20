package imageprocessing.b

import imageprocessing.dataobjects.Line
import imageprocessing.framework.pmp.filter.DataCompositionFilter
import imageprocessing.framework.pmp.interfaces.Writeable

class Words2ULines(output: Writeable<Line>, private val lineLength: Int) : DataCompositionFilter<String, Line>(output) {
    private var overlenght: String? = null
    private var lineNumber = 0


    override fun fillEntity(nextVal: String?, entity: Line?): Boolean {
        /*
        if (overlenght != null) {
            entity?.content = entity?.content.plus(overlenght)
            overlenght = null
        }
        if (nextVal != null) {
            if (entity?.content == null) {
                entity?.content = ""
            }
            if (entity?.content!!.length + nextVal.length >= lineLength) {
                overlenght = nextVal;
                return true
            } else {
                if (entity?.content!!.isNotEmpty()) {
                    entity?.content += " ";
                }
                entity?.content += nextVal;
                return false
            }
        } else {
            return true
        }

         */
        return false
    }



    override fun getNewEntityObject(): Line {
        lineNumber++
        return Line(lineNumber, null)
    }
}
