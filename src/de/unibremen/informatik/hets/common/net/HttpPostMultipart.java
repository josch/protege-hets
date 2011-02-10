package de.unibremen.informatik.hets.common.net;

import java.net.URLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Iterator;
import java.util.Random;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;

public class HttpPostMultipart {
    URLConnection connection;
    OutputStream os = null;
    String boundary = "---------------------------" + randomString() + randomString() + randomString();
    private static Random random = new Random();

    protected void write(String s) throws IOException {
        os.write(s.getBytes());
    }

    protected static String randomString() {
        return Long.toString(random.nextLong(), 36);
    }

    public HttpPostMultipart(URL url) throws IOException {
        connection = url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type",
                "multipart/form-data; boundary=" + boundary);
        os = connection.getOutputStream();
    }

    public InputStream post(Map parameters) throws IOException {
        String name;
        Object object;
        if (parameters != null) {
            for (Iterator i = parameters.entrySet().iterator(); i.hasNext();) {
                Map.Entry entry = (Map.Entry)i.next();
                name = entry.getKey().toString();
                object = entry.getValue();

                write("--" + boundary + "\r\n");
                write("Content-Disposition: form-data; name=\"" + name + "\"");
                if (object instanceof File) {
                    write("; filename=\"" + ((File)object).getPath() + "\"\r\n");
                    String type = connection.guessContentTypeFromName(((File)object).getPath());
                    if (type == null)
                        type = "application/octet-stream";
                    write("Content-Type: " + type + "\r\n");
                    write("\r\n");
                    pipe(new FileInputStream((File)object), os);
                } else {
                    write("\r\n");
                    write("\r\n");
                    write(object.toString());
                }
                write("\r\n");
            }
        }
        write("--" + boundary + "--\r\n");
        os.close();
        return connection.getInputStream();
    }

    public static InputStream post(URL url, Map parameters) throws IOException {
        return new HttpPostMultipart(url).post(parameters);
    }
}
