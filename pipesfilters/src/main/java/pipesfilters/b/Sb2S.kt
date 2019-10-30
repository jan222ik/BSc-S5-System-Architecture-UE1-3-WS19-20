package pipesfilters.b

import pipesfilters.framework.pmp.filter.DataTransformationFilter2
import pipesfilters.framework.pmp.interfaces.Writeable

class Sb2S(output: Writeable<String>): DataTransformationFilter2<StringBuilder, String>(output) {
    override fun process(entity: StringBuilder?): String? {
        println(entity)
        return entity?.toString()
    }
}
