Hets Protege Plugin
===================

Installation
------------

To compile this Protege plugin a protege installation is required.
It easily can be checked out from svn and compiled using this script:

	#!/bin/sh -e

	export PROTEGE_HOME=/home/josch/protege-hets/protege_home
	mkdir -p $PROTEGE_HOME

	svn checkout -q http://smi-protege.stanford.edu/repos/protege/protege4/protege-base/trunk protege-base
	( cd protege-base; ant install; )

	for i in org.protege.common org.protege.editor.core.application org.semanticweb.owl.owlapi org.protege.editor.owl org.protege.jaxb org.coode.owlviz; do
		svn checkout -q http://smi-protege.stanford.edu/repos/protege/protege4/plugins/$i/trunk $i
		( cd $i; ant install; )
	done

Bugs
----
