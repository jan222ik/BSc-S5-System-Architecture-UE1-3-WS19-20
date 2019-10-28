package pipesfilters

import pipesfilters.dataobjects.Line
import pipesfilters.framework.pmp.filter.DataCompositionFilter
import pipesfilters.framework.pmp.interfaces.Writeable
import java.util.*

class DataCompositionFilterImpl(output: Writeable<LinkedList<Line>>) : DataCompositionFilter<Line, LinkedList<Line>>(output) {
    override fun fillEntity(nextVal: Line?, entity: LinkedList<Line>?): Boolean {
        if (nextVal != null) {
            entity?.add(nextVal)
            entity?.sortBy { line: Line -> line.content }
            return false
        } else {
            return true
        }
    }

    override fun getNewEntityObject(): LinkedList<Line> {
        return LinkedList()
    }


}
