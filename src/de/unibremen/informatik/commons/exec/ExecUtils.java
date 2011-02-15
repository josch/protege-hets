package de.unibremen.informatik.commons.exec;

public class ExecUtils {
    static {
    }

    public ExecUtils() {
        super();
    }

    public static void run(String cmd) {
        try {
            Process process = Runtime.getRuntime().exec(cmd);

            try {
                process.waitFor();
            } catch (java.lang.InterruptedException e) {
                e.printStackTrace();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
