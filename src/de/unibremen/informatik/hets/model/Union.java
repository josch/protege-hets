package de.unibremen.informatik.hets.model;

import java.util.ArrayList;
import java.util.Iterator;

import org.semanticweb.owlapi.model.OWLOntologyManager;

public class Union extends Spec {
    private ArrayList<Spec> specs;

    public Union() {
        specs = new ArrayList<Spec>();
    }

    public void add(Spec spec) {
        if (spec instanceof Union) {
            return;
        } else {
            specs.add(spec);
        }
    }

    public String toString(OWLOntologyManager ontologymanager) {
        StringBuilder builder = new StringBuilder();

        Iterator<Spec> it = specs.iterator();
        for (;;) {
            Spec spec = it.next();
            String annotation = spec.getAnnotation();

            if (annotation != null) {
                builder.append(" ");
                builder.append(spec.getAnnotation());
            }

            builder.append("\n");
            builder.append(spec.toString());

            if (it.hasNext()) {
                builder.append("and");
            } else {
                break;
            }
        }

        return builder.toString();
    }
}
