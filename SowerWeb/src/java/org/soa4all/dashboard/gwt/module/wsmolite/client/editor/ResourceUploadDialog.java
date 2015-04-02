package org.soa4all.dashboard.gwt.module.wsmolite.client.editor;



import org.soa4all.dashboard.gwt.module.wsmolite.client.WsmoLiteDataServiceAsync;
import org.soa4all.dashboard.gwt.module.wsmolite.client.editor.ResourceSelectionDialog.SelectedResourceCallback;

import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Encoding;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Method;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;

public class ResourceUploadDialog extends Window {
	
	private org.soa4all.dashboard.gwt.module.wsmolite.client.WsmoLiteDataServiceAsync processor;
	private FileUploadField file;
	private TextField<String> remoteLocText;
	private HiddenField<String> urlParamField;
	private String submitActionURL = "";
	
	public ResourceUploadDialog(WsmoLiteDataServiceAsync _service) {
		
		this.processor = _service;
		setHeading("Upload Local Resource");
		setLayout(new RowLayout(Orientation.VERTICAL));
		
		LayoutContainer upperPanel = new LayoutContainer(new RowLayout(Orientation.HORIZONTAL));
		
		Button selectRemoteFile = new Button("Select");
		selectRemoteFile.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				try {
				    doSelectRemoteFile();
				}
				catch(Exception ex) {
					MessageBox.alert("Error", ex.getMessage(), null);
				}
			}
		});
		upperPanel.add(new Label("Remote Location: "), new RowData(120, -1, new Margins(10)));
		
		remoteLocText = new TextField<String>();
		remoteLocText.setReadOnly(true);
		
		upperPanel.add(remoteLocText, new RowData(300, 32, new Margins(5)));
		upperPanel.add(selectRemoteFile, new RowData(50, -1, new Margins(5)));
		
		add(upperPanel, new RowData(1, 38));

		final FormPanel panel = new FormPanel();  
		panel.setHeaderVisible(false);
		panel.setAction(GWT.getModuleBaseURL() + "fileupload");  
		panel.setEncoding(Encoding.MULTIPART);  
		panel.setMethod(Method.POST); 
		panel.setButtonAlign(HorizontalAlignment.CENTER); 
		panel.setLabelWidth(80);
		panel.setFieldWidth(350);
		panel.setWidth(470);
		panel.setBodyBorder(false);
		
		urlParamField = new HiddenField<String>();
		urlParamField.setName("url");
		panel.add(urlParamField);

		file = new FileUploadField();  
		file.setName("file");
		file.setFieldLabel("Local File");  
		panel.add(file);  

		Button btn = new Button("Upload");  
		btn.addSelectionListener(new SelectionListener<ButtonEvent>() {  
			@Override  
			public void componentSelected(ButtonEvent ce) {  
				if (!panel.isValid()) {  
					return;  
				}
				if (submitActionURL == null 
						|| submitActionURL.length() == 0) {
					MessageBox.alert("No Remote Location", "No remote location seleted", null); 
					return;
				}
				if (file.getValue() == null) {
					MessageBox.alert("No File", "No local file seleted", null); 
					return;
				}
				urlParamField.setValue(submitActionURL);
				panel.submit();  

			}  
		});  
		panel.addButton(btn);
		
		btn = new Button("Cancel");  
		btn.addSelectionListener(new SelectionListener<ButtonEvent>() {  
			@Override  
			public void componentSelected(ButtonEvent ce) {  
				setVisible(false); 
			}  
		});  
		panel.addButton(btn);  

		
		panel.addListener(Events.Submit, new Listener<FormEvent>() {
			@Override
			public void handleEvent(FormEvent be) {
				MessageBox.info("Submit", "File has been submitted successfully!", null);
				setVisible(false);
			}
		});
		add(panel, new RowData(1, 80));
		setSize(500, 160);
	}
	
	private void doSelectRemoteFile() {
		ResourceSelectionDialog dialog = new ResourceSelectionDialog("Select Save Location", this.processor , new SelectedResourceCallback() {
			
			@Override
			public void processSelection(String url) {
				int repoEnd = url.indexOf("/repositories/") + 14;
				int fileStart = url.indexOf("/files/");
				remoteLocText.setValue(url.substring(repoEnd, fileStart) + " > " + url.substring(fileStart + 7));
				submitActionURL = url;
			}
		});
		if (file.getValue() != null) {
			String fName = file.getValue();
			if (fName.indexOf('/') != -1) {
				fName = fName.substring(fName.lastIndexOf('/')  +1);
			}
			if (fName.indexOf('\\') != -1) {
				fName = fName.substring(fName.lastIndexOf('\\')  +1);
			}
			dialog.setSelection(ResourceSelectionDialog.serviceURL, "", fName);
		}
		dialog.setVisible(true);
	}

}
