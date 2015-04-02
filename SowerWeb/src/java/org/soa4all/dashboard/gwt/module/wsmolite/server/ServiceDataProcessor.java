package org.soa4all.dashboard.gwt.module.wsmolite.server;

import java.util.*;

import javax.xml.XMLConstants;

import org.soa4all.dashboard.gwt.module.wsmolite.client.Constants;
import org.w3c.dom.*;


public class ServiceDataProcessor {
    
   
    public static void markElementsWithIDs(Element root) {
        markElementsWithIDs(root, "0");
    }
    private static void markElementsWithIDs(Element root, String prefix) {
        root.setAttribute("xID", prefix);
        NodeList ch = root.getChildNodes();
        for(int i = 0; i < ch.getLength(); i++) {
            if (ch.item(i) instanceof Element) {
                Element el = (Element)ch.item(i);
                markElementsWithIDs(el, prefix + " " + i);
            }
        }
    }
    
    public static void cleanXData(Element root) {
        root.removeAttribute("xID");
        NodeList ch = root.getChildNodes();
        for(int i = 0; i < ch.getLength(); i++) {
            if (ch.item(i) instanceof Element) {
                cleanXData((Element)ch.item(i));
            }
        }        
    }

    public static void simplifyTree(Element root) {
        NodeList children = root.getChildNodes();
        List<Node> todo = new LinkedList<Node>();
        for(int i = 0; i < children.getLength(); i++) {
            todo.add(children.item(i));
        }
        
        for(Node node : todo) {
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                root.removeChild(node);
                continue;
            }
            Element el = (Element)node;
            if (isImportantElement(el)) {
                simplifyTree(el);
            }
            else {
                for (Element newCh : getSimplifiedContent(el)) {
                    root.insertBefore(newCh, el);
                }
                root.removeChild(el);
            }
        }
    }
    
    private static List<Element> getSimplifiedContent(Element root) {
        List<Element> result = new LinkedList<Element>();
        NodeList children = root.getChildNodes();
        for(int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                if (isImportantElement((Element)children.item(i))) {
                    result.add((Element)children.item(i));
                    simplifyTree((Element)children.item(i));
                }
                else {
                    result.addAll(getSimplifiedContent((Element)children.item(i)));
                }
            }
        }
        return result;
    }
    
    private static boolean isImportantElement(Element el) {
        if (false == XMLConstants.W3C_XML_SCHEMA_NS_URI.equals(el.getNamespaceURI())) {
            return false;
        }
        if ("complexType".equals(el.getLocalName())) {
            return el.getAttributeNode("name") != null;
        }
        return ("element".equals(el.getLocalName())
                    || "attribute".equals(el.getLocalName())
                    || "simpleType".equals(el.getLocalName())
                    || "schema".equals(el.getLocalName())
                    || "group".equals(el.getLocalName())
                    || "any".equals(el.getLocalName())
                    || "attributeGroup".equals(el.getLocalName())) ;
    } 
    public static void buildLabels(Element el) {
        String lab = findLabel(el);
        if (lab != null) {
            el.setAttribute("xLab", lab);
        }
        buildTooltip(el);
        NodeList children = el.getChildNodes();
        for(int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                buildLabels((Element)children.item(i));
            }
        }
    }
    private static void buildTooltip(Element el) {
        String tooltipData = null;
        if (el.getNamespaceURI().equals(XMLConstants.W3C_XML_SCHEMA_NS_URI)) {
            if (el.getLocalName().equals("import")) {
                tooltipData = getAttr(el, "location");
                if (tooltipData == null) {
                    tooltipData = getAttr(el, "schemaLocation");
                }
                if (tooltipData != null) {
                    tooltipData = "location: " + tooltipData;
                }
            }
            else if (el.getLocalName().equals("simpleType")) {
                tooltipData = getSimpleTypeTooltip(el);
            }
            else if (el.getLocalName().equals("complexType")) {
                tooltipData = getComplexTypeTooltip(el);
            }
            else if (el.getLocalName().equals("group")) {
                Element child = getFirstElement(el);
                if (child != null) {
                    tooltipData = child.getLocalName();
                }
            }
            else if (el.getLocalName().equals("attribute")) {
                Element child = getFirstElement(el);
                if (child != null) {
                    tooltipData = getSimpleTypeTooltip(child);
                }
            }
            else if (el.getLocalName().equals("element")) {
                Element child = getFirstElement(el);
                if (child != null) {
                    if (child.getNodeName().endsWith("simpleType")) {
                        tooltipData = getSimpleTypeTooltip(child);
                    }
                    else if (child.getNodeName().endsWith("complexType")) {
                        tooltipData = getComplexTypeTooltip(child);
                    }
                }
            }
            // TODO add more elements
        }
        else if (el.getNamespaceURI().equals(Constants.WSDL_NS) 
                || el.getNamespaceURI().equals(Constants.WSDL_2_NS)
                || el.getNamespaceURI().equals(Constants.WSDL_2_NS_OLD)) {
            if ((el.getLocalName().equals("fault")
                    || el.getLocalName().equals("input")
                    || el.getLocalName().equals("output")) 
                        && el.getAttributeNode("element") != null) {
                tooltipData = "element: " + el.getAttribute("element");
            }
            else if ((el.getLocalName().equals("infault")
                    || el.getLocalName().equals("outfault"))
                        && el.getAttributeNode("messageLabel") != null) {
                tooltipData = "messageLabel: " + el.getAttribute("messageLabel");
            }
            else if ((el.getLocalName().equals("binding")
                    || el.getLocalName().equals("service"))
                    && el.getAttributeNode("interface") != null) {
                tooltipData = "interface: " + el.getAttribute("interface");
            }
        }
        if (tooltipData != null 
                && tooltipData.length() > 0) {
            el.setAttribute("xTooltip", tooltipData);
        }
    }
    
    private static String getSimpleTypeTooltip(Element el) {
        Element child = getFirstElement(el);
        if (child != null) {
            if (child.getNodeName().endsWith("restriction")) {
                return "restriction on " + child.getAttribute("base");
            }
            else if (child.getNodeName().endsWith("list")) {
                return "list of " + child.getAttribute("itemType");
            }
            else if (child.getNodeName().endsWith("union")) {
                return "union of " + child.getAttribute("memberTypes");
            }
        }
        return null;
    }
    
    public static String getComplexTypeTooltip(Element el) {
        Element child = getFirstElement(el);
        if (child != null) {
            if (child.getNodeName().endsWith("simpleContent") 
                    || child.getNodeName().endsWith("complexContent")) {
                Element subChild = getFirstElement(child);
                if (subChild != null) {
                    return subChild.getNodeName() 
                                  + " of " 
                                  + subChild.getAttribute("base");
                }
            }
            else {
                return child.getLocalName();
            }
        }
        return null;
    }
    
    private static String findLabel(Element element) {
        String ln = element.getTagName();
        if (ln.indexOf(":") != -1) {
            ln = ln.substring(ln.indexOf(":") + 1);
        }        
        String label = null;
        if (element.getNamespaceURI().equals(XMLConstants.W3C_XML_SCHEMA_NS_URI)) {
            if (ln.equals("schema")) {
                label = getAttr(element, "targetNamespace");
                if (label == null) {
                    label = "schema";
                }
                else {
                    label = "schema: " + label;
                }
            }
            else if (ln.equals("import")) {
                label = getAttr(element, "namespace");
                if (label == null) {
                    label = getAttr(element, "targetNamespace");
                }
                if (label == null) {
                    label = getAttr(element, "location");
                }
                if (label == null) {
                    label = getAttr(element, "schemaLocation");
                }
                
                if (label != null) {
                    label = "import: " + label;
                }
            }
            else if (ln.equals("attribute")) {
                label = getAttr(element, "name");
                if (label == null) {
                    label = getAttr(element, "ref");
                }
                if (label != null
                        && getAttr(element, "type") != null) {
                    label += "  << " + getAttr(element, "type") + " >>";
                }
            }
            else if (ln.equals("element")) {
                label = getAttr(element, "name");
                if (label == null) {
                    label = getAttr(element, "ref");
                }
                if (label != null
                        && element.getAttributeNode("type") != null) {
                    label += "  << " + getAttr(element, "type") + " >>";
                }
            }
            else if (ln.equals("complexType")) {
                label = getAttr(element, "name");
            }
            else if (ln.equals("simpleType")) {
                label = getAttr(element, "name");
                if (label != null) {
                    NodeList nl = element.getChildNodes();
                    if (nl != null) {
                        for (int i = 0; i < nl.getLength(); i++) {
                            if (nl.item(i).getNodeType() != Node.ELEMENT_NODE) {
                                continue;
                            }
                            Element el = (Element)nl.item(i);
                            if (el.getTagName().endsWith("restriction")) {
                                if (el.getAttributeNode("base") != null) {
                                    label += " (restriction on << " + el.getAttribute("base") + " >>)";
                                    break;
                                }
                            }
                            if (el.getTagName().endsWith("list")) {
                                if (el.getAttributeNode("itemType") != null) {
                                    label += " (list of << " + el.getAttribute("itemType") + " >>)";
                                    break;
                                }
                            }
                            if (el.getTagName().endsWith("union")) {
                                if (el.getAttributeNode("memberTypes") != null) {
                                    label += " (uniun of: " + el.getAttribute("memberTypes") + ")";
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            else if (ln.equals("attributeGroup") 
                    || ln.equals("group")) {
                label = getAttr(element, "name");
                if (label == null) {
                    label = getAttr(element, "ref");
                }
            }
        }
        else if (element.getNamespaceURI().equals(Constants.WSDL_2_NS) 
                || element.getNamespaceURI().equals(Constants.WSDL_2_NS_OLD)) { // WSDL 2.0
           
            if (ln.equals("interface") 
                    || ln.equals("fault")
                    || ln.equals("operation")) {
                label = getAttr(element, "name");
            }
            else if (ln.equals("binding") 
                    || ln.equals("service")) {
                label = getAttr(element, "name");
                if (label != null) {
                    label = ln + ": " + label;
                }
            }
            else if (ln.equals("input")
                    || ln.equals("output")) {
                label = getAttr(element, "messageLabel");
                if (label == null) {
                    label = getAttr(element, "element");
                }
            }
            else if (ln.equals("infault") 
                    || ln.equals("outfault")) {
                label = getAttr(element, "ref");
                if (label != null) {
                    label = ln + ": " + label;
                }
            }
        }
        else if (element.getNamespaceURI().equals(Constants.WSDL_NS)) { // WSDL 1.1
           
            if (ln.equals("portType") 
                    || ln.equals("message")
                    || ln.equals("operation")) {
                label = getAttr(element, "name");
            }
            else if (ln.equals("fault")) {
                label = getAttr(element, "name");
                if (label != null 
                        && element.getAttributeNode("message") != null) {
                    label += " message: " + getAttr(element, "message");
                }
            }
            else if (ln.equals("input")
                    || ln.equals("output")) {
                label = getAttr(element, "message");
                if (element.getAttributeNode("name") != null) {
                    if (label == null) {
                        label = getAttr(element, "name");
                    }
                    else {
                        label = getAttr(element, "name") + " message: " + label;
                    }
                }
            }
            else if (ln.equals("binding")) {
                label = getAttr(element, "name");
                if (label != null) {
                    label = "binding: " + label;
                    if (getAttr(element, "type") != null) {
                        label += " type: " + getAttr(element, "type");
                    }
                }
                else {
                    label = getAttr(element, "type");
                    if (label != null) {
                        label = "binding type: " + label;
                    }
                }
            }
            else if (ln.equals("service")) {
                label = getAttr(element, "name");
                if (label != null) {
                    label = "service: " + label;
                }
            }
            else if (ln.equals("part")) {
                label = getAttr(element, "name");
                if (label != null) {
                    if (element.getAttributeNode("element") != null) {
                        label += "  << " + getAttr(element, "element") + " >>";
                    }
                    else if (element.getAttributeNode("type") != null) {
                        label += "  << " + getAttr(element, "type")+ " >>";
                    }
                }
            }
        }
        return label;
    }
    
    private static String getAttr(Element el, String name) {
        String val = el.getAttribute(name);
        if (val != null 
                && val.length() == 0) {
            val = null;
        }
        return val;
    }
    
    private static Element getFirstElement(Element root) {
        Node ch = root.getFirstChild();
        while (ch != null) {
            if (ch.getNodeType() == Node.ELEMENT_NODE 
                    && false == ch.getNodeName().endsWith("annotation")
                    && false == ch.getNodeName().endsWith("documentation")) {
                break;
            }
            ch = ch.getNextSibling();
        }
        return (Element)ch;
    }
    
    public static void mergeAnnotation(Document annDoc, Document original) {
        Element root = original.getDocumentElement();
        markElementsWithIDs(root);
        NamedNodeMap nnm = root.getAttributes();
        String sawsdlPrefix = null;
        for (int i = 0; i < nnm.getLength(); i++) {
            Attr attr = (Attr)nnm.item(i);
            if (attr.getName().startsWith("xmlns") 
                    && attr.getValue().equals(Constants.SAWSDL_NS)) {
                sawsdlPrefix = (attr.getName().equals("xmlns")) ? "" : attr.getName().substring(6);
                break;
            }
        }
        if (sawsdlPrefix == null) {
            nnm = annDoc.getDocumentElement().getAttributes();
            for (int i = 0; i < nnm.getLength(); i++) {
                Attr attr = (Attr)nnm.item(i);
                if (attr.getName().startsWith("xmlns") 
                        && attr.getValue().equals(Constants.SAWSDL_NS)) {
                    sawsdlPrefix = (attr.getName().equals("xmlns")) ? "" : attr.getName().substring(6);
                    root.setAttribute(attr.getName(), Constants.SAWSDL_NS);
                    break;
                }
            }
        }
        
        Map<String, String> id2modelRef = new HashMap<String, String>();
        Map<String, String> id2lifting = new HashMap<String, String>();
        Map<String, String> id2lowering = new HashMap<String, String>();
        NodeList allAnn = annDoc.getDocumentElement().getChildNodes();
        for(int i = 0; i < allAnn.getLength(); i++) {
            Element annEl = (Element)allAnn.item(i);
            String id = annEl.getAttribute("xID");
            String modelRef = annEl.getAttributeNS(Constants.SAWSDL_NS, "modelReference");
            String lifting = annEl.getAttributeNS(Constants.SAWSDL_NS, "liftingSchemaMapping");
            String lowering = annEl.getAttributeNS(Constants.SAWSDL_NS, "loweringSchemaMapping");
            if (modelRef.length() > 0) {
                id2modelRef.put(id, modelRef);
            }
            if (lifting.length() > 0) {
                id2lifting.put(id, lifting);
            }
            if (lowering.length() > 0) {
                id2lowering.put(id, lowering);
            }
        }
        mergeElementAnnotation(root, sawsdlPrefix, id2modelRef, id2lifting, id2lowering);
        cleanXData(root);
    }
    
    private static void mergeElementAnnotation(Element el, String sawsdlPrefix,
                                               Map<String, String> id2modelRef, 
                                               Map<String, String> id2lifting, 
                                               Map<String, String> id2lowering) {
        String id = el.getAttribute("xID");
        if (id2modelRef.containsKey(id)) {
            String oldValue = el.getAttributeNS(Constants.SAWSDL_NS, "modelReference");
            if (false == matchValues(oldValue, id2modelRef.get(id))) {
                String qName = "modelReference";
                if (sawsdlPrefix.length() > 0) {
                    qName = sawsdlPrefix + ":" + qName;
                }
                el.setAttributeNS(Constants.SAWSDL_NS, qName, id2modelRef.get(id));
            }
        }
        else {
            el.removeAttributeNS(Constants.SAWSDL_NS, "modelReference");
        }
        
        if (id2lowering.containsKey(id)) {
            String oldValue = el.getAttributeNS(Constants.SAWSDL_NS, "loweringSchemaMapping");
            if (false == matchValues(oldValue, id2lowering.get(id))) {
                String qName = "loweringSchemaMapping";
                if (sawsdlPrefix.length() > 0) {
                    qName = sawsdlPrefix + ":" + qName;
                }
                el.setAttributeNS(Constants.SAWSDL_NS, qName, id2lowering.get(id));
            }
        }
        else {
            el.removeAttributeNS(Constants.SAWSDL_NS, "loweringSchemaMapping");
        }
        
        if (id2lifting.containsKey(id)) {
            String oldValue = el.getAttributeNS(Constants.SAWSDL_NS, "liftingSchemaMapping");
            if (false == matchValues(oldValue, id2lifting.get(id))) {
                String qName = "liftingSchemaMapping";
                if (sawsdlPrefix.length() > 0) {
                    qName = sawsdlPrefix + ":" + qName;
                }
                el.setAttributeNS(Constants.SAWSDL_NS, qName, id2lifting.get(id));
            }
        }
        else {
            el.removeAttributeNS(Constants.SAWSDL_NS, "liftingSchemaMapping");
        }
        // recursively ....
        NodeList nl = el.getChildNodes();
        for(int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                mergeElementAnnotation((Element)nl.item(i), sawsdlPrefix,
                        id2modelRef, 
                        id2lifting, 
                        id2lowering);
            }
        }
    }
    
    private static boolean matchValues(String val1, String val2) {
        if (val1.equals(val2)) {
            return true;
        }
        StringTokenizer toker = new StringTokenizer(val1);
        while (toker.hasMoreTokens()) {
            if (val2.indexOf(toker.nextToken()) == -1) {
                return false;
            }
        }
        toker = new StringTokenizer(val2);
        while (toker.hasMoreTokens()) {
            if (val1.indexOf(toker.nextToken()) == -1) {
                return false;
            }
        }
        return true;
    }
    
    public static void encodeWSDL11AnnOnOperations(Element root) {
        NodeList nl = root.getElementsByTagNameNS(Constants.WSDL_NS, "operation");
        if (nl.getLength() == 0) {
            nl = root.getElementsByTagNameNS(Constants.WSDL_2_NS, "operation");
        }
        if (nl.getLength() == 0) {
            nl = root.getElementsByTagNameNS(Constants.WSDL_2_NS_OLD, "operation");
        }
        for(int i = 0; i < nl.getLength(); i++) {
            Element opElement = (Element)nl.item(i);
            NodeList extElements = opElement.getElementsByTagNameNS(Constants.SAWSDL_NS, "attrExtensions");
            if (extElements.getLength() == 0) {
                continue;
            }
            Element extElement = ((Element)extElements.item(0));
            String sawsdlPrefix = extElement.getTagName();
            if (sawsdlPrefix.equals("attrExtensions")) {
                sawsdlPrefix = "";
            }
            else {
                sawsdlPrefix = sawsdlPrefix.substring(0, sawsdlPrefix.length() - 14);
            }
            if (extElement.getAttributeNodeNS(Constants.SAWSDL_NS, "modelReference") != null) {
                opElement.setAttributeNS(Constants.SAWSDL_NS, 
                        sawsdlPrefix + "modelReference", 
                        extElement.getAttributeNS(Constants.SAWSDL_NS, "modelReference"));
            }
            if (extElement.getAttributeNodeNS(Constants.SAWSDL_NS, "loweringSchemaMapping") != null) {
                opElement.setAttributeNS(Constants.SAWSDL_NS, 
                        sawsdlPrefix + "loweringSchemaMapping", 
                        extElement.getAttributeNS(Constants.SAWSDL_NS, "loweringSchemaMapping"));
            }
            if (extElement.getAttributeNodeNS(Constants.SAWSDL_NS, "liftingSchemaMapping") != null) {
                opElement.setAttributeNS(Constants.SAWSDL_NS, 
                        sawsdlPrefix + "liftingSchemaMapping", 
                        extElement.getAttributeNS(Constants.SAWSDL_NS, "liftingSchemaMapping"));
            }
        }
    }

    public static void decodeWSDL11AnnOnOperations(Element root) {
        NodeList nl = root.getElementsByTagNameNS(Constants.WSDL_NS, "operation");
        if (nl.getLength() == 0) {
            nl = root.getElementsByTagNameNS(Constants.WSDL_2_NS, "operation");
        }
        if (nl.getLength() == 0) {
            nl = root.getElementsByTagNameNS(Constants.WSDL_2_NS_OLD, "operation");
        }
        for(int i = 0; i < nl.getLength(); i++) {
            Element opElement = (Element)nl.item(i);
            NodeList extElements = opElement.getElementsByTagNameNS(Constants.SAWSDL_NS, "attrExtensions");
            Element extElement = null;
            if (extElements.getLength() > 0) {
                extElement = ((Element)extElements.item(0));
            }
            
            Attr annAttr = opElement.getAttributeNodeNS(Constants.SAWSDL_NS, "modelReference");
            if (annAttr == null) {
                if (extElement != null) {
                    extElement.removeAttributeNS(Constants.SAWSDL_NS, "modelReference");
                }
            }
            else {
                if (extElement == null) {
                    extElement = opElement.getOwnerDocument().createElementNS(
                            Constants.SAWSDL_NS, 
                            (annAttr.getPrefix() == null || annAttr.getPrefix().length() == 0)? 
                                    "attrExtensions"
                                    : annAttr.getPrefix() + ":attrExtensions");
                    opElement.appendChild(extElement);
                }
                extElement.setAttributeNS(Constants.SAWSDL_NS, annAttr.getName(), annAttr.getValue());
                opElement.removeAttributeNode(annAttr);
            }
            // lifting
            annAttr = opElement.getAttributeNodeNS(Constants.SAWSDL_NS, "liftingSchemaMapping");
            if (annAttr == null) {
                if (extElement != null) {
                    extElement.removeAttributeNS(Constants.SAWSDL_NS, "liftingSchemaMapping");
                }
            }
            else {
                if (extElement == null) {
                    extElement = opElement.getOwnerDocument().createElementNS(
                            Constants.SAWSDL_NS, 
                            (annAttr.getPrefix() == null || annAttr.getPrefix().length() == 0)? 
                                    "attrExtensions"
                                    : annAttr.getPrefix() + ":attrExtensions");
                    opElement.appendChild(extElement);
                }
                extElement.setAttributeNS(Constants.SAWSDL_NS, annAttr.getName(), annAttr.getValue());
                opElement.removeAttributeNode(annAttr);
            }
            ///lowering
            annAttr = opElement.getAttributeNodeNS(Constants.SAWSDL_NS, "loweringSchemaMapping");
            if (annAttr == null) {
                if (extElement != null) {
                    extElement.removeAttributeNS(Constants.SAWSDL_NS, "loweringSchemaMapping");
                }
            }
            else {
                if (extElement == null) {
                    extElement = opElement.getOwnerDocument().createElementNS(
                            Constants.SAWSDL_NS, 
                            (annAttr.getPrefix() == null || annAttr.getPrefix().length() == 0)? 
                                    "attrExtensions"
                                    : annAttr.getPrefix() + ":attrExtensions");
                    opElement.appendChild(extElement);
                }
                extElement.setAttributeNS(Constants.SAWSDL_NS, annAttr.getName(), annAttr.getValue());
                opElement.removeAttributeNode(annAttr);
            }
            
            if (extElement != null 
                    && extElement.getAttributes().getLength() == 0) {
                opElement.removeChild(extElement);
            }
        }
    }

}
