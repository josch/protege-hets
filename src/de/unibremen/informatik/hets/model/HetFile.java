package de.unibremen.informatik.hets.model;

import java.util.ArrayList;

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
}
