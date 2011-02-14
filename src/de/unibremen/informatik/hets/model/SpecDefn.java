package de.unibremen.informatik.hets.model;

import java.util.ArrayList;
import java.util.Iterator;

import org.semanticweb.owlapi.model.OWLOntology;

public class SpecDefn {
    private ArrayList<Spec> specs;
    private String name;
    private String logic;
    private String annotation;

    public SpecDefn(String n, String l, String a) {
        name = n;
        logic = l;
        annotation = a;
        specs = new ArrayList<Spec>();
    }

    public void add(Spec spec) {
        specs.add(spec);
    }

    public String getLogic() {
        return logic;
    }

    public String getName() {
        return name;
    }

    public Iterator<Spec> getIterator() {
        return specs.iterator();
    }

    // just return the first Basicspec for now
    public OWLOntology getOntology() {
        for (Spec spec : specs) {
            if (spec instanceof Basicspec) {
                return ((Basicspec)spec).getOntology();
            }
        }
        return null;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (annotation != null && annotation != "") {
            builder.append(annotation);
            builder.append("\n");
        }
        builder.append("spec ");
        builder.append(name);
        builder.append(" =");

        Iterator<Spec> it = specs.iterator();
        while (it.hasNext()) {
            Spec spec = it.next();
            String annotation = spec.getAnnotation();

            if (annotation != null) {
                builder.append(" ");
                builder.append(spec.getAnnotation());
            }

            if (!(spec instanceof Union)) {
                builder.append("\n");
            }
            builder.append(spec.toString());

            if (it.hasNext()) {
                builder.append("then");
            } else {
                break;
            }
        }

        builder.append("end");
        builder.append("\n");

        return builder.toString();
    }
}
