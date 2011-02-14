package de.unibremen.informatik.hets.protege;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.net.URL;
import java.net.MalformedURLException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.HeadlessException;

import org.protege.editor.owl.ui.action.ProtegeOWLAction;
import org.protege.editor.core.ui.util.UIUtil;

import de.unibremen.informatik.commons.net.HttpPostUrlencoded;
import de.unibremen.informatik.commons.io.IOUtils;
import de.unibremen.informatik.hets.model.HetFile;
import de.unibremen.informatik.hets.model.PPXMLParser;
import de.unibremen.informatik.hets.model.PPXMLParserException;

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
        InputStream input = null;
        Matcher matcher = null;
        HetFile hetfile = null;

        File file = UIUtil.openFile(new JFrame(), "HetCASL", "Please select a *.het file", exts);

        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("f0x0", file);
        args.put("f0x3", "on");
        args.put("s0x5", "Submit");
        try {
            input = HttpPostUrlencoded.post(new URL("http://www.informatik.uni-bremen.de/cgi-bin/cgiwrap/maeder/hets.cgi"), args);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String patternStr = "http://www.informatik.uni-bremen.de/cofi/hets-tmp/result\\d+.pp.xml";
        Pattern pattern = Pattern.compile(patternStr);
        try {
            matcher = pattern.matcher(IOUtils.getBuffer(input));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!matcher.find()) {
            // TODO: throw exception
        }

        try {
            hetfile = PPXMLParser.parse(new URL(matcher.group()).openStream(),
                    IOUtils.getString(new FileInputStream(file)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PPXMLParserException e) {
            e.printStackTrace();
        }

        System.out.println(hetfile.toString());
	}
}
