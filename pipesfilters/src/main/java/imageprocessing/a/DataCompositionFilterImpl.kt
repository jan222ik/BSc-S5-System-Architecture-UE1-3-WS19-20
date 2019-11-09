package imageprocessing.a

import imageprocessing.dataobjects.Line
import imageprocessing.framework.pmp.filter.DataCompositionFilter
import imageprocessing.framework.pmp.interfaces.Writeable
import java.util.*

class DataCompositionFilterImpl(output: Writeable<LinkedList<Line>>) : DataCompositionFilter<Line, LinkedList<Line>>(output) {
    override fun fillEntity(nextVal: Line?, entity: LinkedList<Line>?): Boolean {
        if (nextVal != null) {
            entity?.add(nextVal)
            return false
        } else {
            println("entity = ${entity?.first}")
            entity?.addLast(entity.removeAt(0))
            entity?.sortWith(nullsLast(compareBy({ it.content }, {it.number})))
            return true
        }
    }

    override fun getNewEntityObject(): LinkedList<Line> {
        return LinkedList()
    }


}
