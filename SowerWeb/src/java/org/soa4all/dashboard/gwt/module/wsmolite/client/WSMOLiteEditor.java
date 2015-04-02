package org.soa4all.dashboard.gwt.module.wsmolite.client;


import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WSMOLiteEditor implements EntryPoint {

	public WSMOLiteModule module;

	public void onModuleLoad() {
            
              
		this.module = new WSMOLiteModule();

		ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setBorders(false);
		panel.setLayout(new FillLayout());
	
		ToolBar toolBar = new ToolBar();
		toolBar.add(module.getMainMenu());
		panel.setTopComponent(toolBar);
		
		panel.add(module.getMainContentContainer());

                /*
		Viewport viewport = new Viewport();
		viewport.setLayout(new FillLayout());
		viewport.add(panel);
		RootPanel.get().add(viewport);
                */
                
                /*
                String moduleUrl = GWT.getModuleBaseURL();
                moduleUrl = moduleUrl.substring(0, moduleUrl.indexOf("WSMOLiteModule/"));
                Label label1 = new Label(moduleUrl);
           */
                
             //   RootPanel.get().add(label1);
                RootPanel.get().add(panel);
               
	}
}
