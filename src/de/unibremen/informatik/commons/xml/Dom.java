package de.unibremen.informatik.commons.xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;

public class Dom {
    static {
    }

    public Dom() {
        super();
    }

    public static ArrayList<Element> getChildElements(Node item) {
        ArrayList<Element> result = new ArrayList<Element>();
        NodeList list = item.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            Node child = list.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                result.add((Element)child);
            }
        }

        return result;
    }

    public static String getTextContent(Element item) {
        StringBuilder sb = new StringBuilder();
        NodeList list = ((Node)item).getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node child = list.item(i);
            if (child.getNodeType() == Node.TEXT_NODE) {
                sb.append(child.getNodeValue());
            }
        }
        return sb.toString();
    }

    public static Element getFirstChildElement(Element item) {
        return getFirstChildElement((Node)item);
    }

    public static Element getFirstChildElement(Node item) {
        NodeList list = item.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node child = list.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                return (Element)child;
            }
        }
        return null;
    }

    public static Element getSecondChildElement(Node item) {
        NodeList list = item.getChildNodes();
        int count = 0;
        for (int i = 0; i < list.getLength(); i++) {
            Node child = list.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                if (count == 1) {
                    return (Element)child;
                } else {
                    count++;
                }
            }
        }
        return null;
    }

    public static Node getFirstChild(Node item, String name) {
        NodeList list = item.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName() == name) {
                return list.item(i);
            }
        }
        return null;
    }

    public static boolean hasChildNode(Node item, String name) {
        NodeList list = item.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName() == name) {
                return true;
            }
        }
        return false;
    }
}
