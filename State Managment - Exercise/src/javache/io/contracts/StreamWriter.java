package javache.io.contracts;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public interface StreamWriter {
    void writeOutputStream(byte[] outputStream, DataOutputStream responseStream) throws IOException;
}
