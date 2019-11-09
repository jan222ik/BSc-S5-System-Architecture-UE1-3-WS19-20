package imageprocessing.b

import imageprocessing.framework.pmp.filter.DataTransformationFilter2
import imageprocessing.framework.pmp.interfaces.Writeable

class Sb2S(output: Writeable<String>): DataTransformationFilter2<StringBuilder, String>(output) {
    override fun process(entity: StringBuilder?): String? {
        println(entity)
        return entity?.toString()
    }
}
