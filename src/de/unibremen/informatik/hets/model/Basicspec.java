package de.unibremen.informatik.hets.model;

import de.unibremen.informatik.owl.OWLUtils;

import org.semanticweb.owlapi.model.OWLOntology;

import org.protege.editor.owl.model.OWLModelManager;

public class Basicspec extends Spec {
    OWLOntology ontology;

    public Basicspec(String cont, String anno) {
        super(cont, anno);
    }

    public OWLOntology getOntology() {
        return ontology;
    }

    public void displayOntology(OWLModelManager owlmodelmanager, String iri) {
        ontology = OWLUtils.displayOntology(owlmodelmanager, content, iri);
    }

    public void displayOntology(OWLModelManager owlmodelmanager, String iri, OWLOntology parent_ont, String parent_iri) {
        ontology = OWLUtils.displayOntology(owlmodelmanager, content, parent_ont, iri, parent_iri);
    }
}
