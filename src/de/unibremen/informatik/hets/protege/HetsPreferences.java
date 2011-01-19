package de.unibremen.informatik.hets.protege;

import org.protege.editor.core.prefs.Preferences;
import org.protege.editor.core.prefs.PreferencesManager;

public class HetsPreferences {
    private static HetsPreferences instance;

    private static final String KEY = "de.unibremen.informatik.hets";

    private static final String DEFAULT_MAC_PATH = "/usr/local/Hets/bin/hets";
    private static final String DEFAULT_WINDOWS_PATH = "C:\\Program Files\\Hets\\bin\\hets";
    private static final String DEFAULT_LINUX_PATH = "/usr/bin/hets";

    private static final String HETSPATH = "HETSPATH";

    public static synchronized HetsPreferences getInstance() {
        if(instance == null) {
            instance = new HetsPreferences();
        }
        return instance;
    }

    private Preferences getPrefs() {
        return PreferencesManager.getInstance().getApplicationPreferences(KEY);
    }

    private static String getDefaultPath() {
        String platform = System.getProperty("os.name");

        if(platform.indexOf("OS X") != -1) {
            return DEFAULT_MAC_PATH;
        }
        else if(platform.indexOf("Windows") != -1) {
            return DEFAULT_WINDOWS_PATH;
        }
        else {
            return DEFAULT_LINUX_PATH;
        }
    }

    public String getHetsPath() {
        return getPrefs().getString(HETSPATH, getDefaultPath());
    }

    public void setHetsPath(String path) {
        getPrefs().putString(HETSPATH, path);
    }
}
