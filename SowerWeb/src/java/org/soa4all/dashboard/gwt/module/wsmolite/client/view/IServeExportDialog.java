package org.soa4all.dashboard.gwt.module.wsmolite.client.view;

import org.soa4all.dashboard.gwt.module.wsmolite.client.WsmoLiteDataServiceAsync;
import org.soa4all.dashboard.gwt.module.wsmolite.client.controller.WSMOLiteModuleController;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class IServeExportDialog extends Window {

	private TextField<String> iServeURL, userID, passField;
	private WSMOLiteModuleController callBackController;
	
	public IServeExportDialog(
			final WsmoLiteDataServiceAsync processor, 
			final String dataContent,
			WSMOLiteModuleController callBack) {
		
		setHeading("Login to iServe");
		FormPanel simple = new FormPanel();  
	    simple.setHeaderVisible(false);  
	    simple.setFrame(true);  
	    simple.setWidth(350); 
	    
	    this.callBackController = callBack;
	  
	    iServeURL = new TextField<String>();  
	    iServeURL.setFieldLabel("iServe URL");  
	    iServeURL.setAllowBlank(false);  
	    iServeURL.setValue("http://iserve.kmi.open.ac.uk");
	  //  iServeURL.setValue("http://iserve-dev.kmi.open.ac.uk:8080/iserve");
	    simple.add(iServeURL, new FormData("-20"));  

	    userID = new TextField<String>();  
	    userID.setFieldLabel("User ID");  
	    userID.setAllowBlank(false);  
	    simple.add(userID, new FormData("-20"));  

	    passField = new TextField<String>();  
	    passField.setFieldLabel("Password");  
	    passField.setAllowBlank(false);  
	    passField.setPassword(true);
	    simple.add(passField, new FormData("-20"));
	    

	    Button b = new Button("Publish");  
	    b.addSelectionListener(new SelectionListener<ComponentEvent>() {
			@Override
			public void componentSelected(ComponentEvent ce) {
				try {
					setVisible(false);
					submitData(processor, dataContent);
				}
				catch(Exception ex) {
					setVisible(true);
					MessageBox.alert("Error", ex.getMessage(), null);
				}
			}
		});
	    simple.addButton(b);  
	    b =  new Button("Cancel");
	    b.addSelectionListener(new SelectionListener<ComponentEvent>() {
			@Override
			public void componentSelected(ComponentEvent ce) {
				setVisible(false);
			}
		});
	    simple.addButton(b);
	    simple.setButtonAlign(HorizontalAlignment.CENTER);
	    
	    setLayout(new FillLayout());
	    add(simple);
	    setSize(600, 180);
	    setModal(true);
	}
	
	private void submitData(WsmoLiteDataServiceAsync processor, String dataContent) throws Exception {
		if (iServeURL.getValue() == null 
				|| iServeURL.getValue().length() == 0) {
			throw new Exception("No iServe URL provided!");
		}
		if (userID.getValue() == null 
				|| userID.getValue().length() == 0) {
			throw new Exception("No user ID provided!");
		}
		if (passField.getValue() == null 
				|| passField.getValue().length() == 0) {
			throw new Exception("No user password provided!");
		}
		callBackController.showProgressDialog("Exporting service description ...");
		processor.exportSAWSDLToIServe(iServeURL.getValue(), 
					userID.getValue(), 
					passField.getValue(), 
					dataContent, 
					new AsyncCallback<String>() {

				@Override
				public void onSuccess(String result) {
					callBackController.clearProgressDialog();
                    Dialog d = new Dialog();
                    d.setHeading("Service Description Export");
                    d.addText("<big>" + result + "</text>");
//                    d.addText("<big>Service description successfully exported at:<br><br><a target=\"_blank\" href=\"" + result + "\">" + result + "</a></big>");
                    d.setBodyStyle("fontWeight:bold;padding:13px;color:ffffff;");
                    d.setSize(800, 150);
                    d.setHideOnButtonClick(true);
                    d.setButtons(Dialog.OK);
                    d.show();
				}

				@Override
				public void onFailure(Throwable caught) {
					callBackController.clearProgressDialog();
					MessageBox.alert("Error", caught.getMessage(), null);
					setVisible(true);
				}
			});
	}

}
