package imageprocessing.b;

import imageprocessing.framework.pmp.filter.Source;
import imageprocessing.framework.pmp.interfaces.Writeable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

public class ReadFileAsChars extends Source<Character> {

    private BufferedReader br;

    public ReadFileAsChars(Writeable<Character> output, File input) throws InvalidParameterException, FileNotFoundException {
        super(output);
        FileReader fr = new FileReader(input);
        br = new BufferedReader(fr);
    }

    @Override
    public Character read() throws StreamCorruptedException {
        int c = 0;
        try {
            if ((c = br.read()) != -1) {
                return (char) c;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
