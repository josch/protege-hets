package de.unibremen.informatik.hets.protege;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import org.protege.editor.core.ui.util.UIUtil;

import javax.swing.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Set;

import org.protege.editor.owl.ui.action.ProtegeOWLAction;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

import de.unibremen.informatik.hets.grammar.HetCASLParser;
import de.unibremen.informatik.hets.grammar.ParseException;
import de.unibremen.informatik.hets.grammar.SimpleNode;

public class ImportHetsAction extends ProtegeOWLAction {
	
	public static ArrayList<String> parts;
	public static Hashtable<String, OWLOntology> ontologies;
	public static ArrayList<String> owl_parts;

	/**
	 * 
	 */
	private static final long serialVersionUID = -4056096587762591108L;

	@Override
	public void initialise() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() throws Exception {
		// TODO Auto-generated method stub
		
	}

	OWLOntology loadOntologyFromString(OWLOntologyManager manager, String str, String iri) {
		OWLOntology ont;
		try {
			str = "Ontology: <http://informatik.uni-bremen.de/hets/"+iri+".het>\n"
				+ "Prefix: : <http://informatik.uni-bremen.de/hets/"+iri+".het#>\n"
				+ str;
			ont = manager.loadOntologyFromOntologyDocument(new ByteArrayInputStream(str.getBytes()));
		} catch (OWLOntologyCreationException e1) {
			System.out.println(" -- begin");
			System.out.println(str);
			System.out.println("-- end");
			e1.printStackTrace();
			ont = null;
		}
		return ont;
	}
	
	void saveOntologyToStream(OWLOntologyManager manager, OWLOntology ont, OutputStream out, String defaultprefix) {
		try {
			manager.saveOntology(ont, new HetCASLOntologyFormat(defaultprefix), out);
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		}
	}
	
	OWLOntology createOntologyFromURL(OWLOntologyManager manager, String url) {
		OWLOntology ont;
		try {
			ont = manager.createOntology(IRI.create(url));
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
			ont = null;
		}
		return ont;
	}
	
	OWLOntology displayOntology(String ont_string, OWLOntology parent_ont, String iri, String parent_iri) {
    	OWLOntologyManager manager = this.getOWLModelManager().getOWLOntologyManager();

		manager.addOntologyStorer(new HetCASLOntologyStorer());
    	
		System.out.println(parent_ont + ", " + parent_iri + ", " + iri);
    	if (parent_ont != null && parent_iri != "" && parent_iri != iri) {
        	OWLOntologyManager tempmanager = OWLManager.createOWLOntologyManager();
    		tempmanager.addOntologyStorer(new HetCASLOntologyStorer());
        	
    		manager.addIRIMapper(new SimpleIRIMapper(IRI.create("http://informatik.uni-bremen.de/hets/"+iri+".het"), IRI.create("http://foobar")));
    		OWLOntology temp_ont;
    		ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
    		temp_ont = createOntologyFromURL(tempmanager, "http://informatik.uni-bremen.de/hets/"+iri+".het");

    		System.out.println(parent_iri + ", " + parent_ont);
    		tempmanager.addIRIMapper(new SimpleIRIMapper(IRI.create("http://informatik.uni-bremen.de/hets/"+parent_iri+".het"), manager.getOntologyDocumentIRI(parent_ont)));

    		tempmanager.applyChange(new AddImport(temp_ont, manager.getOWLDataFactory().getOWLImportsDeclaration(IRI.create("http://informatik.uni-bremen.de/hets/"+parent_iri+".het"))));

    		for (OWLClass cls : parent_ont.getClassesInSignature()) {
    			tempmanager.addAxiom(temp_ont, manager.getOWLDataFactory().getOWLDeclarationAxiom(cls));
    		}
    		
    		saveOntologyToStream(tempmanager, temp_ont, outputstream, "http://informatik.uni-bremen.de/hets/"+parent_iri+".het#");

    		tempmanager.removeOntology(temp_ont);

    		try {
    			outputstream.write(ont_string.getBytes());
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		
    		ont_string = outputstream.toString();
    	}
    	
    	if (parent_iri == "") {
    		parent_iri = iri;
    	}
    	
    	OWLOntology ontology1 = loadOntologyFromString(manager, ont_string, iri);
		manager.setOntologyFormat(ontology1, new HetCASLOntologyFormat("http://informatik.uni-bremen.de/hets/"+parent_iri+".het#"));
    	
		this.getOWLModelManager().setActiveOntology(ontology1);

		return ontology1;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		parts = new ArrayList<String>();
		ontologies = new Hashtable<String, OWLOntology>();
		owl_parts = new ArrayList<String>();
		
		Set<String> exts = new HashSet<String>();
        exts.add("het");
        exts.add("owl");
        FileInputStream file = null;
		try {
			file = new FileInputStream(UIUtil.openFile(new JFrame(), "HetCASL", "Please select a *.het file", exts));
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HetCASLParser parser = new HetCASLParser(file);
		SimpleNode parseTree = null;
		try {
			parseTree = parser.LIB_DEFN();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    int num_logics, num_spec_defs, num_specs;
	    boolean is_owl = false;
	    OWLOntology parent = null;
	    String parent_name = "";
	    String spec_name = "";
	    String value;
	    SimpleNode logicnode, specdefnode, specnode;

	    value = (String)((SimpleNode)parseTree).jjtGetValue(); // lib name
	    num_logics = parseTree.jjtGetNumChildren();

	    parts.add("");
	    parts.set(parts.size()-1, parts.get(parts.size()-1) + "library " + value + "\n\n");

	    for (int i = 0; i < num_logics; i++) {
	    	logicnode = (SimpleNode)parseTree.jjtGetChild(i);
	    	value = (String)logicnode.jjtGetValue(); // logic name
	    	num_spec_defs = logicnode.jjtGetNumChildren();

	    	parts.set(parts.size()-1, parts.get(parts.size()-1) + "logic " + value + "\n\n");

	    	System.out.println(value);
	    	if (value.contains("OWL")) {
	    		is_owl = true;
	    		System.out.println("is OWL");
	    	} else {
	    		is_owl = false;
	    	}

	    	for (int j = 0; j < num_spec_defs; j++) {
	    		specdefnode = (SimpleNode)logicnode.jjtGetChild(j);
	    		value = (String)specdefnode.jjtGetValue(); // spec name
	    		num_specs = specdefnode.jjtGetNumChildren();

	    		spec_name = value.trim();
	    		parts.set(parts.size()-1, parts.get(parts.size()-1) + "spec " + spec_name + " =");

	    		for (int k = 0; k < num_specs; k++) {
	    			specnode = (SimpleNode)specdefnode.jjtGetChild(k);
	    			value = (String)specnode.jjtGetValue(); // spec

	    			// TODO: enable printspecial
	    			//System.out.println(printspecial(specnode.jjtGetFirstToken()));
	    			
	    			if (is_owl) {
	    				// check if only name
	    				if (value.trim().contains(" ")) {
	    					//System.out.println("display ontology: "+spec_name+", "+parent_name);
	    					parent = displayOntology(value, parent, spec_name, parent_name);
		    				ontologies.put(spec_name+parent_name, parent);
		    				owl_parts.add(spec_name);
		    				parts.add("");
	    				} else {
	    					parent_name = value.trim();
	    					parts.set(parts.size()-1, parts.get(parts.size()-1) + "  " + parent_name + "\n");
	    					parent = ontologies.get(parent_name);
	    					
	    					//System.out.println(" +++ " + parent + " " + ((String)values.get("spec")).trim());
	    				}
	    			} else {
	    				parts.set(parts.size()-1, parts.get(parts.size()-1) + value);
	    			}

	    			if (specnode.toString() == "SPEC_THEN") {
	    				parts.set(parts.size()-1, parts.get(parts.size()-1) + "then");
	    			} else if (specnode.toString() == "SPEC_END") {
	    				parts.set(parts.size()-1, parts.get(parts.size()-1) + "end\n\n");
	    			}
	    		}
    			
    			parent = null;
    			parent_name = "";
	    	}
	    }
	}
}
