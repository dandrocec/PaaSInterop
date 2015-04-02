package org.soa4all.dashboard.gwt.module.wsmolite.client.view;

import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.toolbar.ToolItem;

/**
 * 
 * @author Manuel J. Gallego <manuel.gallego@tieglobal.com>
 * 
 */
public abstract class AbstractModuleView extends View {

    public AbstractModuleView(Controller controller) {
	super(controller);
    }

    public abstract LayoutContainer getMainContentContainer();

    public abstract ToolItem getMainMenu();

    public abstract ToolItem getShortcutMenu();
}