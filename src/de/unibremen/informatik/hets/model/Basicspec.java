package de.unibremen.informatik.hets.model;

import de.unibremen.informatik.owl.OWLUtils;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.IRI;

import org.protege.editor.owl.model.OWLModelManager;

public class Basicspec extends Spec {
    String ontname;

    public Basicspec(String cont, String anno) {
        super(cont, anno);
    }

    public String getOntologyName() {
        return ontname;
    }

    public void displayOntology(OWLModelManager owlmodelmanager, String iri) {
        OWLUtils.displayOntology(owlmodelmanager, content, iri);
        ontname = iri;
    }

    public void displayOntology(OWLModelManager owlmodelmanager, String iri, String parent_iri) {
        OWLUtils.displayOntology(owlmodelmanager, content, iri, parent_iri);
        ontname = iri;
    }

    public String toString(OWLOntologyManager ontologymanager) {
        return OWLUtils.dumpOntologyToString(ontologymanager, ontname);
    }

    public String toString() {
        return content;
    }
}
