package de.unibremen.informatik.hets.model;

import java.util.ArrayList;
import java.util.Iterator;

public class SpecDefn {
    private ArrayList<Spec> specs;
    private String name;
    private String logic;

    public SpecDefn(String n, String l) {
        name = n;
        logic = l;
        specs = new ArrayList<Spec>();
    }

    public void add(Spec spec) {
        specs.add(spec);
    }

    public String getLogic() {
        return logic;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("spec ");
        builder.append(name);
        builder.append(" =");

        Iterator<Spec> it = specs.iterator();
        for (;;) {
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
