package de.unibremen.informatik.hets.model;

import org.semanticweb.owlapi.model.OWLOntologyManager;

public class Group extends Spec {
    public Group(String cont, String anno) {
        super(cont, anno);
    }

    public String toString(OWLOntologyManager ontologymanager) {
        return content;
    }

    public String toString() {
        return content;
    }
}
