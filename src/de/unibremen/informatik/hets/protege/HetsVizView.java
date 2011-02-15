package de.unibremen.informatik.hets.protege;

import java.io.File;

import de.unibremen.informatik.hets.graphviz.Graph;
import de.unibremen.informatik.hets.graphviz.DotGraphLayoutEngine;
import de.unibremen.informatik.hets.graphviz.NodeRenderer;
import de.unibremen.informatik.hets.graphviz.EdgeRenderer;
import de.unibremen.informatik.hets.graphviz.Node;
import de.unibremen.informatik.hets.graphviz.Edge;

import org.protege.editor.core.ui.view.ViewComponent;

import javax.swing.*;
import java.awt.*;

public class HetsVizView extends ViewComponent {
    Graph g;

    @Override
    public void initialise() throws Exception {
    }

    @Override
    public void dispose() throws Exception {
    }

    public void setDot(File file) {
        DotGraphLayoutEngine engine = new DotGraphLayoutEngine();
        g = engine.layoutGraph(file);
    }

    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;

        Shape clip = g2.getClip();
        g2.setColor(Color.WHITE);

        g2.fill(clip);

        g2.scale(1.0, 1.0);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (g == null) {
            return;
        }

        NodeRenderer noderenderer = new NodeRenderer();
        EdgeRenderer edgerenderer = new EdgeRenderer();
        Dimension size = new Dimension();

        Node[] nodes = g.getNodes();
        for (int i = 0; i < nodes.length; i++) {
            noderenderer.getPreferredSize(nodes[i], size);
            nodes[i].setSize(size.width, size.height);
            noderenderer.renderNode(g2, nodes[i], true, true);
        }

        Edge[] edges = g.getEdges();
        for (int i = 0; i < edges.length; i++) {
            edgerenderer.renderEdge(g2, edges[i], true, true);
        }
    }
}
