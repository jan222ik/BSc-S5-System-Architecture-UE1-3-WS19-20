package pipesfilters.b

import pipesfilters.dataobjects.Line
import pipesfilters.framework.pmp.filter.DataTransformationFilter1
import pipesfilters.framework.pmp.interfaces.Writeable

class LayoutFilter(output: Writeable<Line>, private val layout: Layout, private val lineLength: Int) : DataTransformationFilter1<Line>(output) {
    override fun process(entity: Line?) {
        if (entity != null) {
            when (layout) {
                Layout.LEFT -> return
                Layout.CENTER -> doLayoutCenter(entity)
                Layout.RIGHT -> doLayoutRight(entity)
            }
        }
    }

    fun doLayoutRight(entity: Line) {
        val content = entity.content
        if (content != null) run {
            val charsToInflate: Int = lineLength - content.length
            entity.content = " ".repeat(charsToInflate).plus(content)
        }
    }

    fun doLayoutCenter(entity: Line) {
        val content = entity.content
        if (content != null) run {
            val charsToInflate: Int = lineLength - content.length
            entity.content = " ".repeat(charsToInflate / 2).plus(content).plus(" ".repeat(charsToInflate / 2))
        }
    }
}

enum class Layout {
    LEFT, CENTER, RIGHT
}
