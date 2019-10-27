package pipesfilters

import pipesfilters.dataobjects.Line
import pipesfilters.framework.pmp.filter.DataTransformationFilter1
import pipesfilters.framework.pmp.interfaces.Writeable

class SanitizerFilter(output: Writeable<Line>) : DataTransformationFilter1<Line>(output) {
    override fun process(line: Line?) {
        val c : String? = line?.content;
        if (c != null && c.isNotEmpty()) {
            var san: String = "";
            var char: String;
            var prev: Char = c[0];
            for (i: Int in c.indices) {
                char = c[i].toString()
                san += when(char) {
                    "\n", "\t", "!","\"","#","$","%","&","(",")","*","+",",","-",".","/",":",";","<","=",">","?","@","[","\\","]","^","_","`","{","|","}","~" -> " "
                    "'" -> if (prev.toString().matches("""[a-zA-Z]""".toRegex())) char else ""
                    else -> char
                }.also { prev = i.toChar() }
            }
            line.content = san
        }
    }
}
