package de.unibremen.informatik.hets.model;

import java.util.ArrayList;
import java.io.InputStream;
import java.io.IOException;

import de.unibremen.informatik.hets.model.HetFile;
import de.unibremen.informatik.hets.model.PPXMLParserException;
import de.unibremen.informatik.commons.xml.Dom;
import de.unibremen.informatik.commons.io.IOUtils;
import de.unibremen.informatik.commons.lang.StringUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PPXMLParser {
    static {
    }

    public PPXMLParser() {
        super();
    }

    private static String getrange(String hetfile, String range) throws PPXMLParserException {
        int minus = range.indexOf('-');
        int fromdot, todot;
        int fromline, fromcolumn;
        int toline, tocolumn;

        String from, to;

        try {
            from = range.substring(0, minus);
            to = range.substring(minus+1);
        } catch (IndexOutOfBoundsException e) {
            throw new PPXMLParserException(e);
        }

        fromdot = from.indexOf('.');
        todot = to.indexOf('.');

        if (fromdot == -1 || todot == -1) {
            throw new PPXMLParserException();
        }

        try {
            fromline = Integer.parseInt(from.substring(0, fromdot));
            fromcolumn = Integer.parseInt(from.substring(fromdot+1));

            toline = Integer.parseInt(to.substring(0, todot));
            tocolumn = Integer.parseInt(to.substring(todot+1));
        } catch (NumberFormatException e) {
            throw new PPXMLParserException(e);
        } catch (IndexOutOfBoundsException e) {
            throw new PPXMLParserException(e);
        }

        try {
            return StringUtils.getSlice(hetfile, fromline, fromcolumn, toline, tocolumn);
        } catch (java.io.IOException e) {
            throw new PPXMLParserException(e);
        }
    }

    private static Spec parsespec(String hetfile, Element spec) throws PPXMLParserException {
        spec = Dom.getFirstChildElement(spec);

        if (spec == null) {
            throw new PPXMLParserException("<spec> block doesnt have a child");
        }

        if (spec.getTagName() == "Union") {
            Union un = new Union();
            ArrayList<Element> speclist = Dom.getChildElements(spec);

            for (Element s : speclist) {
                un.add(parsespec(hetfile, s));
            }
            return un;
        } else {
            return parsespecspec(hetfile, spec);
        }
    }

    private static Spec parsespecspec(String hetfile, Element spec) throws PPXMLParserException {
        Element left = Dom.getFirstChildElement(spec);

        String annotation = "";

        if (left != null && left.getTagName() == "Left") {
            annotation = Dom.getTextContent(Dom.getFirstChildElement(left));
        }

        if (spec.getTagName() == "Basicspec") {
            return new Basicspec(getrange(hetfile, spec.getAttribute("range")), annotation);
        } else if (spec.getTagName() == "Actuals") {
            return new Actuals(spec.getAttribute("name"), annotation);
        } else {
            throw new PPXMLParserException();
        }
    }

    public static HetFile parse(InputStream ppxml, String hetfile) throws PPXMLParserException {
        Document document;

        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ppxml);
        } catch (ParserConfigurationException e) {
            throw new PPXMLParserException(e);
        } catch (org.xml.sax.SAXException e) {
            throw new PPXMLParserException(e);
        } catch (IOException e) {
            throw new PPXMLParserException(e);
        }

        Node root = document.getDocumentElement();

        HetFile het = new HetFile(((Element)root).getAttribute("name"));

        ArrayList<Element> list = Dom.getChildElements(root);

        String currentlogic = "";

        for (Element item : list) {
            if (item.getNodeName() == "Logic") {
                currentlogic = ((Element)item).getAttribute("name");
            } else if (item.getNodeName() == "SpecDefn") {
                if (currentlogic == "") {
                    throw new PPXMLParserException();
                }

                String annotation = "";

                Element child = Dom.getFirstChildElement(item);

                if (child.getTagName() == "Left") {
                    annotation = Dom.getTextContent(Dom.getFirstChildElement(child));

                    child = Dom.getSecondChildElement(item);
                }

                SpecDefn specdefn = new SpecDefn(((Element)item).getAttribute("name"), currentlogic, annotation);

                if (child.getTagName() == "Basicspec") {
                    specdefn.add(parsespecspec(hetfile, child));
                } else if (child.getTagName() == "Extension") {
                    ArrayList<Element> speclist = Dom.getChildElements(child);

                    for (Element spec : speclist) {
                        specdefn.add(parsespec(hetfile, spec));
                    }
                } else {
                    throw new PPXMLParserException("unexpected element: " + child.getTagName());
                }

                het.add(specdefn);
            }
        }

        return het;
    }
}
