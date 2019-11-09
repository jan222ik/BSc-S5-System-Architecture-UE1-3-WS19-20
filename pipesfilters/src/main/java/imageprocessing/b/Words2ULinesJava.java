package imageprocessing.b;

import org.jetbrains.annotations.NotNull;
import imageprocessing.dataobjects.Line;
import imageprocessing.framework.pmp.filter.DataCompositionFilter;
import imageprocessing.framework.pmp.interfaces.Writeable;

public class Words2ULinesJava extends DataCompositionFilter<String, Line> {
    private String overLength = null;
    private int lineNumber = 0;
    private int lineLength;

    public Words2ULinesJava(Writeable<Line> output, int lineLength) {
        super(output);
        this.lineLength = lineLength;
    }

    @Override
    protected boolean fillEntity(String nextVal, @NotNull Line entity) {
        if (overLength != null) {
            entity.setContent(entity.getContent() + overLength);
            overLength = null;
        }
        if (nextVal != null) {
            if (entity.getContent().length() + nextVal.length() >= lineLength) {
                overLength = nextVal;
                return true;
            } else {
                if (!entity.getContent().isEmpty() || (entity.getContent().length() > 0 && entity.getContent().charAt(entity.getContent().length() - 1) != ' ')) {
                    entity.setContent(entity.getContent() + " ");
                }
                entity.setContent(entity.getContent() + nextVal);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    protected Line getNewEntityObject() {
        lineNumber++;
        System.out.println("lineNumber = " + lineNumber);
        return new Line(lineNumber, "");
    }
}
