package de.unibremen.informatik.hets.graphviz;

import java.io.IOException;

import javax.swing.JOptionPane;

//import org.apache.log4j.Logger;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 16, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 * <p/>
 * A wrapper for a native dot process.
 */
public class DotProcess {
    //private static Logger log = Logger.getLogger(DotProcess.class);

	private Process process;


	/**
	 * Contructs a <code>DotProcess</code>, and starts
	 * the native dot process. Using the default process
	 * path for the particular platfrom being used.
	 */
	public DotProcess() {

	}


	/**
	 * Lays out a graph using the dot application
	 *
	 * @param fileName A file that acts as a 'scratch pad'
	 *                 The graph is read from this file, and then export
	 *                 to the same file in attributed dot format.
	 * @return <code>true</code>  if the process completed without
	 *         any errors, or <code>false</code> if the process did not
	 *         complete.
	 */
	public boolean startProcess(String fileName) {
		if(process != null) {
			killProcess();
		}

		Runtime r = Runtime.getRuntime();
		//DotLayoutEngineProperties properties = DotLayoutEngineProperties.getInstance();

		try {
			//process = r.exec(properties.getDotProcessPath() + " " + fileName + " -q -o " + fileName);
			process = r.exec("/usr/bin/dot" + " " + fileName + " -q -o " + fileName);

			try {
				process.waitFor();

				return true;
			}

			catch(InterruptedException irEx) {
				irEx.printStackTrace();

				return false;
			}
		}

		catch(IOException ioEx) {
			String errMsg = "An error related to DOT has occurred. " + "This error was probably because OWLViz could not" + " find the DOT application.  Please ensure that the" + " path to the DOT application is set properly";

			String dlgErrMsg = "<html><body>A DOT error has occurred.<br>" +
                    "This is probably because OWLViz could not find the DOT application.<br>" +
                    "OWLViz requires that Graphviz (http://www.graphviz.org/) is installed<br>" +
                    " and the path to the DOT application is set properly (in options).</body></html>";

			JOptionPane.showMessageDialog(null, dlgErrMsg, "DOT Error", JOptionPane.ERROR_MESSAGE);
			
			// Display on stderr
			//log.error("DOT Process Error:");

			//log.error(ioEx.getMessage());

			//log.error(errMsg);

			return false;

			// Display a dialog
			//ioEx.printStackTrace();
		}
	}


	/**
	 * Kills the native dot process (if it was started
	 * successfully).
	 */
	protected void killProcess() {
		if(process != null) {
			process.destroy();

			process = null;
		}
	}
}
