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

import org.soa4all.dashboard.gwt.module.wsmolite.client.WsmoLiteDataServiceAsync;
import org.soa4all.dashboard.gwt.module.wsmolite.client.editor.ResourceSelectionDialog.DocumentNode;
import org.soa4all.dashboard.gwt.module.wsmolite.client.editor.onto.model.ModelNode;
import org.soa4all.dashboard.gwt.module.wsmolite.client.view.WSMOLiteModuleView;

import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.Style.*;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.*;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.layout.*;
import com.extjs.gxt.ui.client.widget.tree.TreeItem;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;


public class ActionHandler {

    public static void previewDocument(WsmoLiteDataServiceAsync processor, final TreeItem selected, String baseURL) {
        try {
            processor.getRepositoryFileAsString(baseURL, 
                    selected.getParentItem().getModel().toString(), 
                    selected.getModel().toString(), new AsyncCallback<String>() {
                public void onFailure(Throwable caught) {
                    MessageBox.alert("Error", caught.getMessage(), null);
                }
                public void onSuccess(String result) {
                    final Dialog simple = new Dialog();   
                    simple.setHeading(selected.getModel().toString());   
                    simple.setButtons(Dialog.OK);
                    simple.setBodyStyle("no-wrap");
                    simple.addText( GUIHelper.formatAsHtml(result));   
                    simple.setScrollMode(Scroll.AUTO);   
                    simple.setHideOnButtonClick(true);
                    simple.setSize(400, 300);
                    simple.show();
                }

            });
        }
        catch(Exception exc) {
            MessageBox.alert("Error", exc.getMessage(), null);
        }
    }
    
    public static void deleteResource(WsmoLiteDataServiceAsync processor, 
    								DocumentNode node,
    								AsyncCallback<Boolean> callBack) {
    	try {
    	    processor.deleteRepositoryFile(node.getUrl(), callBack);
    	}
    	catch(Exception ex) {
    		MessageBox.alert("Error", ex.getMessage(), null);
    	}
    }
    
    public static void showQuickFindDialog(ListStore<ModelNode> data, final WSMOLiteModuleView view) {
        final Dialog simple = new Dialog();   
        simple.setHeading("Quick Find");   
        simple.setButtons(Dialog.OKCANCEL);
        simple.getButtonBar().getButtonById("ok").setText("Select");
        simple.setBodyStyle("no-wrap");

        simple.setLayout(new RowLayout(Orientation.HORIZONTAL));
        simple.add(new Label("Search Term:"), new RowData(70, 20, new Margins(25, 0, 0, 5)));

        final ComboBox<ModelNode> comboField = new ComboBox<ModelNode>();
        comboField.setWidth(250);   
        comboField.reset();
        comboField.setStore(data);   
        comboField.setTemplate(getTemplate());   
        comboField.setTypeAhead(true);   
        comboField.setTriggerAction(TriggerAction.ALL);
        comboField.setDisplayField("fnd");
        comboField.setDeferHeight(true);
        
        simple.add(comboField, new RowData(1, 1, new Margins(23, 10, 0, 10)));
        simple.addListener(Events.BeforeHide, new Listener<ComponentEvent>() {
            public void handleEvent(ComponentEvent be) {
                if (simple.getButtonPressed() != null
                        && simple.getButtonPressed().getText().equalsIgnoreCase("select")
                        && comboField.getSelection().size() > 0) {
                    view.selectOntologyNode(comboField.getSelection().get(0));
                }
            }
        }); 
        simple.setHideOnButtonClick(true);
        simple.setSize(400, 120);
        simple.show();
        comboField.focus();
    }
    
    private static native String getTemplate() /*-{  
    return  [  
    '<tpl for=".">',  
    '<div class="x-combo-list-item" qtip="{uri}" qtitle="uri"> {fnd} </div>',  
    '</tpl>'  
    ].join("");  
    }-*/; 
}
