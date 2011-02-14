package de.unibremen.informatik.owl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.IOException;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

import org.protege.editor.owl.model.OWLModelManager;

import de.unibremen.informatik.hets.protege.HetCASLOntologyFormat;
import de.unibremen.informatik.hets.protege.HetCASLOntologyStorer;

public class OWLUtils {
    static {
    }

    public OWLUtils() {
        super();
    }

    public static OWLOntology loadOntologyFromString(OWLOntologyManager manager, String str, String iri) {
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

    public static void saveOntologyToStream(OWLOntologyManager manager, OWLOntology ont, OutputStream out, String defaultprefix) {
        try {
            manager.saveOntology(ont, new HetCASLOntologyFormat(defaultprefix), out);
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }
    }

    public static OWLOntology createOntologyFromURL(OWLOntologyManager manager, String url) {
        OWLOntology ont;
        try {
            ont = manager.createOntology(IRI.create(url));
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
            ont = null;
        }
        return ont;
    }

    public static OWLOntology displayOntology(OWLModelManager owlmodelmanager, String ont_string, String iri, String parent_iri) {
        OWLOntologyManager manager = owlmodelmanager.getOWLOntologyManager();

        manager.addOntologyStorer(new HetCASLOntologyStorer());

        OWLOntology parent_ont = manager.getOntology(IRI.create("http://informatik.uni-bremen.de/hets/"+parent_iri+".het"));

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

        owlmodelmanager.setActiveOntology(ontology1);

        return ontology1;
    }

    public static OWLOntology displayOntology(OWLModelManager owlmodelmanager, String ont_string, String iri) {
        OWLOntologyManager manager = owlmodelmanager.getOWLOntologyManager();

        manager.addOntologyStorer(new HetCASLOntologyStorer());

        OWLOntology ontology1 = loadOntologyFromString(manager, ont_string, iri);
        manager.setOntologyFormat(ontology1, new HetCASLOntologyFormat("http://informatik.uni-bremen.de/hets/"+iri+".het#"));

        owlmodelmanager.setActiveOntology(ontology1);

        return ontology1;
    }

    public static String dumpOntologyToString(OWLOntologyManager ontologymanager, String ontname) {
        String result;

        OWLOntology ont = ontologymanager.getOntology(IRI.create("http://informatik.uni-bremen.de/hets/"+ontname+".het"));

        System.out.println("ontology: " + ont);

        ByteArrayOutputStream outputstream = new ByteArrayOutputStream();

        try {
            ontologymanager.saveOntology(ont, outputstream);
        } catch (OWLOntologyStorageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        result = outputstream.toString();
        result = result.replaceAll("(?m)^Prefix: .*$", "");
        result = result.replaceAll("(?m)^Ontology: .*$", "");
        result = result.replaceAll("(?m)^Import: .*$", "");
        result = result.replaceAll("(?m)^\\s*$[\n\r]{1,}", "");
        result = result.replaceAll("(?m)(^.*$)", "  $1");
        return result;
    }
}
