package imageprocessing.b

import imageprocessing.framework.pmp.filter.DataCompositionFilter
import imageprocessing.framework.pmp.interfaces.Writeable

class Chars2Words(output: Writeable<StringBuilder>) : DataCompositionFilter<Char, StringBuilder>(output) {
    override fun fillEntity(nextVal: Char?, entity: StringBuilder?): Boolean {
        return if (nextVal != null) {
            if (nextVal != ' ' && nextVal != '\n' && nextVal != '\r' && nextVal != '\t') {
                entity?.append(nextVal)
                false
            } else true
        } else true
    }

    override fun getNewEntityObject(): StringBuilder {
        return StringBuilder()
    }
}
