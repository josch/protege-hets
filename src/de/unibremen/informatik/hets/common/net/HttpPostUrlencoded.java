package de.unibremen.informatik.hets.common.net;

import java.net.URLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Iterator;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;

public class HttpPostUrlencoded {
    URLConnection connection;
    OutputStream os = null;

    protected void write(String s) throws IOException {
        os.write(s.getBytes());
    }

    public HttpPostUrlencoded(URL url) throws IOException {
        connection = url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
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

                write(name + "=");
                if (object instanceof File) {
                    write(URLEncoder.encode(InputStreamToString(new FileInputStream((File)object)), "UTF-8"));
                } else {
                    write(object.toString());
                }
                write("&");
            }
        }
        os.close();
        return connection.getInputStream();
    }

    public static InputStream post(URL url, Map parameters) throws IOException {
        return new HttpPostUrlencoded(url).post(parameters);
    }
}
