package pipesfilters

import pipesfilters.dataobjects.Line
import pipesfilters.dataobjects.Word
import pipesfilters.framework.pmp.filter.DataCompositionFilter
import pipesfilters.framework.pmp.interfaces.Writeable

class WordCirclerFilter(output: Writeable<Line>) : DataCompositionFilter<Word, Line>(output) {
    override fun getNewEntityObject(): Line {
        return Line(0, null)
    }

    override fun fillEntity(nextVal: Word?, entity: Line?): Boolean {
        return (nextVal == null)
    }

}
