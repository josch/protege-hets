package de.unibremen.informatik.hets.model;

public class Spec {
    protected String annotation;
    protected String content;

    public Spec(String cont, String anno) {
        content = cont;
        annotation = anno;
    }

    public Spec(String cont) {
        content = cont;
        annotation = null;
    }

    public Spec() {
        content = null;
        annotation = null;
    }

    public String toString() {
        return content;
    }

    public String getAnnotation() {
        return annotation;
    }
}
