package de.unibremen.informatik.hets.model;

import org.semanticweb.owlapi.model.OWLOntologyManager;

public class Actuals extends Spec {
    public Actuals(String cont, String anno) {
        super(cont, anno);
    }

    public String getName() {
        return content;
    }

    public String toString(OWLOntologyManager ontologymanager) {
        return content + "\n";
    }

    public String toString() {
        return content + "\n";
    }
}
