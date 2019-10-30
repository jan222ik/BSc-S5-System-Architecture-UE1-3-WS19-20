package pipesfilters.a

import pipesfilters.dataobjects.Line
import pipesfilters.framework.pmp.filter.DataCompositionFilter
import pipesfilters.framework.pmp.interfaces.Writeable
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
