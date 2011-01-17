package de.unibremen.informatik.hets.protege;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;



public class Activator implements BundleActivator {

	//private OWLDataFactory dataFactory;
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Hello Woaaaarld!!");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye World!!");
	}

}
