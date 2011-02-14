package de.unibremen.informatik.hets.protege;

import java.io.Writer;
import java.io.IOException;

import org.protege.editor.owl.ui.view.ontology.AbstractOntologyRenderingViewComponent;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.io.OWLRendererException;

import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxRenderer;

public class HetCASLRenderingViewComponent extends AbstractOntologyRenderingViewComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3845613418666267771L;

	@Override
	protected void renderOntology(OWLOntology ontology, Writer writer) {
        try {
    		ManchesterOWLSyntaxRenderer ren = new ManchesterOWLSyntaxRenderer(getOWLModelManager().getOWLOntologyManager());
    		ren.render(ontology, writer);
    		writer.flush();
        } catch (OWLRendererException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
