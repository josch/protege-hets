package de.unibremen.informatik.hets.protege;

import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.protege.editor.core.ui.util.UIUtil;
import org.protege.editor.owl.ui.action.ProtegeOWLAction;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.io.StreamDocumentTarget;

import org.semanticweb.owlapi.model.OWLOntology;

import de.unibremen.informatik.hets.protege.ImportHetsAction;
import de.unibremen.informatik.hets.model.HetFile;
import org.semanticweb.owlapi.model.IRI;

public class ExportHetsAction extends ProtegeOWLAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8507263538705345008L;

	public ExportHetsAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialise() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*
        Set<String> exts = new HashSet<String>();
        exts.add("het");
        exts.add("owl");
		UIUtil.saveFile(this.getOWLWorkspace(), "title", "description", exts, "foobar.het");
		System.out.println(this.getOWLModelManager().getActiveOntology());
		this.getOWLModelManager().getOWLOntologyManager().addOntologyStorer(new HetCASLOntologyStorer());
		this.getOWLWorkspace();
		try {
			this.getOWLModelManager().getOWLOntologyManager().saveOntology(this.getOWLModelManager().getActiveOntology(), new HetCASLOntologyFormat(), new StreamDocumentTarget(System.out));
		} catch (OWLOntologyStorageException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		

        /*
        Set<String> exts = new HashSet<String>();
        exts.add("het");
        exts.add("owl");
		File f = UIUtil.saveFile(this.getOWLWorkspace(), "title", "description", exts, "foobar.het");
		if (f.exists()) {
			// TODO: ask if overwrite
			f.delete();
		}
		FileOutputStream outputFile = null;
		try {
			outputFile = new FileOutputStream(f, true);
			int i;
			for (i = 0; i < ImportHetsAction.parts.size()-1; i++) {
				outputFile.write(ImportHetsAction.parts.get(i).getBytes());
				outputFile.write(dumpOntologyToString(ImportHetsAction.owl_parts.get(i)).getBytes());
			}
			outputFile.write(ImportHetsAction.parts.get(i).getBytes());
			outputFile.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        */

        HetFile hetfile = ImportedHetFile.getHetFile();
        
        Set<String> exts = new HashSet<String>();
        exts.add("het");
        exts.add("owl");
        File f = UIUtil.saveFile(this.getOWLWorkspace(), "title", "description", exts, "foobar.het");

        if (f.exists()) {
            // TODO: ask if overwrite
            f.delete();
        }

        try {
            FileOutputStream outputFile = new FileOutputStream(f, true);
            outputFile.write(hetfile.toString(this.getOWLModelManager().getOWLOntologyManager()).getBytes());
            outputFile.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
	}
}
