package de.unibremen.informatik.commons.io;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.io.StringWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOUtils {
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    static {
    }

    public IOUtils() {
        super();
    }

    public static long copy(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
        int nread = 0;
        long count = 0;
        while((nread = in.read(buf)) >= 0) {
            out.write(buf, 0, nread);
            count += nread;
        }
        return count;
    }

    public static long copy(Reader in, Writer out) throws IOException {
        char[] buf = new char[DEFAULT_BUFFER_SIZE];
        int nread = 0;
        long count = 0;
        while((nread = in.read(buf)) >= 0) {
            out.write(buf, 0, nread);
            count += nread;
        }
        return count;
    }

    public static StringWriter getStringWriter(InputStream in) throws IOException {
        StringWriter sw = new StringWriter();
        copy(new InputStreamReader(in), sw);
        return sw;
    }

    public static String getString(InputStream in) throws IOException {
        return getStringWriter(in).toString();
    }

    public static StringBuffer getBuffer(InputStream in) throws IOException {
        return getStringWriter(in).getBuffer();
    }
}
