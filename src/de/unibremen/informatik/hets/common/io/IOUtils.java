package de.unibremen.informatik.hets.common.io

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
