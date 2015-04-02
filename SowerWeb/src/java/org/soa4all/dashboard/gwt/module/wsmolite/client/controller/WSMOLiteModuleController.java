/**
 * Copyright (c) 2009, Ontotext AD
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General Public License along with this library; if not, write
 * to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 *
 */
package org.soa4all.dashboard.gwt.module.wsmolite.client.controller;

import org.soa4all.dashboard.gwt.core.client.util.CoreDashboardURLInfo;
import org.soa4all.dashboard.gwt.core.client.util.URLParameterConstants;
import org.soa4all.dashboard.gwt.core.client.util.URLUtils;
import org.soa4all.dashboard.gwt.core.shared.client.controller.AbstractModuleController;
import org.soa4all.dashboard.gwt.core.shared.client.controller.DashboardDesktopEventTypes;
import org.soa4all.dashboard.gwt.core.shared.client.model.domain.Module;
import org.soa4all.dashboard.gwt.module.wsmolite.client.WSMOLiteModule;
import org.soa4all.dashboard.gwt.module.wsmolite.client.editor.GUIHelper;
import org.soa4all.dashboard.gwt.module.wsmolite.client.editor.ResourceSelectionDialog;
import org.soa4all.dashboard.gwt.module.wsmolite.client.editor.ResourceUploadDialog;
import org.soa4all.dashboard.gwt.module.wsmolite.client.editor.model.DocumentNode;
import org.soa4all.dashboard.gwt.module.wsmolite.client.editor.model.ModelBuilder;
import org.soa4all.dashboard.gwt.module.wsmolite.client.editor.onto.model.OntologyNode;
import org.soa4all.dashboard.gwt.module.wsmolite.client.view.IServeExportDialog;
import org.soa4all.dashboard.gwt.module.wsmolite.client.view.WSMOLiteModuleView;

import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.extjs.gxt.ui.client.widget.tree.TreeItem;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import java.util.List;
import org.w3c.dom.Document;

public class WSMOLiteModuleController extends AbstractModuleController {

    private WSMOLiteModuleView managedModuleView;
    private DocumentNode dataModel;
    private PopupPanel progressPanel;

    public WSMOLiteModuleController(Module module) {
        super(module);

        registerEventTypes(WSMOLiteModuleEventTypes.MENU_ABOUT);
        registerEventTypes(WSMOLiteModuleEventTypes.MENU_OPEN_SERVICE);
        registerEventTypes(WSMOLiteModuleEventTypes.MENU_OPEN_SERVICE_FROM_URL);
        registerEventTypes(WSMOLiteModuleEventTypes.MENU_OPEN_ONTOLOGY);
        registerEventTypes(WSMOLiteModuleEventTypes.MENU_OPEN_ONTOLOGY_FROM_URL);
        registerEventTypes(WSMOLiteModuleEventTypes.MENU_SAVE_SERVICE);
        registerEventTypes(WSMOLiteModuleEventTypes.MENU_CLOSE);

        registerEventTypes(WSMOLiteModuleEventTypes.MENU_UPLOAD_RESOURCE);
        registerEventTypes(WSMOLiteModuleEventTypes.MENU_ISERVE_EXPORT);

        registerEventTypes(WSMOLiteModuleEventTypes.ACTION_SELECT_LOWERING);
        registerEventTypes(WSMOLiteModuleEventTypes.ACTION_SELECT_LIFTING);

        registerEventTypes(WSMOLiteModuleEventTypes.NOT_IMPLEMENTED);

        registerEventTypes(WSMOLiteModuleEventTypes.MENU_DASHBOARD_INFO);
    }

    public WSMOLiteModuleView getManagedModuleView() {
        return managedModuleView;
    }

    @SuppressWarnings("unchecked")
    public void handleEvent(AppEvent event) {
        switch (event.type) {
            case WSMOLiteModuleEventTypes.MENU_OPEN_SERVICE:
                ResourceSelectionDialog dialog = new ResourceSelectionDialog(
                        "Open Service Description",
                        ((WSMOLiteModule) getModule()).getDataServiceProxy(),
                        new ResourceSelectionDialog.SelectedResourceCallback() {
                            public void processSelection(String url) {
                                showProgressDialog("Opening service description ...");
                                loadService(url);
                            }
                        });
                dialog.show();
                break;
            case WSMOLiteModuleEventTypes.MENU_OPEN_SERVICE_FROM_URL:
                showOntoURLDialog("Service URL");
                break;
            case WSMOLiteModuleEventTypes.MENU_OPEN_ONTOLOGY:
                dialog = new ResourceSelectionDialog(
                        "Open Ontology",
                        ((WSMOLiteModule) getModule()).getDataServiceProxy(),
                        new ResourceSelectionDialog.SelectedResourceCallback() {
                            public void processSelection(String url) {
                                showProgressDialog("Opening ontology ...");
                                loadOntology(url);
                            }
                        });
                dialog.show();
                break;
            case WSMOLiteModuleEventTypes.MENU_OPEN_ONTOLOGY_FROM_URL:
                showOntoURLDialog("Ontology URL");
                break;
            case WSMOLiteModuleEventTypes.MENU_SAVE_SERVICE:
                doSaveEditorContent();
                break;
            case WSMOLiteModuleEventTypes.MENU_ISERVE_EXPORT:
                if (dataModel == null) {
                    MessageBox.alert("Warning", "Open a service description first!", null);
                    return;
                }
                new IServeExportDialog(
                        ((WSMOLiteModule) getModule()).getDataServiceProxy(),
                        ModelBuilder.serializeServiceModel(dataModel),
                        this).show();
                break;
            case WSMOLiteModuleEventTypes.MENU_CLOSE:
                dataModel = null;
                getManagedModuleView().closeService();
                break;
            case WSMOLiteModuleEventTypes.MENU_ABOUT:
                MessageBox.info("WSMO-Lite Editor", "<b>WSMO-Lite Editor</b><br>version 0.5<br><br>Developed under EU project SOA4ALL (FP7-215219)<br><br>Copyright (c) 2010, Ontotext AD", null);
                break;
            case WSMOLiteModuleEventTypes.MENU_DASHBOARD_INFO:
                listDashboardConf();
                break;
            case WSMOLiteModuleEventTypes.ACTION_SELECT_LOWERING:
                GUIHelper.selectLoweringSchema((Tree) event.getData("tree"),
                        (TreeItem) event.getData("item"),
                        ((WSMOLiteModule) getModule()).getDataServiceProxy());
                break;
            case WSMOLiteModuleEventTypes.ACTION_SELECT_LIFTING:
                GUIHelper.selectLiftingSchema((Tree) event.getData("tree"),
                        (TreeItem) event.getData("item"),
                        ((WSMOLiteModule) getModule()).getDataServiceProxy());
                break;
            case DashboardDesktopEventTypes.MODULE_DISPLAY_REQUEST:
                handleDisplayRequestEvent(event);
                break;
            case WSMOLiteModuleEventTypes.NOT_IMPLEMENTED:
                MessageBox.info("WSMO-Lite Editor", "Functionality not implemented", null);
                break;
            case WSMOLiteModuleEventTypes.MENU_UPLOAD_RESOURCE:
                try {
                    uploadLocalResource();
                } catch (Exception e) {
                    MessageBox.alert("Resource Upload Error", e.getMessage(), null);
                }
        }
    }

    protected void initialize() {
        super.initialize();
    }

    public void setManagedModuleView(WSMOLiteModuleView managedModuleView) {
        this.managedModuleView = managedModuleView;
    }

    public void loadService(final String url) {

        if (url == null
                || url.length() == 0) {
            clearProgressDialog();
            return;
        }
        ((WSMOLiteModule) getModule()).getDataServiceProxy().getSAWSDLService(url, new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                MessageBox.alert("Error", caught.getMessage(), null);
                clearProgressDialog();
            }

            public void onSuccess(String result) {
                dataModel = ModelBuilder.buildModel(result);
                if (dataModel == null) {
                    clearProgressDialog();
                    return;
                }
                dataModel.setSourceUrl(url);
                int pos = url.lastIndexOf("/files/");
                if (pos != -1) {
                    dataModel.setFileName(url.substring(pos + 7));
                } else {
                    dataModel.setFileName(url);
                }
                getManagedModuleView().showService(dataModel);
                clearProgressDialog();
            }
        });
    }

    public void loadOntology(final String url) {

        if (url == null
                || url.length() == 0) {
            clearProgressDialog();
            return;
        }
        if (getManagedModuleView().findOntologyNode(url) != null) {
            MessageBox.alert("Warning", "Ontology is already loaded!", null);
            clearProgressDialog();
            return;
        }
        try {
            ((WSMOLiteModule) getModule()).getDataServiceProxy().buildModelFromOntology(url, new AsyncCallback<OntologyNode>() {
                public void onFailure(Throwable caught) {
                    MessageBox.alert("Error", caught.getMessage(), null);
                    clearProgressDialog();
                }

                public void onSuccess(OntologyNode result) {
                    if (result == null) {
                        MessageBox.alert("Error", "Ontology not loaded!", null);
                        clearProgressDialog();
                        return;
                    }
                    result.setSourceURL(url);
                    getManagedModuleView().showOntology(result);
                    clearProgressDialog();
                }
            });
        } catch (Exception err) {
            clearProgressDialog();
            MessageBox.alert("Error", err.getMessage(), null);
        }
    }

    private void showOntoURLDialog(final String title) {
        final Dialog openDialog = new Dialog();
        openDialog.setBodyBorder(false);
        openDialog.setHeading(title);
        openDialog.setHideOnButtonClick(false);
        openDialog.setModal(true);
        openDialog.setLayout(new FillLayout());

        FormPanel panel = new FormPanel();
        panel.setHeaderVisible(false);
        panel.setLabelWidth(80);
        panel.setFieldWidth(350);

        final TextField<String> urlOntoField = new TextField<String>();
        urlOntoField.setFieldLabel(title);
        panel.add(urlOntoField);

        openDialog.add(panel);
        openDialog.setButtons(Dialog.OK);

        Button okButton = openDialog.getButtonBar().getItem(0);
        okButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                String urlText = urlOntoField.getValue();
                if (urlText == null
                        || urlText.trim().length() == 0) {
                    return; // no error, no response
                }
                showProgressDialog("Loading resource, please wait ...");
                //FIXME very dirty impl
                if (title.toLowerCase().contains("ontology")) {
                    loadOntology(urlText);
                } else {
                    loadService(urlText);
                }
                openDialog.setVisible(false);
            }
        });
        openDialog.setSize(500, 80);
        openDialog.show();
    }

     private void doSaveEditorContent() {
        if (dataModel == null) {
            MessageBox.alert("Warning", "Nothing to save!", null);
            return;
        } // POČETAK: moj kod Maven
        else {

            /*
             String fileName = "mojPrvi.sawsdl";
             dataModel.setFileName(fileName);

             final String url = "http://localhost:8091/SowerWeb/sawsdls";
            
            
             String content = "";
        
             List<TreeModel> lista = dataModel.getChildren();
           
             if (lista != null && lista.size()>0){
             for (int i = 0; i < lista.size(); i++) {
             content = content + lista.get(i).toString();
             }
             }

             //   String content =  new Integer(getManagedModuleView().getTree().getAllItemCount()).toString();
            
             //   event.getData("tree");
         
        
                  
             MessageBox.info("Info", content, null);
             
               
             String content2= "";    
             
             List<TreeItem> treeItems = getManagedModuleView().getTree().getAllItems();
             
             for (int i = 0; i < treeItems.size(); i++) {
             content2 = content2 + treeItems.get(i).getText();
             }
              
             MessageBox.info("Test", content2, null);
            
             String content3 = ModelBuilder.serializeServiceModel(dataModel);
            
             if (content3.length() > 1){
             content3 = "Content 3 length is " + content3.length();
             }
             else{
             content3 = "Content 3 is empty";
             }

             MessageBox.info("Test", content3, null);*/
            
             String name = "";
        
             List<TreeModel> lista = dataModel.getChildren();
           
             if (lista != null && lista.size()>0){
             
                name = lista.get(0).toString();
             }
             
             if (name.compareTo("") == 0){
                 name = "NoNameDefined";
             }
             else{
                   name = name.substring(name.indexOf("=\"")+2);
                    name = name.substring(0, name.indexOf("\""));
             }
          
            

            String content = ModelBuilder.serializeServiceModel(dataModel);
            
          

            String fileName = name + ".sawsdl";
            dataModel.setFileName(fileName);
            
            
          //  final String fileUrl = "D:\\DOKTORAT\\generatedSAWSDLs\\"+ fileName;
            
            // store directly to Glassfish root
            final String fileUrl = "D:\\java\\glassfish3_novi\\glassfish\\domains\\domain1\\docroot\\"+ fileName;
            
            
          //  dataModel.setSourceUrl(url);
            
          //  http://localhost:8091/SowerWeb/owls/PassOntology.owl
// http://localhost:8091/SowerWeb/wsdls/AzureServices.wsdl
         
         
            ((WSMOLiteModule) getModule()).getDataServiceProxy().storeSAWSDLService(fileUrl, content,
                    new AsyncCallback<String>() {
                        public void onFailure(Throwable caught) {

                            MessageBox.alert("Error", caught.getMessage(), null);
                        }

                        public void onSuccess(String result) {

                            String url = dataModel.getSourceUrl();
                            dataModel.setSourceUrl(url);
                            Dialog d = new Dialog();
                            d.setHeading("Save");
                            d.addText("<big>Service description is saved to "+ fileUrl + " </big>");
                            d.setBodyStyle("fontWeight:bold;padding:13px;color:black;");
                            d.setSize(800, 150);
                            d.setHideOnButtonClick(true);
                            d.setButtons(Dialog.OK);
                            d.show();
                            getManagedModuleView().updateEditorTitle(dataModel);
                        }
                    });



        }


        // ZAVRŠETAK: moj kod Maven

        /*
         ResourceSelectionDialog dialog = new ResourceSelectionDialog(
         "Save Service Annotation",
         ((WSMOLiteModule) getModule()).getDataServiceProxy(),
         new ResourceSelectionDialog.SelectedResourceCallback() {
         public void processSelection(final String url) {
         showProgressDialog("Saving service description ...");
         final int pos = url.lastIndexOf("/files/");
         String fileName = url.substring(pos + 7);
         dataModel.setFileName(fileName);

         String content = ModelBuilder.serializeServiceModel(dataModel);
         ((WSMOLiteModule) getModule()).getDataServiceProxy().storeSAWSDLService(url, content,
         new AsyncCallback<String>() {
         public void onFailure(Throwable caught) {
         clearProgressDialog();
         MessageBox.alert("Error", caught.getMessage(), null);
         }

         public void onSuccess(String result) {
         clearProgressDialog();
         dataModel.setSourceUrl(url);
         Dialog d = new Dialog();
         d.setHeading("Save");
         d.addText("<big>Service description is saved at (direct link) :<br><br><a target=\"_blank\" href=\"" + url + "\">" + url + "</a></big>");
         d.setBodyStyle("fontWeight:bold;padding:13px;color:ffffff;");
         d.setSize(800, 150);
         d.setHideOnButtonClick(true);
         d.setButtons(Dialog.OK);
         d.show();
         getManagedModuleView().updateEditorTitle(dataModel);
         }
         });
         }
         });
         if (dataModel.getSourceUrl() != null
         && dataModel.getSourceUrl().lastIndexOf("/repositories/") > 0) {
         String url = dataModel.getSourceUrl();
         String repoName = url.substring(url.lastIndexOf("/repositories/") + 14);
         url = url.substring(0, url.lastIndexOf("/repositories/"));

         dialog.setSelection(url, repoName, dataModel.getFileName());
         }
         dialog.show();
        
         */
    }
     
    public void listDashboardConf() {

        CoreDashboardURLInfo contextInfo = URLUtils.getLocation();
        String apiURL = contextInfo.getParameter(URLParameterConstants.SOA4ALL_API);
        final String userID = contextInfo.getParameter(URLParameterConstants.SOA4ALL_USER_ID);
        final String sessionID = contextInfo.getParameter(URLParameterConstants.SOA4ALL_SESSION_ID);

        if (apiURL == null) {
            MessageBox.alert("Warning", "No dashboard context detected!", null);
            return;
        }

        String methodName = "list_modules";
        if (apiURL.endsWith("/")) {
            apiURL = apiURL.substring(0, apiURL.length() - 1);
        }
        String requestURL = apiURL
                + "?method=" + methodName;
        if (userID != null) {
            requestURL += '&' + URLParameterConstants.SOA4ALL_USER_ID + '=' + userID;
        }
        if (sessionID != null) {
            requestURL += '&' + URLParameterConstants.SOA4ALL_SESSION_ID + '=' + sessionID;
        }
        final String apiFinal = apiURL;
        ((WSMOLiteModule) getModule()).getDataServiceProxy().proxifyRequest("GET", apiURL, null, new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                MessageBox.alert("Error", caught.getMessage(), null);
            }

            @Override
            public void onSuccess(String result) {
                String res = "<b>user:</b>&nbsp;&nbsp;" + userID
                        + "<br><b>session:</b>&nbsp;&nbsp;" + sessionID
                        + "<br><b>API URL:</b><br>&nbsp;&nbsp;" + apiFinal
                        + "<br><b>config:</b><br><br>" + result.replace("<", "&lt;").replace(">", "&gt;");
                MessageBox.info("Dashboard Configuration", res, null);
            }
        });
    }

    private void uploadLocalResource() throws Exception {
        new ResourceUploadDialog(((WSMOLiteModule) getModule()).getDataServiceProxy()).setVisible(true);
    }

    public void showProgressDialog(String message) {
        if (progressPanel == null) {
            progressPanel = new PopupPanel(false, true);
            Image.prefetch("WSMOLiteModule/images/default/shared/large-loading.gif");
            HorizontalPanel container = new HorizontalPanel();
            container.add(new Image("WSMOLiteModule/images/default/shared/large-loading.gif"));
            TableData td = new TableData();
            td.setVerticalAlign(VerticalAlignment.MIDDLE);
            td.setMargin(10);
            container.add(new Label(message), td);
            progressPanel.setWidget(container);
            progressPanel.center();
        } else {
            ((Label) ((HorizontalPanel) progressPanel.getWidget()).getWidget(1)).setText(message);
        }

        progressPanel.show();
    }

    public void clearProgressDialog() {
        if (progressPanel != null
                && progressPanel.isVisible()) {
            progressPanel.hide();
        }
    }
}
