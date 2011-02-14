package de.unibremen.informatik.hets.model;

public class Actuals extends Spec {
    public Actuals(String cont, String anno) {
        super(cont, anno);
    }

    public String getName() {
        return content;
    }

    public String toString() {
        return content + "\n";
    }
}
