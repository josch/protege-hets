package de.unibremen.informatik.hets.common.xml

public class Dom {
    static {
    }

    public Dom() {
        super();
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
