package de.unibremen.informatik.hets.model;

import java.util.ArrayList;
import java.util.Iterator;

import org.semanticweb.owlapi.model.OWLOntology;

import org.protege.editor.owl.model.OWLModelManager;

public class HetFile {
    private String libname;
    private ArrayList<SpecDefn> specdefns;

    public HetFile(String name) {
        libname = name;
        specdefns = new ArrayList<SpecDefn>();
    }

    public void add(SpecDefn specdefn) {
        specdefns.add(specdefn);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("library ");
        builder.append(libname);
        builder.append("\n\n");

        String logic = "";

        for (SpecDefn specdefn : specdefns) {
            if (logic != specdefn.getLogic()) {
                logic = specdefn.getLogic();

                builder.append("logic ");
                builder.append(logic);
                builder.append("\n\n");
            }

            builder.append(specdefn.toString());
            builder.append("\n");
        }

        return builder.toString();
    }

    public OWLOntology getOntologyByName(String name) {
        for (SpecDefn specdefn : specdefns) {
            if (specdefn.getLogic().equals("OWL") && specdefn.getName().equals(name)) {
                return specdefn.getOntology();
            }
        }
        return null;
    }

    public void displayOntologies(OWLModelManager manager) {
        // TODO: this yet only supports basicspec and simple extension blocks

        for (SpecDefn specdefn : specdefns) {
            if (specdefn.getLogic().equals("OWL")) {
                Iterator<Spec> it = specdefn.getIterator();

                String parent_iri = "";

                while (it.hasNext()) {
                    Spec spec = it.next();
                    if (spec instanceof Basicspec) {
                        if (parent_iri != "") {
                            ((Basicspec)spec).displayOntology(manager, specdefn.getName(), getOntologyByName(parent_iri), parent_iri);
                        } else {
                            ((Basicspec)spec).displayOntology(manager, specdefn.getName());
                        }
                    } else if (spec instanceof Actuals) {
                        parent_iri = ((Actuals)spec).getName();
                    }
                }
            }
        }
    }
}
