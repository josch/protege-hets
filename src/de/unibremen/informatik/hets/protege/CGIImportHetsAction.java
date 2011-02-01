package de.unibremen.informatik.hets.protege;

import java.io.File;
import java.util.Set;
import java.util.HashSet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.HeadlessException;

import org.protege.editor.owl.ui.action.ProtegeOWLAction;
import org.protege.editor.core.ui.util.UIUtil;

public class CGIImportHetsAction extends ProtegeOWLAction {

	private static final long serialVersionUID = -4056096587762591108L;

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
	}
}
