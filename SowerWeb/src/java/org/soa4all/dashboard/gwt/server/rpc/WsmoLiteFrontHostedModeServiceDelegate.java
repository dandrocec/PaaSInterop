package org.soa4all.dashboard.gwt.server.rpc;

import org.soa4all.dashboard.gwt.server.rpc.AbstractFrontHostedModeServiceDelegate;

/**
 * 
 * @author Manuel J. Gallego <manuel.gallego@tieglobal.com>
 * 
 */
public class WsmoLiteFrontHostedModeServiceDelegate extends AbstractFrontHostedModeServiceDelegate {
    @Override
    public String getClassPathXmlApplicationContextDefinition() {
	return "classpath:soa4all_dashboard_gwt_module_wsmolite_applicationContext.xml";
    }

}
