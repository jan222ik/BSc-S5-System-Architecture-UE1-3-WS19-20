package pipesfilters.a

import pipesfilters.dataobjects.Line
import pipesfilters.framework.pmp.filter.DataTransformationFilter1
import pipesfilters.framework.pmp.interfaces.Writeable

class SanitizerFilter(output: Writeable<Line>) : DataTransformationFilter1<Line>(output) {
    override fun process(line: Line?) {
        val c: String? = line?.content
        if (c != null && c.isNotEmpty()) {
            var sanitizedStr = ""
            var char: String
            var prev: Char = c[0]
            for (i in c.indices) {
                char = c[i].toString()
                sanitizedStr += when (char) {
                    "\n", "\t", "!", "\"", "#", "$", "%", "&", "(", ")",
                    "*", "+", ",", ".", "/", ":", ";", "<", "=",
                    ">", "?", "@", "[", "\\", "]", "^", "_", "`", "{",
                    "|", "}", "~", "·"
                        -> " "
                    "'", "-" -> if (prev.toString().matches("""[a-zA-Z]""".toRegex())) char else ""
                    else -> char
                }
                prev = char.toCharArray()[0]
            }
            line.content = sanitizedStr
        }
    }
}
