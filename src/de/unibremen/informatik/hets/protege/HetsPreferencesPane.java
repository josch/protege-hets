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

        OWLPreferencesPanel foo = new Foobar();
        OWLPreferencesPanel bar = new Foobar();
        OWLPreferencesPanel baz = new Foobar();

        JTabbedPane tabPane = new JTabbedPane();

        Box box = new Box(BoxLayout.Y_AXIS);
        box.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        box.add(foo);
        box.add(Box.createVerticalStrut(7));
        box.add(bar);
        box.add(Box.createVerticalStrut(7));
        box.add(baz);

        tabPane.add("General Options", box);

        optionPages.add(foo);
        optionPages.add(bar);
        optionPages.add(baz);

        foo.initialise();
        bar.initialise();
        baz.initialise();

        add(tabPane, BorderLayout.NORTH);
    }

    public void dispose() throws Exception {
        for (OWLPreferencesPanel optionPage : optionPages) {
            optionPage.dispose();
        }
    }

    class Foobar extends OWLPreferencesPanel {
        private JTextField pathField;

        public void applyChanges() {
            HetsPreferences.getInstance().setHetsPath(pathField.getText());
        }

        public void initialise() throws Exception {
            setLayout(new BorderLayout(12, 12));

            setBorder(BorderFactory.createTitledBorder("Dot Application Path"));

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
}
