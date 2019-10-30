package pipesfilters.b;

import pipesfilters.framework.pmp.interfaces.IOable;
import pipesfilters.framework.pmp.interfaces.Writeable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

public class SimpleMultiPipe<T> implements IOable<T, T> {

    private Writeable<T>[] m_Outputs;


    @SafeVarargs
    public SimpleMultiPipe(Writeable<T>... outputs) {
        if (outputs == null) {
            throw new InvalidParameterException("output filters can't be null!");
        }
        m_Outputs = outputs;
    }

    public T read() throws StreamCorruptedException {
        throw new NotImplementedException();
    }

    public void write(T input) throws StreamCorruptedException {
        for (Writeable<T> m_output : m_Outputs) {
            m_output.write(input);
        }
    }


}
