package pipesfilters

import pipesfilters.framework.pmp.filter.Source
import pipesfilters.framework.pmp.interfaces.Writeable

class SourceImpl(output: Writeable<String>?): Source<String>(output) {

    private var i : Int = 0;
    override fun read(): String? {
        return if (i < 3) "Demo TEXT $i".also { i++ } else null
    }
}
