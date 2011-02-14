package de.unibremen.informatik.hets.protege;

import de.unibremen.informatik.hets.model.HetFile;

public class ImportedHetFile {
    static HetFile hetfile;

    static {
    }

    public ImportedHetFile() {
        super();
    }

    public static void setHetFile(HetFile hf) {
        hetfile = hf;
    }

    public static HetFile getHetFile() {
        return hetfile;
    }
}
