/**
 Copyright (c) 2009, Ontotext AD
 
 This library is free software; you can redistribute it and/or modify it under
 the terms of the GNU Lesser General Public License as published by the Free
 Software Foundation; either version 2.1 of the License, or (at your option)
 any later version.
 This library is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 details.
 You should have received a copy of the GNU Lesser General Public License along
 with this library; if not, write to the Free Software Foundation, Inc.,
 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 
 */

package org.soa4all.dashboard.gwt.module.wsmolite.client.editor;


import org.soa4all.dashboard.gwt.module.wsmolite.client.Constants;
import org.soa4all.dashboard.gwt.module.wsmolite.client.WsmoLiteDataServiceAsync;
import org.soa4all.dashboard.gwt.module.wsmolite.client.editor.model.*;
import org.soa4all.dashboard.gwt.module.wsmolite.client.editor.onto.model.*;

import com.extjs.gxt.ui.client.data.*;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.widget.ComponentHelper;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.extjs.gxt.ui.client.widget.tree.TreeItem;




public class GUIHelper {
    
    public static void addLiftingSchema(final Tree owner, final TreeItem parent) {
        final MessageBox box = MessageBox.prompt("Lifting Schema", "Please provide lifting schema URL:");   
        box.addCallback(new Listener<MessageBoxEvent>() {   
            public void handleEvent(MessageBoxEvent be) {
                if (be.value == null || be.value.trim().length() == 0) {
                    return;
                }
                createMappingAnnotation(owner, parent, true, be.value);
            }   
        });     
    }
    public static void addLoweringSchema(final Tree owner, final TreeItem parent) {
        final MessageBox box = MessageBox.prompt("Lowering Schema", "Please provide lowering schema URL:");   
        box.addCallback(new Listener<MessageBoxEvent>() {   
            public void handleEvent(MessageBoxEvent be) {
                if (be.value == null || be.value.trim().length() == 0) {
                    return;
                }
                createMappingAnnotation(owner, parent, false, be.value);
            }   
        });            
    }
    public static void selectLiftingSchema(final Tree owner, final TreeItem parent, WsmoLiteDataServiceAsync processor) {
        ResourceSelectionDialog dialog = new ResourceSelectionDialog(
                "Select Lifting Schema", 
                processor,
                new ResourceSelectionDialog.SelectedResourceCallback() {
            public void processSelection(String url) {
                createMappingAnnotation(owner, parent, true, url);
                
            }
        });
        dialog.show();
    }
    public static void selectLoweringSchema(final Tree owner, final TreeItem parent, WsmoLiteDataServiceAsync processor) {
        ResourceSelectionDialog dialog = new ResourceSelectionDialog(
                "Select Lowering Schema", 
                processor,
                new ResourceSelectionDialog.SelectedResourceCallback() {
            public void processSelection(String url) {
                createMappingAnnotation(owner, parent, false, url);
                
            }
        });
        dialog.show();       
    }
    
    @SuppressWarnings("unchecked")
    public static void removeAnnotation(final TreeItem remNode) {
        TreeModel<TreeModel<?>> parentModel = ((AnnotationNode)remNode.getModel()).getParent();
        remNode.getParentItem().remove(remNode);
        parentModel.remove((AnnotationNode)remNode.getModel());
    }
    
    public static void createMappingAnnotation(Tree owner, TreeItem parent, boolean isLifting, String ref) {

        String attrName = (isLifting) ? "liftingSchemaMapping" : "loweringSchemaMapping";
        String sawsdlNS = ((ElementNode)parent.getModel()).getSawsdlPrefix();
        if (sawsdlNS == null) {
            sawsdlNS = "sawsdl";
        }
        if (sawsdlNS.length() > 0) {
            attrName = sawsdlNS + ":" + attrName;
        }
        AnnotationNode annNode = new AnnotationNode(attrName, ref);
        TreeItem newChild = new TreeItem(annNode.toString());
        ComponentHelper.setModel(newChild, annNode);
        newChild.setIconStyle((isLifting) ? "icon-ann-lift" : "icon-ann-low");
        parent.add(newChild);
        ((ElementNode)parent.getModel()).add(annNode);
        owner.setSelectedItem(newChild);
    }

    public static boolean checkDropTarget(ModelData model) {
        if (false == model instanceof ElementNode) {
            return false;
        }
        String ns = ((ElementNode)model).getNamespace();
        String tagname = ((ElementNode)model).getTagname();
        return matchElement(Constants.XSD_NS, "simpleType", ns, tagname)
            || matchElement(Constants.XSD_NS, "complexType", ns, tagname)
            || matchElement(Constants.XSD_NS, "element", ns, tagname)
            || matchElement(Constants.XSD_NS, "attribute", ns, tagname)

            || matchElement(Constants.WSDL_NS, "portType", ns, tagname)
            || matchElement(Constants.WSDL_NS, "input", ns, tagname)
            || matchElement(Constants.WSDL_NS, "output", ns, tagname)
            || matchElement(Constants.WSDL_NS, "fault", ns, tagname)
            || matchElement(Constants.WSDL_NS, "operation",  ns, tagname)
            || matchElement(Constants.WSDL_NS, "service",  ns, tagname)
            || matchElement(Constants.WSDL_NS, "part",  ns, tagname)

            || matchElement(Constants.WSDL_2_NS, "interface", ns, tagname)
            || matchElement(Constants.WSDL_2_NS, "input", ns, tagname)
            || matchElement(Constants.WSDL_2_NS, "output", ns, tagname)
            || matchElement(Constants.WSDL_2_NS, "fault", ns, tagname)
            || matchElement(Constants.WSDL_2_NS, "infault", ns, tagname)
            || matchElement(Constants.WSDL_2_NS, "outfault", ns, tagname)
            || matchElement(Constants.WSDL_2_NS, "service",  ns, tagname)
            || matchElement(Constants.WSDL_2_NS, "operation",  ns, tagname)

            || matchElement(Constants.WSDL_2_NS_OLD, "interface", ns, tagname)
            || matchElement(Constants.WSDL_2_NS_OLD, "input", ns, tagname)
            || matchElement(Constants.WSDL_2_NS_OLD, "output", ns, tagname)
            || matchElement(Constants.WSDL_2_NS_OLD, "fault", ns, tagname)
            || matchElement(Constants.WSDL_2_NS_OLD, "infault", ns, tagname)
            || matchElement(Constants.WSDL_2_NS_OLD, "outfault", ns, tagname)
            || matchElement(Constants.WSDL_2_NS_OLD, "service",  ns, tagname)
            || matchElement(Constants.WSDL_2_NS_OLD, "operation",  ns, tagname);
    }
     
    public static void setServiceModelIcons(TreeItem root) {
        
        if (root.getModel() instanceof OntologyNode) {
            root.setIconStyle("icon-wsmo-onto");
        }
        else if (root.getModel() instanceof ConceptNode) {
            root.setIconStyle("icon-wsmo-concept");
        }
        else if (root.getModel() instanceof InstanceNode) {
            root.setIconStyle("icon-wsmo-instance");
        }
        else if (root.getModel() instanceof AttributeNode) {
            root.setIconStyle("icon-wsmo-attr");
        }
        else if (root.getModel() instanceof AxiomNode) {
            root.setIconStyle("icon-wsmo-axiom");
        }
        else if (root.getModel() instanceof AttributeValueNode) {
            root.setIconStyle("icon-wsmo-attr-value");
        }
        else if (root.getModel() instanceof RelationNode) {
            root.setIconStyle("icon-wsmo-relation");
        }
        else if (root.getModel() instanceof RelationInstanceNode) {
            root.setIconStyle("icon-wsmo-rel-inst");
        }
        // the service model ...
        else if (root.getModel() instanceof TextNode) {
            root.setIconStyle("icon-txt");
        }
        else if (root.getModel() instanceof ElementNode) {
            ElementNode elNode = (ElementNode)root.getModel();
            String tag = elNode.getTagname();
            String ns = elNode.getNamespace();
            if (elNode.getTooltip() != null) {
                root.setTitle(elNode.getTooltip());
            }
            else {
                root.setTitle(tag);
            }

            if (matchElement(Constants.WSDL_NS, "message", ns, tag)) {
                root.setIconStyle("icon-message");
            }
            else if (matchElement(Constants.WSDL_NS, "part", ns, tag)) {
                root.setIconStyle("icon-part");
            }
            else if (matchElement(Constants.WSDL_NS, "types", ns, tag)
                    || matchElement(Constants.WSDL_2_NS_OLD, "types", ns, tag)
                    || matchElement(Constants.WSDL_2_NS, "types", ns, tag)) {
                root.setIconStyle("icon-types");
            }

            else if (matchElement(Constants.XSD_NS, "attribute", ns, tag)) {
                root.setIconStyle("icon-attr");
            }            
            else if (matchElement(Constants.XSD_NS, "element", ns, tag)) {
                root.setIconStyle("icon-element");
            }            
            else if (matchElement(Constants.XSD_NS, "complexType", ns, tag)) {
                root.setIconStyle("icon-complex-type");
            }            
            else if (matchElement(Constants.WSDL_NS, "definitions", ns, tag)
                    || matchElement(Constants.WSDL_2_NS_OLD, "description", ns, tag)
                    || matchElement(Constants.WSDL_2_NS, "description", ns, tag)) {
                root.setIconStyle("icon-wsdl");
            }
            else if (matchElement(Constants.WSDL_NS, "service", ns, tag)
                    || matchElement(Constants.WSDL_2_NS_OLD, "service", ns, tag)
                    || matchElement(Constants.WSDL_2_NS, "service", ns, tag)) {
                root.setIconStyle("icon-service");
            }
            else if (matchElement(Constants.WSDL_NS, "binding", ns, tag)
                    || matchElement(Constants.WSDL_2_NS_OLD, "binding", ns, tag)
                    || matchElement(Constants.WSDL_2_NS, "binding", ns, tag)) {
                root.setIconStyle("icon-binding");
            }
            else if (matchElement(Constants.WSDL_NS, "fault", ns, tag)
                    || matchElement(Constants.WSDL_2_NS, "fault", ns, tag)
                    || matchElement(Constants.WSDL_2_NS, "infault", ns, tag)
                    || matchElement(Constants.WSDL_2_NS, "outfault", ns, tag)
                    || matchElement(Constants.WSDL_2_NS_OLD, "fault", ns, tag)
                    || matchElement(Constants.WSDL_2_NS_OLD, "infault", ns, tag)
                    || matchElement(Constants.WSDL_2_NS_OLD, "outfault", ns, tag)) {
                root.setIconStyle("icon-fault");
            }
            else if (matchElement(Constants.WSDL_NS, "input", ns, tag)
                    || matchElement(Constants.WSDL_2_NS, "input", ns, tag)
                    || matchElement(Constants.WSDL_2_NS_OLD, "input", ns, tag)) {
                root.setIconStyle("icon-input");
            }           
            else if (matchElement(Constants.WSDL_NS, "portType", ns, tag)
                    || matchElement(Constants.WSDL_2_NS, "interface", ns, tag)
                    || matchElement(Constants.WSDL_2_NS_OLD, "interface", ns, tag)) {
                root.setIconStyle("icon-interface");
            }

            else if (matchElement(Constants.WSDL_NS, "operation", ns, tag)
                    || matchElement(Constants.WSDL_2_NS_OLD, "operation", ns, tag)
                    || matchElement(Constants.WSDL_2_NS, "operation", ns, tag)) {
                root.setIconStyle("icon-operation");
            }

            else if (matchElement(Constants.WSDL_NS, "output", ns, tag)
                    || matchElement(Constants.WSDL_2_NS, "output", ns, tag)
                    || matchElement(Constants.WSDL_2_NS_OLD, "output", ns, tag)) {
                root.setIconStyle("icon-output");
            }
            else if (matchElement(Constants.XSD_NS, "schema", ns, tag)) {
                root.setIconStyle("icon-schema");
            }
            else if (matchElement(Constants.XSD_NS, "import", ns, tag)) {
                root.setIconStyle("icon-import-schema");
            }
            
            else if (matchElement(Constants.XSD_NS, "simpleType", ns, tag)) {
                root.setIconStyle("icon-simple-type");
            }     
            else if (root.getText() != null) {
                root.setIconStyle("icon-tag");
            }
        }
        else if (root.getModel() instanceof AnnotationNode) {
            AnnotationNode annNode = (AnnotationNode)root.getModel();
            if (matchElement(Constants.SAWSDL_NS, "modelReference", Constants.SAWSDL_NS, annNode.getAttr())) {
                root.setIconStyle("icon-ann-attr");
            }
            else if (matchElement(Constants.SAWSDL_NS, "liftingSchemaMapping", Constants.SAWSDL_NS, annNode.getAttr())) {
                root.setIconStyle("icon-ann-lift");
            }
            else if (matchElement(Constants.SAWSDL_NS, "loweringSchemaMapping", Constants.SAWSDL_NS, annNode.getAttr())) {
                root.setIconStyle("icon-ann-low");
            }     
        }
        else if (root.getModel() != null 
        		&& root.getModel().get("icon") != null) {
        	root.setIconStyle((String)root.getModel().get("icon"));
        }

        for(TreeItem child : root.getItems()) {
            setServiceModelIcons(child);//, wsdlNS, sawsdlNS, xsdNS);
        }
    }
    
    public static void setOntologyTooltips(TreeItem ti) {
        ModelNode model = (ModelNode)ti.getModel();
        if (model.getTooltip() != null) {
            ti.setTitle(model.getTooltip());
        }
        for(TreeItem child : ti.getItems()) {
            setOntologyTooltips(child);
        }
    }
    
    public static String getPreviewText(ModelData model) {
        if (model instanceof AnnotationNode) {
            return "<nobr><i>&nbsp;&nbsp;&nbsp;&nbsp;" 
                    + ((AnnotationNode)model).getAttr() + "</i> = \"<b>" 
                    + ((AnnotationNode)model).getValue() + "</b>\"</nobr>";
        }
        if (model instanceof ElementNode) {
            ElementNode elNode = (ElementNode)model;
            String html = "<nobr>&nbsp;&lt;" + elNode.getTagname();
            if (elNode.getAttrData() != null) {
                html += elNode.getAttrData();
            }
            html += "</nobr>";
            for (TreeModel<?> child : ((ElementNode)model).getChildren()) {
                if (child instanceof AnnotationNode) {
                    html += "<br>" + getPreviewText(child);
                }
            }
            return html + "&gt&nbsp;";
        }
        return model.toString();
    }
    
    public static String formatAsHtml(String raw) {
        StringBuffer result = new StringBuffer(raw.length());
        char ch;
        for (int i = 0; i < raw.length(); i++) {
            ch = raw.charAt(i);
            if (ch == '<') {
                result.append("&lt;");
            }
            else if (ch == '\n') {
                result.append("<br>");
            }
            else if (ch == '\r') {
                continue;
            }
            else if (ch == '\t') {
                result.append("&nbsp;&nbsp;&nbsp;&nbsp;");
            }
            else if (ch == ' ') {
                result.append("&nbsp;");
            }
            else if (ch == '-') {
                result.append("&ndash;");
            }
            else if (ch < 32) {
                result.append("?");
            }
            else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    private static boolean matchElement(String expectedNS, String expectedLocalName, String ns, String local) {
        return expectedNS.equals(ns) 
                && (local.equals(expectedLocalName) 
                        || local.endsWith(":" + expectedLocalName));
    }
    
    

}
