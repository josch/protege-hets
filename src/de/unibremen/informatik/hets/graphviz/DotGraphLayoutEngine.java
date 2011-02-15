package de.unibremen.informatik.hets.graphviz;

/*
import org.apache.log4j.Logger;
import uk.ac.man.cs.mig.util.graph.graph.Graph;
import uk.ac.man.cs.mig.util.graph.layout.GraphLayoutEngine;
import uk.ac.man.cs.mig.util.graph.layout.dotlayoutengine.dotparser.DotParameterSetter;
import uk.ac.man.cs.mig.util.graph.layout.dotlayoutengine.dotparser.DotParser;
import uk.ac.man.cs.mig.util.graph.layout.dotlayoutengine.dotparser.ParseException;
import uk.ac.man.cs.mig.util.graph.outputrenderer.GraphOutputRenderer;
import uk.ac.man.cs.mig.util.graph.outputrenderer.impl.DotOutputGraphRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.impl.DefaultEdgeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.impl.DefaultNodeLabelRenderer;
*/
import de.unibremen.informatik.hets.graphviz.dotparser.DotParser;

import java.io.*;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 16, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */
public class DotGraphLayoutEngine
{
    //private static Logger log = Logger.getLogger(DotGraphLayoutEngine.class);
	//private GraphOutputRenderer renderer;

    public static final int LAYOUT_LEFT_TO_RIGHT = 0;
    public static final int LAYOUT_TOP_TO_BOTTOM = 1;
    private int layoutDirection = LAYOUT_LEFT_TO_RIGHT;

    public DotGraphLayoutEngine()
	{
		//renderer = new DotOutputGraphRenderer(new DefaultNodeLabelRenderer(), new DefaultEdgeLabelRenderer());

	}

    /*
	public void setGraphOutputRenderer(GraphOutputRenderer renderer)
	{
		this.renderer = renderer;
	}
    */

	/**
	 * Lays out the specified <code>Graph</code>
	 * @param g The <code>Graph</code>
	 */
	public synchronized Graph layoutGraph(File file)
	{
		long t0, t1, t2;
        Graph g = new Graph();
        Node n0 = new Node("0");
        Node n1 = new Node("1");
        Node n2 = new Node("2");
        Node n3 = new Node("3");
        Node n4 = new Node("4");
        Node n5 = new Node("5");
        g.add(n0);
        g.add(n1);
        g.add(n2);
        g.add(n3);
        g.add(n4);
        g.add(n5);
        Edge e0 = new Edge(n0, n1, "0", 1);
        Edge e1 = new Edge(n0, n2, "1", 1);
        Edge e2 = new Edge(n0, n3, "2", 1);
        Edge e3 = new Edge(n0, n4, "3", 1);
        Edge e4 = new Edge(n3, n0, "4", 1);
        Edge e5 = new Edge(n4, n5, "5", 1);
        g.add(e0);
        g.add(e1);
        g.add(e2);
        g.add(e3);
        g.add(e4);
        g.add(e5);

		DotProcess process = new DotProcess();

            // Render the graph in DOT and send it to the
            // DOT Process the be laid out
            /*
            if(layoutDirection == LAYOUT_LEFT_TO_RIGHT)
            {
                renderer.setRendererOption(DotOutputGraphRenderer.LAYOUT_DIRECTION, "LR");
            }
            else
            {
                renderer.setRendererOption(DotOutputGraphRenderer.LAYOUT_DIRECTION, "TB");
            }
            */

		//DotLayoutEngineProperties properties = DotLayoutEngineProperties.getInstance();

        /*
            renderer.setRendererOption(DotOutputGraphRenderer.RANK_SPACING, Double.toString(properties.getRankSpacing()));

            renderer.setRendererOption(DotOutputGraphRenderer.SIBLING_SPACING, Double.toString(properties.getSiblingSpacing()));
            */


	        try
	        {
		        //File file = File.createTempFile("OWLVizScratch", null);

		        //file.deleteOnExit();

		        //log.debug("TRACE(DotGraphLayoutEngine): TempFile: " + file.getAbsolutePath());

	            //FileOutputStream fos = new FileOutputStream(file);

		        //renderer.renderGraph(g, fos);

		        //fos.close();

				if(process.startProcess(file.getAbsolutePath()) == false)
				{
					return null;
				}


          // Read outputrenderer from process and parse it

	           InputStream is = null;



	           is = new FileInputStream(file);

		         // InputStream is = process.getReader();

				if(is != null)
				{
					try
					{
						DotParameterSetter paramSetter = new DotParameterSetter();

						//paramSetter.setGraph(g);
                        paramSetter.setGraph(g);

						DotParser.parse(paramSetter, is);
					}
					catch(ParseException e)
					{
						e.printStackTrace();
					}

					t1 = System.currentTimeMillis();

				//	parser.parse(is);

					process.killProcess();

					process = null;
				}

				t2 = System.currentTimeMillis();
	        }
	        catch(IOException ioEx)
	        {
		        ioEx.printStackTrace();
	        }


        return g;
	}

    /**
     * Sets the direction of the layout.
     * @param layoutDirection The layout direction. This should be one of
     * the constants <code>DotGraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT</code> or
     * <code>DotGraphLayoutEngine.LAYOUT_TOP_TO_BOTTOM</code>.
     */
    public void setLayoutDirection(int layoutDirection)
    {
        this.layoutDirection = layoutDirection;
    }

    /**
     * Gets the layout direction.
     * @return The direction of the layout. <code>DotGraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT</code> or
     * <code>DotGraphLayoutEngine.LAYOUT_TOP_TO_BOTTOM</code>.
     */
    public int getLayoutDirection()
    {
        return layoutDirection;
    }
}
