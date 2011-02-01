package de.unibremen.informatik.hets.protege;

import de.unibremen.informatik.hets.protege.HetsPreferences;

import org.protege.editor.core.ui.util.UIUtil;
import org.protege.editor.owl.ui.preferences.OWLPreferencesPanel;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HetsPreferencesPane extends OWLPreferencesPanel {

    private java.util.List<OWLPreferencesPanel> optionPages = new ArrayList<OWLPreferencesPanel>();

    public void applyChanges() {
        for (OWLPreferencesPanel optionPage : optionPages) {
            optionPage.applyChanges();
        }
    }

    public void initialise() throws Exception {
        setLayout(new BorderLayout());

        OWLPreferencesPanel dotpath = new DotPath();
        OWLPreferencesPanel hetspath = new HetsPath();
        OWLPreferencesPanel cgiurl = new CGIUrl();
        OWLPreferencesPanel restfulhets = new RestFulHets();

        JTabbedPane tabPane = new JTabbedPane();

        Box box1 = new Box(BoxLayout.Y_AXIS);
        box1.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        box1.add(dotpath);
        box1.add(Box.createVerticalStrut(7));
        box1.add(hetspath);
        box1.add(Box.createVerticalStrut(7));
        box1.add(cgiurl);
        box1.add(Box.createVerticalStrut(7));
        box1.add(restfulhets);

        tabPane.add("General Options", box1);

        optionPages.add(dotpath);
        optionPages.add(hetspath);
        optionPages.add(cgiurl);
        optionPages.add(restfulhets);

        dotpath.initialise();
        hetspath.initialise();
        cgiurl.initialise();
        restfulhets.initialise();

        Box box2 = new Box(BoxLayout.Y_AXIS);

        tabPane.add("Graph Options", box2);

        add(tabPane, BorderLayout.NORTH);
    }

    public void dispose() throws Exception {
        for (OWLPreferencesPanel optionPage : optionPages) {
            optionPage.dispose();
        }
    }

    class DotPath extends OWLPreferencesPanel {
        private JTextField pathField;

        public void applyChanges() {
            HetsPreferences.getInstance().setDotPath(pathField.getText());
        }

        public void initialise() throws Exception {
            setLayout(new BorderLayout(12, 12));

            setBorder(BorderFactory.createTitledBorder("Dot Application Path"));

            Box panel = new Box(BoxLayout.LINE_AXIS);

            pathField = new JTextField(15);
            pathField.setText(HetsPreferences.getInstance().getDotPath());

            JButton browseButton = new JButton(new AbstractAction("Browse") {
                    public void actionPerformed(ActionEvent e) {
                    }
            });

            panel.add(new JLabel("Path:"));
            panel.add(pathField);
            panel.add(browseButton);

            add(panel);
        }

        public void dispose() throws Exception {
        }
    }

    class HetsPath extends OWLPreferencesPanel {
        private JTextField pathField;

        public void applyChanges() {
            HetsPreferences.getInstance().setHetsPath(pathField.getText());
        }

        public void initialise() throws Exception {
            setLayout(new BorderLayout(12, 12));

            setBorder(BorderFactory.createTitledBorder("Hets Application Path"));

            Box panel = new Box(BoxLayout.LINE_AXIS);

            pathField = new JTextField(15);
            pathField.setText(HetsPreferences.getInstance().getHetsPath());

            JButton browseButton = new JButton(new AbstractAction("Browse") {
                    public void actionPerformed(ActionEvent e) {
                    }
            });

            panel.add(new JLabel("Path:"));
            panel.add(pathField);
            panel.add(browseButton);

            add(panel);
        }

        public void dispose() throws Exception {
        }
    }


    class CGIUrl extends OWLPreferencesPanel {
        private JTextField pathField;

        public void applyChanges() {
            HetsPreferences.getInstance().setCGIUrl(pathField.getText());
        }

        public void initialise() throws Exception {
            setLayout(new BorderLayout(12, 12));

            setBorder(BorderFactory.createTitledBorder("CGI Url"));

            Box panel = new Box(BoxLayout.LINE_AXIS);

            pathField = new JTextField(15);
            pathField.setText(HetsPreferences.getInstance().getCGIUrl());

            panel.add(new JLabel("Url:"));
            panel.add(pathField);

            add(panel);
        }

        public void dispose() throws Exception {
        }
    }

    class RestFulHets extends OWLPreferencesPanel {
        private JTextField pathField;

        public void applyChanges() {
            HetsPreferences.getInstance().setRestFulUrl(pathField.getText());
        }

        public void initialise() throws Exception {
            setLayout(new BorderLayout(12, 12));

            setBorder(BorderFactory.createTitledBorder("Hets RestFul Url"));

            Box panel = new Box(BoxLayout.LINE_AXIS);

            pathField = new JTextField(15);
            pathField.setText(HetsPreferences.getInstance().getRestFulUrl());

            panel.add(new JLabel("Url:"));
            panel.add(pathField);

            add(panel);
        }

        public void dispose() throws Exception {
        }
    }
}
