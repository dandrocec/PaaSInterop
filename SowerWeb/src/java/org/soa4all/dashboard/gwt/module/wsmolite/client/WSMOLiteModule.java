/**
 Copyright (c) 2009, Ontotext AD
                     TIE Holding N.V. ALL RIGHTS RESERVED
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

package org.soa4all.dashboard.gwt.module.wsmolite.client;

import org.soa4all.dashboard.gwt.core.shared.client.model.domain.AbstractModule;
import org.soa4all.dashboard.gwt.core.shared.client.model.domain.DefaultModuleCategories;
import org.soa4all.dashboard.gwt.core.shared.client.model.domain.ModuleCategory;
import org.soa4all.dashboard.gwt.core.shared.client.util.rpc.RemoteServiceHelper;
import org.soa4all.dashboard.gwt.module.wsmolite.client.controller.WSMOLiteModuleController;
import org.soa4all.dashboard.gwt.module.wsmolite.client.view.WSMOLiteModuleView;

import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.toolbar.ToolItem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Composer module definition
 * 
 * @author Manuel J. Gallego <manuel.gallego@tieglobal.com>
 * 
 */
public class WSMOLiteModule extends AbstractModule {

  
    private WSMOLiteModuleView moduleView;
    private WSMOLiteModuleController moduleController;
    
    private WsmoLiteDataServiceAsync processor;

    public WSMOLiteModule() {
	    moduleController = new WSMOLiteModuleController(this);
	    moduleView = new WSMOLiteModuleView(moduleController);
	    moduleController.setManagedModuleView(moduleView);
	    
	    processor = (WsmoLiteDataServiceAsync)
	    RemoteServiceHelper.getInstance().setupRemoteService((ServiceDefTarget) GWT.create(WsmoLiteDataService.class));
	    
	//    processor = (WsmoLiteDataServiceAsync)GWT.create(WsmoLiteDataService.class);
     //   String serviceEndpoint = GWT.getModuleBaseURL();
      //  if (serviceEndpoint.endsWith("/") == false) {
    //        serviceEndpoint += '/';
     //   }
      //  serviceEndpoint += "WsmoLiteDataService.do";
	  //  ((ServiceDefTarget)processor).setServiceEntryPoint(serviceEndpoint);
    }

    public WsmoLiteDataServiceAsync getDataServiceProxy() {
        return this.processor;
    }
    
    public String getDashboardOverviewButtonIconStyle() {
	    return " ";
    }

    public String getDashboardOverviewButtonTitle() {
	    return "Editor";
    }

    public String getDescription() {
	    return null;
    }

    public String getHomeButtonIconStyle() {
	return "";
    }

    public long getId() {
	    return 0;
    }

    public LayoutContainer getMainContentContainer() {
	    return getModuleView().getMainContentContainer();
    }

    public ToolItem getMainMenu() {
	return getModuleView().getMainMenu();
    }

    public ModuleCategory getModuleCategory() {
	return DefaultModuleCategories.getWSMOLiteModuleCategory();
    }

 

    public WSMOLiteModuleView getModuleView() {
	return moduleView;
    }

    public String getName() {
	return "Editor";
    }

    public Controller getParentController() {
	return new WSMOLiteModuleController(this);
    }

    public ToolItem getShortcutMenu() {
	return getModuleView().getShortcutMenu();
    }

    public boolean isDashboardOverviewVisible() {
	return true;
    }

 

    public void setModuleView(WSMOLiteModuleView moduleView) {
	this.moduleView = moduleView;
    }
}
