package de.unibremen.informatik.hets.common.lang;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;
import java.lang.StringIndexOutOfBoundsException;

public class StringUtils {
    static {
    }

    public StringUtils() {
        super();
    }

    /*
     * return from line fromline, column fromcolumn until line toline, column
     * tocolumn
     */
    public static String getSlice(String str, int fromline, int fromcolumn, int toline, int tocolumn) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(str));
        StringBuilder builder = new StringBuilder();

        // seek to fromline
        for (int i = 0; i < fromline-1; i++) {
            reader.readLine();
        }

        String cur = reader.readLine();
        if (cur == null) {
            throw new StringIndexOutOfBoundsException();
        }

        if (fromline == toline) {
            builder.append(cur.substring(fromcolumn-1, tocolumn-1));
        } else {
            builder.append(cur.substring(fromcolumn-1));
            builder.append("\n");

            // append lines until toline
            for (int i = fromline-1; i < toline-2; i++) {
                cur = reader.readLine();
                if (cur == null) {
                    throw new StringIndexOutOfBoundsException();
                }
                builder.append(cur);
                builder.append("\n");
            }

            cur = reader.readLine();
            if (cur == null) {
                throw new StringIndexOutOfBoundsException();
            }
            builder.append(cur.substring(0, tocolumn-1));
        }

        return builder.toString();
    }
}
