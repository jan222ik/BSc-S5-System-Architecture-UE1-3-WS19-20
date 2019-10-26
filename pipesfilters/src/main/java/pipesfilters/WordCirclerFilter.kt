package pipesfilters

import pipesfilters.framework.pmp.filter.DataCompositionFilter
import pipesfilters.framework.pmp.interfaces.Writeable

class WordCirclerFilter(output: Writeable<RawLine>) : DataCompositionFilter<Word, RawLine>(output) {
    override fun getNewEntityObject(): RawLine {
        return RawLine(0, null)
    }

    override fun fillEntity(nextVal: Word?, entity: RawLine?): Boolean {
        return (nextVal == null)
    }

}
