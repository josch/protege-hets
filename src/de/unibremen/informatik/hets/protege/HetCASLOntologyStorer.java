package de.unibremen.informatik.hets.protege;

import org.semanticweb.owlapi.model.OWLOntologyFormat;

import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxOntologyStorer;

public class HetCASLOntologyStorer extends ManchesterOWLSyntaxOntologyStorer {

	public boolean canStoreOntology(OWLOntologyFormat arg0) {
		return arg0.equals(new HetCASLOntologyFormat());
	}
}
