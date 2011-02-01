package de.unibremen.informatik.hets.protege;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.HeadlessException;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;

import org.protege.editor.owl.ui.action.ProtegeOWLAction;
import org.protege.editor.core.ui.util.UIUtil;

public class LocalImportHetsAction extends ProtegeOWLAction {

	private static final long serialVersionUID = -4056096587762591108L;

    private Process process;

	@Override
	public void initialise() throws Exception {
	}

	@Override
	public void dispose() throws Exception {
	}

	@Override
	public void actionPerformed(ActionEvent event) {
        Set<String> exts = new HashSet<String>();
        exts.add("het");
        exts.add("owl");
        File file = null;
        try {
            file = UIUtil.openFile(new JFrame(), "HetCASL", "Please select a *.het file", exts);
        } catch (HeadlessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        if(process != null) {
            process.destroy();
            process = null;
        }

        Runtime r = Runtime.getRuntime();

        try {
            process = r.exec("/usr/bin/hets" + " " + file.getAbsolutePath() + " -o pp.xml");

            try {
                process.waitFor();
            } catch(InterruptedException irEx) {
                irEx.printStackTrace();
            }
        } catch(IOException ioEx) {
            ioEx.printStackTrace();
        }
	}
}
