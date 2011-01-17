package de.unibremen.informatik.hets.protege;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;

public class HetCASLOntologyFormat extends ManchesterOWLSyntaxOntologyFormat {

	public HetCASLOntologyFormat() {
		setDefaultPrefix("http://informatik.uni-bremen.de/hets#");
	}
	
	public HetCASLOntologyFormat(String prefix) {
		setDefaultPrefix(prefix);
	}
	
    public String toString() {
        return "HetCASL";
    }
}