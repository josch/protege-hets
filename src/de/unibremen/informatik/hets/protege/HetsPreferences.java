package de.unibremen.informatik.hets.protege;

import org.protege.editor.core.prefs.Preferences;
import org.protege.editor.core.prefs.PreferencesManager;

public class HetsPreferences {
    private static HetsPreferences instance;

    private static final String KEY = "de.unibremen.informatik.hets";

    private static final String DEFAULT_HETS_MAC_PATH = "/usr/local/Hets/bin/hets";
    private static final String DEFAULT_HETS_WINDOWS_PATH = "C:\\Program Files\\Hets\\bin\\hets";
    private static final String DEFAULT_HETS_LINUX_PATH = "/usr/bin/hets";

    private static final String DEFAULT_DOT_MAC_PATH = "/usr/local/graphviz-2.14/bin/dot";
    private static final String DEFAULT_DOT_WINDOWS_PATH = "C:\\Program Files\\GraphViz\\bin\\Dot";
    private static final String DEFAULT_DOT_LINUX_PATH = "/usr/bin/dot";

    private static final String HETSPATH = "HETSPATH";
    private static final String DOTPATH = "DOTPATH";
    private static final String CGIURL = "CGIURL";
    private static final String RESTFUL = "RESTFUL";

    public static synchronized HetsPreferences getInstance() {
        if(instance == null) {
            instance = new HetsPreferences();
        }
        return instance;
    }

    private Preferences getPrefs() {
        return PreferencesManager.getInstance().getApplicationPreferences(KEY);
    }

    private static String getDefaultHetsPath() {
        String platform = System.getProperty("os.name");

        if(platform.indexOf("OS X") != -1) {
            return DEFAULT_HETS_MAC_PATH;
        }
        else if(platform.indexOf("Windows") != -1) {
            return DEFAULT_HETS_WINDOWS_PATH;
        }
        else {
            return DEFAULT_HETS_LINUX_PATH;
        }
    }

    private static String getDefaultDotPath() {
        String platform = System.getProperty("os.name");

        if(platform.indexOf("OS X") != -1) {
            return DEFAULT_DOT_MAC_PATH;
        }
        else if(platform.indexOf("Windows") != -1) {
            return DEFAULT_DOT_WINDOWS_PATH;
        }
        else {
            return DEFAULT_DOT_LINUX_PATH;
        }
    }

    public String getHetsPath() {
        return getPrefs().getString(HETSPATH, getDefaultHetsPath());
    }

    public void setHetsPath(String path) {
        getPrefs().putString(HETSPATH, path);
    }

    public String getDotPath() {
        return getPrefs().getString(DOTPATH, getDefaultDotPath());
    }

    public void setDotPath(String path) {
        getPrefs().putString(DOTPATH, path);
    }

    public String getCGIUrl() {
        return getPrefs().getString(CGIURL, "http://www.informatik.uni-bremen.de/cgi-bin/cgiwrap/maeder/hets.cgi");
    }

    public void setCGIUrl(String path) {
        getPrefs().putString(CGIURL, path);
    }

    public String getRestFulUrl() {
        return getPrefs().getString(RESTFUL, "http://pollux.informatik.uni-bremen.de:8000");
    }

    public void setRestFulUrl(String path) {
        getPrefs().putString(RESTFUL, path);
    }
}
