/*
 * Copyright 2009, 2010 Ontotext AD
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.soa4all.dashboard.gwt.module.wsmolite.client.editor.model;

import java.util.HashMap;
import java.util.Map;

import org.soa4all.dashboard.gwt.module.wsmolite.client.Constants;

import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.google.gwt.xml.client.*;
import com.google.gwt.xml.client.impl.DOMParseException;



public class ModelBuilder {

    public static DocumentNode buildModel(String xmlData) throws DOMParseException {
        DocumentNode root = new DocumentNode();
        
        Document domDoc = XMLParser.parse(xmlData);
        XMLParser.removeWhitespace(domDoc);
        String sawsdlPrefix = findSAWSDLPrefix(domDoc.getDocumentElement());
        if (sawsdlPrefix == null) {
            sawsdlPrefix = "sawsdl";
            if (domDoc.getDocumentElement().getAttributeNode("xmlns:sawsdl") != null) {
                int i = 1;
                while(domDoc.getDocumentElement().getAttributeNode("xmlns:" + sawsdlPrefix + String.valueOf(i)) != null) {
                    i++;
                }
                sawsdlPrefix += String.valueOf(i);
            }
            domDoc.getDocumentElement().setAttribute("xmlns:" + sawsdlPrefix, Constants.SAWSDL_NS);
        }
        root.setSawsdlPrefix(sawsdlPrefix);
        BaseTreeModel<TreeModel> childNode = processNode(domDoc.getDocumentElement());
        if (childNode != null) {
            root.add(childNode);
        }
        return root;
    }
    
    public static BaseTreeModel<TreeModel> processNode(Node root) {
        if (root.getNodeType() == Node.TEXT_NODE) {
            if (root.getNodeValue().trim().length() > 0) {
                return new TextNode(root.getNodeValue());
            }
        }
        else if (root.getNodeType() == Node.ELEMENT_NODE) {
            ElementNode elNode = new ElementNode((Element)root);
            NodeList childs = root.getChildNodes();
            if (childs != null) {
                for (int i = 0; i < childs.getLength(); i++) {
                    BaseTreeModel<?> childNode = processNode(childs.item(i));
                    if (childNode != null) {
                        elNode.add(childNode);
                    }
                }
            }
            return elNode;
        }
        return null;
    }
    

    public static String serializeServiceModel(DocumentNode model) {
        if (model == null) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("<annotation src=\"")
              .append(model.getSourceUrl())
          //    .append("/files/")
           //   .append(model.getFileName())
              .append("\" xmlns:")
              .append(model.getSawsdlPrefix())
              .append("=\"")
              .append(Constants.SAWSDL_NS)
              .append("\">");
        for (int i = 0; i < model.getChildCount(); i++) {
            if (model.getChild(i) instanceof ElementNode) {
                serializeServiceModel((ElementNode)model.getChild(i), buffer);
            }
        }
        buffer.append("</annotation>");
        return buffer.toString();
    }

    public static void serializeServiceModel(ElementNode elementNode, StringBuffer buffer) {
        
        serializeAnnotations(elementNode, buffer);
        for (int i = 0; i < elementNode.getChildCount(); i++) {
            if (elementNode.getChild(i) instanceof ElementNode) {
                serializeServiceModel((ElementNode)elementNode.getChild(i), buffer);
            }
        }        
    }
    
    private static void serializeAnnotations(ElementNode parent, StringBuffer buffer) {
        
        Map<String, String> attrMap = new HashMap<String, String>();
        for(int i = 0; i < parent.getChildCount(); i++) {
            TreeModel<?> child = parent.getChild(i);
            if (child instanceof AnnotationNode) {
                String attrName = ((AnnotationNode)child).getAttr();
                if (attrMap.containsKey(attrName)) {
                    attrMap.put(attrName, attrMap.get(attrName) + " " + ((AnnotationNode)child).getValue());
                }
                else {
                    attrMap.put(attrName, ((AnnotationNode)child).getValue());
                }
            }
        }
        if (attrMap.size() > 0) {
            buffer.append("<ann ");
            // setting the ID
            buffer.append("xID=\"").append(parent.getXID()).append('\"').append(' ');
            for (String attrName : attrMap.keySet()) {
                buffer.append(attrName).append("=\"").append(attrMap.get(attrName)).append("\" ");
            }
            buffer.append("/>");
        }
    }
    
    public static String findSAWSDLPrefix(Element root) {
        NamedNodeMap nnm = root.getAttributes();
        if (nnm == null) {
            return null;
        }
        for(int i = 0; i < nnm.getLength(); i++) {
            Attr attr = (Attr)nnm.item(i);
            if (false == attr.getName().startsWith("xmlns")) {
                continue;
            }
            if (attr.getValue().equals(Constants.SAWSDL_NS)) {
                return (attr.getName().equals("xmlns")) ? "" : attr.getName().substring(6);
            }
        }
        return null;
    }
}
