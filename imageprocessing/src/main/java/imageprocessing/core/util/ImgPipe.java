package imageprocessing.core.util;

import imageprocessing.core.dataobj.ImgDTO;
import imageprocessing.framework.pmp.interfaces.Readable;
import imageprocessing.framework.pmp.interfaces.Writeable;
import imageprocessing.framework.pmp.pipes.SimplePipe;

import java.io.StreamCorruptedException;

public class ImgPipe extends SimplePipe<ImgDTO> {

    private Writeable<ImgDTO> renderGUI = null;

    public ImgPipe(Readable<ImgDTO> input) {
        super(input);
    }

    public ImgPipe(Writeable<ImgDTO> output) {
        super(output);
    }

    public ImgPipe(Readable<ImgDTO> input, Writeable<ImgDTO> output) {
        super(input, output);
    }

    public ImgPipe(Writeable<ImgDTO> renderGUI, Readable<ImgDTO> input) {
        super(input);
        this.renderGUI = renderGUI;
    }

    public ImgPipe(Writeable<ImgDTO> renderGUI, Writeable<ImgDTO> output) {
        super(output);
        this.renderGUI = renderGUI;
    }

    public ImgPipe(Writeable<ImgDTO> renderGUI, Readable<ImgDTO> input, Writeable<ImgDTO> output) {
        super(input, output);
        this.renderGUI = renderGUI;
    }

    @Override
    public ImgDTO read() throws StreamCorruptedException {
        ImgDTO read = super.read();
        showInGUI(read);
        return read;
    }

    @Override
    public void write(ImgDTO input) throws StreamCorruptedException {
        showInGUI(input);
        super.write(input);
    }

    private void showInGUI(ImgDTO imgDTO) throws StreamCorruptedException {
        if (renderGUI != null) {
            renderGUI.write(imgDTO);
        }
    }

}
