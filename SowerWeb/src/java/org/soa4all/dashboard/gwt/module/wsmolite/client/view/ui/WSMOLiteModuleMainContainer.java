/*Copyright (c) 2009 TIE Holding N.V. ALL RIGHTS RESERVED.
 *  http://www.tieGlobal.com
 *  info@tieGlobal.com

 *All source code and material of this file is proprietary to TIE.  No part of this file may be changed, copied, or transmitted in any form or for any purpose without the express prior written permission of *TIE. The content of this file may not be used in advertising or publicity pertaining to distribution of the software without specific, written prior permission. 
 *The material embodied on this software is provided to you "as-is" and without warranty of any kind, express, implied or otherwise, including without limitation, any warranty of merchantability or fitness *for a particular purpose.  In no event shall TIE be liable to you or anyone else for any direct, special, incidental, indirect or consequential damages of any kind, or any damages whatsoever, including *without limitation, loss of profit, loss of use, savings or revenue, or the claims of third parties, whether or not TIE has been advised of the possibility of such loss, however caused and on any theory of *liability, arising out of or in connection with the possession, use or performance of this software.
 */
package org.soa4all.dashboard.gwt.module.wsmolite.client.view.ui;

import org.soa4all.dashboard.gwt.core.shared.client.controller.DashboardDesktopControllerHelper;

import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;

/**
 * Monitor module main UI GWT container
 * 
 * @author Manuel J. Gallego <manuel.gallego@tieglobal.com>
 * 
 */
public class WSMOLiteModuleMainContainer extends LayoutContainer {

    private DashboardDesktopControllerHelper dashboardDesktopControllerHelper = DashboardDesktopControllerHelper.getInstance();

   
    public WSMOLiteModuleMainContainer() {
	    setLayout(new FillLayout());// RowLayout(Orientation.VERTICAL));
    }

    public DashboardDesktopControllerHelper getDashboardDesktopControllerHelper() {
	    return dashboardDesktopControllerHelper;
    }

    @Override
    protected void onRender(Element parent, int pos) {
	super.onRender(parent, pos);
    }

    public void setDashboardDesktopControllerHelper(DashboardDesktopControllerHelper dashboardDesktopControllerHelper) {
	    this.dashboardDesktopControllerHelper = dashboardDesktopControllerHelper;
    }

}
