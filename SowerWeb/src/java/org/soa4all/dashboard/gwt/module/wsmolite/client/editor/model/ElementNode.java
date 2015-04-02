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


import org.soa4all.dashboard.gwt.module.wsmolite.client.Constants;

import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.google.gwt.xml.client.*;

@SuppressWarnings({ "serial", "unchecked" })
public class ElementNode extends BaseTreeModel<TreeModel> {

    private String renamedSawsdlPrefix = null;
    
    public ElementNode(Element element) {
        set("tag", element.getNodeName());
        set("ns", element.getNamespaceURI());
        if (element.getAttributeNode("xID") != null) {
            set("xID", element.getAttribute("xID"));
            element.removeAttribute("xID");
        }
        if (element.getAttributeNode("xTooltip") != null) {
            set("xTooltip", element.getAttribute("xTooltip"));
            element.removeAttribute("xTooltip");
        }
        if (element.getAttributeNode("xLab") != null) {
            set("lab", element.getAttribute("xLab"));
            element.removeAttribute("xLab");
        }
        parseAttributes(element.getAttributes());
    }
    
    public String toString() {
        if (get("lab") != null) {
            return get("lab");
        }
        String attrData = get("attrs");
        if (attrData != null) {
            return get("tag") + attrData;
        }
        else {
            return get("tag");
        }
    }
    
    public String getNamespace() {
        return get("ns");
    }
    public String getTagname() {
        return get("tag");
    }

    public String getXID() {
        return get("xID");
    }
    public String getTooltip() {
        return get("xTooltip");
    }
    public String getAttrData() {
        return get("attrs");
    }    
    public String getSawsdlPrefix() {
        if (renamedSawsdlPrefix != null) {
            return renamedSawsdlPrefix;
        }
        if (getParent() instanceof ElementNode) {
            return ((ElementNode)getParent()).getSawsdlPrefix();
        }
        if (getParent() instanceof DocumentNode) {
            return ((DocumentNode)getParent()).getSawsdlPrefix();
        }
        return "sawsdl";
    }    
    
    private void parseAttributes(NamedNodeMap nnm) {
        String allAttData = "";
        for(int i = 0; i < nnm.getLength(); i++) {
            Attr attr = (Attr)nnm.item(i);
            if (attr.getName().startsWith("x")) {
                continue;
            }
            if (isAnnotation(attr)) {
                String[] parts = attr.getValue().split(" |\\t|\\n|\\r");
                for(int k = 0; k < parts.length; k++) {
                    if (parts[k].length() > 0) {
                        AnnotationNode annNode = new AnnotationNode(attr.getName(), parts[k]);
                        add(annNode);
                    }
                }
            }
            else {
                allAttData += " " + attr.getName() + "=\"" + attr.getValue() + "\"";
                if (attr.getName().startsWith("xmlns") 
                        && attr.getValue().equals(Constants.SAWSDL_NS)) {
                    renamedSawsdlPrefix = (attr.getName().equals("xmlns")) ? "" : attr.getName().substring(6);
                }
            }
        }
        if (allAttData.length() > 0) {
            set("attrs", allAttData);
        }
    }
    
    public static boolean isAnnotation(Attr attr) {
        return Constants.SAWSDL_NS.equals(attr.getNamespaceURI())
             && (attr.getName().endsWith("modelReference") 
                || attr.getName().endsWith("loweringSchemaMapping")
                || attr.getName().endsWith("liftingSchemaMapping"));
    }
}
