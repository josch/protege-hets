Hets Protege Plugin
===================

Originating from an internship task, this now over 5200 lines big plugin
allows protege to load, edit and save *.het files [rest2html][1].

It still suffers from most exceptions to be only printed on standard output
without a popup dialog informing the user of something going wrong so the
plugin should still be run from the terminal.

Installation
------------

To compile this Protege plugin a protege installation is required.
It easily can be checked out from svn and compiled using this script:

	#!/bin/sh -e

	svn checkout -q http://smi-protege.stanford.edu/repos/protege/protege4/protege-base/trunk protege-base
	( cd protege-base; ant install; )

	for i in org.protege.common \
		 org.protege.editor.core.application \
		 org.semanticweb.owl.owlapi \
		 org.protege.editor.owl \
		 org.protege.jaxb \
		 org.coode.owlviz
	do
		svn checkout -q http://smi-protege.stanford.edu/repos/protege/protege4/plugins/$i/trunk $i
		( cd $i; ant install; )
	done

For this and builds of the Hets Protege plugin to work an environment
variable called $PROTEGE_HOME has to be set to the directory where
protege should be/is installed.

After having done that, the plugin can be compiled using ant:

	ant

Or can be compiled and run right away using:

	ant run

At all times $PROTEGE_HOME has to be set, so it makes sense to add it to
your profile.d or similar.

Usage
-----

To run protege, simply run

	ant run

To import a new *.het file, use the Hets menu in the menubar and then select one of
the available import methods.

The most well working *.het file is Family.het located in the examples directory of
this repository.

After doing edits of the contained ontologies the *.het file can be saved using
the export feature of the Hets menu in the menubar.

Adding the HetsViz tab to the interface will allow a graphical representation of the
current *.het file hierarchy. (in the menubar under view->Class views->HetsViz)

Using the HetCASL rendering will show a preview of the Manchester OWL output for
the currently selected ontology. (in the menubar under view->Ontology views->HetCASL rendering)

In the settings dialog (File->Preferences->Hets) several options can be set for the
plugin.

Features
--------

 * hets import using custom grammar, local hets, remote cgi
 * hets export
 * settings dialog
 * graphviz view

Missing Features
----------------

 * parsing *.het files containing view sections
 * loading more than one *.het file at once

Currently Broken Features (will be fixed soon)
----------------------------------------------

 * "local hets" and "custom parser" import methods
 * hetsview graphviz plugin
 * settings dialog

Bugs
----

 * hets interprets a tab as 8 spaces for its pp.xml output - have to work around that

[1]: http://www.informatik.uni-bremen.de/agbkb/forschung/formal_methods/CoFI/hets/index_e.htm
