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

package org.soa4all.dashboard.gwt.module.wsmolite.client.view;



import java.util.LinkedList;
import java.util.List;

import org.soa4all.dashboard.gwt.module.wsmolite.client.controller.WSMOLiteModuleEventTypes;
import org.soa4all.dashboard.gwt.module.wsmolite.client.editor.ActionHandler;
import org.soa4all.dashboard.gwt.module.wsmolite.client.editor.GUIHelper;
import org.soa4all.dashboard.gwt.module.wsmolite.client.editor.model.*;
import org.soa4all.dashboard.gwt.module.wsmolite.client.editor.onto.model.*;
import org.soa4all.dashboard.gwt.module.wsmolite.client.view.ui.WSMOLiteModuleMainContainer;

import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.Style.*;
import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.extjs.gxt.ui.client.dnd.DragSource;
import com.extjs.gxt.ui.client.dnd.TreeDropTarget;
import com.extjs.gxt.ui.client.dnd.DND.Operation;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.TreeBuilder;
import com.extjs.gxt.ui.client.widget.*;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.layout.*;
import com.extjs.gxt.ui.client.widget.menu.*;
import com.extjs.gxt.ui.client.widget.toolbar.AdapterToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.TextToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.ToolItem;
import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.extjs.gxt.ui.client.widget.tree.TreeItem;




public class WSMOLiteModuleView extends AbstractModuleView {

    private WSMOLiteModuleMainContainer moduleMainContainer = new WSMOLiteModuleMainContainer();

    private ToolItem mainMenu;
    
    private ContentPanel editorPanel, ontologiesPanel, previewPanel;
    private LayoutContainer ontoTreeHolder;

    private Tree ontoTree, tree;
    
    private ListStore<ModelNode> searchStore = new ListStore<ModelNode>();
    private Html previewArea;

    public WSMOLiteModuleView(Controller controller) {
        super(controller);
        buildGUI();
        mainMenu = createMainMenu();
    }

    public Tree getTree() {
        return tree;
    }
    
    
    
    private void buildGUI() {
        Viewport viewport = new Viewport();
        viewport.setLayout(new FillLayout());

        ContentPanel mainContainer = new ContentPanel();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setHeaderVisible(false);

        
        LayoutContainer westPanel = new LayoutContainer(new BorderLayout());
        LayoutContainer centerPanel = new LayoutContainer(new BorderLayout());

        editorPanel = new ContentPanel();
        editorPanel.setLayout(new BorderLayout());
        updateEditorTitle(null);
        editorPanel.setScrollMode(Scroll.AUTO);
        
        tree = initServiceTree();
        editorPanel.add(tree, new BorderLayoutData(LayoutRegion.CENTER));

        
        ContentPanel ontoSearchPanel = new ContentPanel();
        ontoSearchPanel.setHeading("Ontologies Search");
        ontologiesPanel = new ContentPanel();
        ontologiesPanel.setHeading("Ontologies");
        ontologiesPanel.setLayout(new BorderLayout());
      //  ontologiesPanel.setScrollMode(Scroll.AUTO);
        
        previewPanel = new ContentPanel(new BorderLayout());
        previewPanel.setScrollMode(Scroll.AUTO);
        previewPanel.setHeading("WSDL Preview");
        
        previewArea = new Html();
        previewArea.setStyleAttribute("foregroundColor", "black");
        previewPanel.add(previewArea, new BorderLayoutData(LayoutRegion.CENTER));
        
        BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER, (float) 0.7);
        bld.setSplit(true);
        bld.setFloatable(true);
        bld.setCollapsible(true);
        bld.setMargins(new Margins(25, 0, 0, 0));
        westPanel.add(ontologiesPanel, bld);
        
        // the ontologies tree
        ontoTree = initOntologyTree();
        
        ontoTreeHolder = new LayoutContainer(new BorderLayout());
        ontoTreeHolder.setScrollMode(Scroll.AUTO);
        ontoTreeHolder.add(ontoTree, new BorderLayoutData(LayoutRegion.CENTER));
        
        ontologiesPanel.add(ontoTreeHolder, new BorderLayoutData(LayoutRegion.CENTER));
        
        ButtonBar bBar = new ButtonBar();
        ontologiesPanel.setButtonBar(bBar);
        
        Button findButton = new Button("Quick Find ...");
        bBar.add(findButton);
        findButton.addSelectionListener(new SelectionListener<ComponentEvent>(){
            public void componentSelected(ComponentEvent ce) {
                if (searchStore.getCount() == 0) {
                    MessageBox.info("No Ontology Data", "Load ontology first!", null);
                    return;
                }
                ActionHandler.showQuickFindDialog(searchStore, WSMOLiteModuleView.this);
            }
        });
        // end of ontology tree
        
        bld = new BorderLayoutData(LayoutRegion.SOUTH, (float) 0.2);
        bld.setSplit(true);
        bld.setFloatable(true);
        bld.setCollapsible(true);
//        bld.setMargins(new Margins(0, 0, 22, 0));
        centerPanel.add(previewPanel, bld);
        
        bld = new BorderLayoutData(LayoutRegion.CENTER, (float) 0.8);
        bld.setSplit(true);
        bld.setFloatable(true);
        bld.setCollapsible(true);
        bld.setMargins(new Margins(25, 0, 0, 0));
        
        centerPanel.add(editorPanel, bld);
        
        bld = new BorderLayoutData(LayoutRegion.SOUTH, (float) 0.2);
        bld.setSplit(true);
        bld.setFloatable(true);
        bld.setCollapsible(true);
 //       bld.setMargins(new Margins(0, 0, 22, 0));
        westPanel.add(ontoSearchPanel, bld);
        
        BorderLayoutData westLayoutData = new BorderLayoutData(LayoutRegion.WEST, (float) 0.2);
        westLayoutData.setCollapsible(true);
        westLayoutData.setSplit(true);

        mainContainer.add(westPanel, westLayoutData);
        BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
        
        mainContainer.add(centerPanel, centerData);

        mainContainer.setBorders(true);
        
        viewport.add(mainContainer);
        moduleMainContainer.addStyleName("x-border-layout-ct");
        moduleMainContainer.add(viewport);
        viewport.layout();
    }

    private ToolItem createMainMenu() {
        ToolBar toolBar = new ToolBar();
        TextToolItem openItem = new TextToolItem("File");

        Menu fileMenu = new Menu();
        
        MenuItem openMenuItem = new MenuItem("Open ...");
        openMenuItem.setIconStyle("icon-open");
        fileMenu.add(openMenuItem);
        
        Menu openSubMenu = new Menu();
        openMenuItem.setSubMenu(openSubMenu);

        /*
        MenuItem mItem = new MenuItem("Service Description");
        mItem.setIconStyle("icon-open-ws");
        mItem.addSelectionListener(new SelectionListener<MenuEvent>() {
            public void componentSelected(MenuEvent ce) {
                fireEvent(WSMOLiteModuleEventTypes.MENU_OPEN_SERVICE);
            }
        });
        openSubMenu.add(mItem);
        */
        
        MenuItem mItem = new MenuItem("Service Description from URL");
        mItem.setIconStyle("icon-open-ws");
        mItem.addSelectionListener(new SelectionListener<MenuEvent>() {
            public void componentSelected(MenuEvent ce) {
                fireEvent(WSMOLiteModuleEventTypes.MENU_OPEN_SERVICE_FROM_URL);
            }
        });
        openSubMenu.add(mItem);
        openSubMenu.add(new SeparatorMenuItem());

        /*
        mItem = new MenuItem("Ontology");
        mItem.setIconStyle("icon-open-onto");
        mItem.addSelectionListener(new SelectionListener<MenuEvent>() {
            public void componentSelected(MenuEvent ce) {
                fireEvent(WSMOLiteModuleEventTypes.MENU_OPEN_ONTOLOGY);
            }
        });
        openSubMenu.add(mItem);
        */
        
        mItem = new MenuItem("Ontology from URL");
        mItem.setIconStyle("icon-open-onto");
        mItem.addSelectionListener(new SelectionListener<MenuEvent>() {
            public void componentSelected(MenuEvent ce) {
                fireEvent(WSMOLiteModuleEventTypes.MENU_OPEN_ONTOLOGY_FROM_URL);
            }
        });
        openSubMenu.add(mItem);

        fileMenu.add(new SeparatorMenuItem());
        mItem = new MenuItem("Save ...");
        mItem.setIconStyle("icon-save");
        mItem.addSelectionListener(new SelectionListener<MenuEvent>() {
            public void componentSelected(MenuEvent ce) {
                fireEvent(WSMOLiteModuleEventTypes.MENU_SAVE_SERVICE);
            }
        });
        fileMenu.add(mItem);
        
        /*
        fileMenu.add(new SeparatorMenuItem());
        mItem = new MenuItem("Export to iServe");
        mItem.setIconStyle("icon-export");
        mItem.addSelectionListener(new SelectionListener<MenuEvent>() {
            public void componentSelected(MenuEvent ce) {
                fireEvent(WSMOLiteModuleEventTypes.MENU_ISERVE_EXPORT);
            }
        });
        fileMenu.add(mItem);
        fileMenu.add(new SeparatorMenuItem());
        */
        
        /*

		mItem = new MenuItem("Upload Local Resource");
        mItem.setIconStyle("icon-upload");
        mItem.addSelectionListener(new SelectionListener<MenuEvent>() {
            public void componentSelected(MenuEvent ce) {
                fireEvent(WSMOLiteModuleEventTypes.MENU_UPLOAD_RESOURCE);
            }
        });
        fileMenu.add(mItem);

		fileMenu.add(new SeparatorMenuItem());

        mItem = new MenuItem("Close");
        mItem.setIconStyle("icon-close");
        mItem.addSelectionListener(new SelectionListener<MenuEvent>() {
            public void componentSelected(MenuEvent ce) {
                fireEvent(WSMOLiteModuleEventTypes.MENU_CLOSE);
            }
        });
        fileMenu.add(mItem);
        */

        openItem.setMenu(fileMenu);
        toolBar.add(openItem);

        /*
        // Help menu
        TextToolItem helpMenuItem = new TextToolItem("Help");
        Menu helpMenu = new Menu();
        helpMenuItem.setMenu(helpMenu);
        toolBar.add(helpMenuItem);
        
        mItem = new MenuItem("About WSMO-Lite Editor");
        mItem.setIconStyle("icon-about");
        mItem.addSelectionListener(new SelectionListener<MenuEvent>() {
            public void componentSelected(MenuEvent ce) {
                fireEvent(WSMOLiteModuleEventTypes.MENU_ABOUT);
            }
        });      
        helpMenu.add(mItem);

        mItem = new MenuItem("About Dashboard");
        mItem.setIconStyle("icon-about");
        mItem.addSelectionListener(new SelectionListener<MenuEvent>() {
            public void componentSelected(MenuEvent ce) {
                fireEvent(WSMOLiteModuleEventTypes.MENU_DASHBOARD_INFO);
            }
        });      
        helpMenu.add(mItem);

        helpMenu.add(new SeparatorMenuItem());
        mItem = new MenuItem("Help Contents");
        mItem.setIconStyle("icon-help");
        mItem.addSelectionListener(new SelectionListener<MenuEvent>() {
            public void componentSelected(MenuEvent ce) {
                fireEvent(WSMOLiteModuleEventTypes.NOT_IMPLEMENTED);
            }
        }); 
        helpMenu.add(mItem);
        */ 
     
        AdapterToolItem moduleMenu = new AdapterToolItem(toolBar);
        moduleMenu.setBorders(false);
        return moduleMenu;
    }

    private Tree initServiceTree() {
        final Tree tree = new Tree();
        tree.setStyleAttribute("backgroundColor", "white");
        
        TreeDropTarget target = new TreeDropTarget(tree) {
            @Override
            protected void onDragDrop(DNDEvent event) {
                ModelNode model = (ModelNode) event.data;
                if (event.getTarget() == null) {
                    return;
                }
                TreeItem item = tree.findItem(event.getTarget());
                if (item == null) {
                    return;
                }
                if (false == GUIHelper.checkDropTarget(item.getModel())) {
                    event.doit = false;
                    return;
                }
                String sawsdlPrefix = ((ElementNode)item.getModel()).getSawsdlPrefix();
                String annAttrName = "sawsdl:modelReference";
                if (sawsdlPrefix != null){
                    if (sawsdlPrefix.length() == 0) {
                        annAttrName = "modelReference";
                    }
                    else {
                        annAttrName = sawsdlPrefix + ":modelReference";
                    }
                }
                AnnotationNode annNode = new AnnotationNode(annAttrName, model.getURI());
                ((ElementNode)item.getModel()).add(annNode);
                TreeItem newItem = new TreeItem(annNode.toString());
                ComponentHelper.setModel(newItem, annNode);
                item.add(newItem);
                newItem.setIconStyle("icon-ann-attr");
                item.setExpanded(true);
                tree.getSelectionModel().deselectAll();
                tree.getSelectionModel().select(newItem);
                
                event.doit = false;
                super.onDragDrop(event);
            }
            protected void showFeedback(DNDEvent event) {
                super.showFeedback(event);
                if (event.getTarget() == null) {
                    event.status.setStatus(false);
                    return;
                }
                TreeItem item = tree.findItem(event.getTarget());
                if (item == null) {
                    event.status.setStatus(false);
                    return;
                }
                boolean stat = GUIHelper.checkDropTarget(item.getModel());
                event.status.setStatus(stat);
            }
        };
        target.setGroup("wsmo-lite-editor");
        target.setOverStyle("drag-ok");
        target.setOperation(Operation.COPY);
        target.setAutoExpand(true);
        
        
        Menu contextMenu = new Menu();
        contextMenu.setWidth(130);
        
        contextMenu.addListener(Events.BeforeShow,
                new Listener<MenuEvent>() {

            @SuppressWarnings("unchecked")
          public void handleEvent(MenuEvent be) {
                //be.container.removeAll();
                
                final TreeItem selected = tree.getSelectionModel().getSelectedItem();
                if (selected == null) {
                    be.doit = false;
                    return;
                }
                if (selected.getModel() instanceof AnnotationNode) {
                    be.container.removeAll();
                    MenuItem remove = new MenuItem();
                    remove.setText("Remove");
                   // remove.setIconStyle("icon-add");
                    be.container.add(remove);
                    remove.addSelectionListener(new SelectionListener<MenuEvent>() {
                        public void componentSelected(MenuEvent ce) {
                            GUIHelper.removeAnnotation(selected);
                        }
                    });
                }
                else if (selected.getModel() instanceof ElementNode) {
                    if (false == GUIHelper.checkDropTarget(selected.getModel())) {
                        be.doit = false;
                        return;
                    }
                    be.container.removeAll();
                    boolean hasLifting  = false;
                    boolean hasLowering = false;
                    for (TreeModel tm : ((ElementNode)selected.getModel()).getChildren()) {
                        if (tm instanceof AnnotationNode) {
                            if (tm.toString().contains("loweringSchemaMapping")) {
                                hasLowering = true;
                            }
                            else if (tm.toString().contains("liftingSchemaMapping")) {
                                hasLifting = true;
                            }
                        }
                    }
                    
                    if (hasLifting == false) {
                        MenuItem insert = new MenuItem();
                        insert.setText("Add Lifting Schema");
                        be.container.add(insert);
                        insert.addSelectionListener(new SelectionListener<MenuEvent>() {
                            public void componentSelected(MenuEvent ce) {
                                GUIHelper.addLiftingSchema(tree, selected);
                            }
                        });
                        
                        insert = new MenuItem();
                        insert.setText("Select Lifting Schema");
                        be.container.add(insert);
                        insert.addSelectionListener(new SelectionListener<MenuEvent>() {
                            public void componentSelected(MenuEvent ce) {
                                AppEvent<Object> event = new AppEvent<Object>(WSMOLiteModuleEventTypes.ACTION_SELECT_LIFTING);
                             //   GUIHelper.selectLiftingSchema(tree, selected);
                                event.setData("tree", tree);
                                event.setData("item", selected);
                                fireEvent(event);
                            }
                        });                      
                        if (hasLowering == false) {
                            be.container.add(new SeparatorMenuItem());
                        }
                    }
                    if (hasLowering == false) {
                        MenuItem insert = new MenuItem();
                        insert.setText("Add Lowering Schema");
                        be.container.add(insert);                       
                        insert.addSelectionListener(new SelectionListener<MenuEvent>() {
                            public void componentSelected(MenuEvent ce) {
                                GUIHelper.addLoweringSchema(tree, selected);
                            }
                        });                      
                        insert = new MenuItem();
                        insert.setText("Select Lowering Schema");
                        be.container.add(insert);
                        insert.addSelectionListener(new SelectionListener<MenuEvent>() {
                            public void componentSelected(MenuEvent ce) {
                                AppEvent<Object> event = new AppEvent<Object>(WSMOLiteModuleEventTypes.ACTION_SELECT_LOWERING);
                                event.setData("tree", tree);
                                event.setData("item", selected);
                                fireEvent(event);
                                //GUIHelper.selectLoweringSchema(tree, selected);
                            }
                        });
                    }
                    if (hasLifting && hasLowering) {
                        be.doit = false;
                    }
                }
                else {
                    be.doit = false;
                }
            }

        });
        
        tree.setContextMenu(contextMenu);
        tree.addListener(Events.SelectionChange, new Listener<TreeEvent>() {
            public void handleEvent(TreeEvent be) {
                if (tree.getSelectionModel().getSelectedItem() == null) {
                    previewArea.setHtml("");
                    return;
                }
                previewArea.setHtml("<table height=\"90%\" width=\"90%\" border=\"0\"><tr valign=\"middle\"><td>" + 
                        GUIHelper.getPreviewText(
                                tree.getSelectionModel().getSelectedItem().getModel())
                                + "</td></tr></table>");
                previewArea.setStyleAttribute("color", "#000000");
            }
        });
        return tree;
    }
    
    private Tree initOntologyTree() {
        final Tree tree = new Tree();
        tree.getStyle().setJointCloseIconStyle("icon-tree-joint-c");
        tree.getStyle().setJointOpenIconStyle("icon-tree-joint-o");

        DragSource source = new DragSource(tree) {
            @Override
            protected void onDragStart(DNDEvent event) {
                if (tree.getSelectedItem().getModel() instanceof AttributeValueNode
                        || tree.getSelectedItem().getModel() instanceof AttributeNode
                        || tree.getSelectedItem().getModel() instanceof RelationInstanceNode
                        || "_#".equals(tree.getSelectedItem().getModel())) {
                    event.doit = false;
                    return;
                }
                // by default drag is allowed
                event.data = tree.getSelectedItem().getModel();
                event.status.update(El.fly(tree.getSelectedItem().getElement()).cloneNode(true));
            }
        };
        source.setGroup("wsmo-lite-editor");

        final Menu contextMenu = new Menu();
      
        contextMenu.addListener(Events.BeforeShow,
                new Listener<MenuEvent>() {

            public void handleEvent(MenuEvent be) {
            	final TreeItem ti = tree.getSelectionModel().getSelectedItem();
            	if (ti != null 
            			&& ti.getModel() instanceof OntologyNode) {
            		contextMenu.removeAll();
                    MenuItem remove = new MenuItem();
                    remove.setText("Close Ontology");
                    be.container.add(remove);
                    remove.addSelectionListener(new SelectionListener<MenuEvent>() {
                        public void componentSelected(MenuEvent ce) {
                        	clearFromSearchIndex((OntologyNode)ti.getModel());
                        	ti.getParentItem().remove(ti);
                        }
                    });
            	}
            	else {
                    be.doit = false;
            	}
            }
        });
        tree.setContextMenu(contextMenu);
    
        return tree;
    }

    public LayoutContainer getMainContentContainer() {
        return moduleMainContainer;
    }

    public ToolItem getMainMenu() {
        return mainMenu;
    }

    public ToolItem getShortcutMenu() {
        // TODO: Localize
        ToolBar toolBar = new ToolBar();
    //    toolBar.add(new LabelToolItem("WSMOLite Shortcuts"));
        AdapterToolItem shortcutMenu = new AdapterToolItem(toolBar);
        shortcutMenu.setBorders(false);
        return shortcutMenu;
    }

    @Override
    protected void handleEvent(AppEvent<?> event) {
        Info.display("MessageBox", "SHOW_INFO");
        switch (event.type) {
        case WSMOLiteModuleEventTypes.SHOW_INFO:
            Info.display("MessageBox", "SHOW_INFO");
            return;
        }
    }
    
    public void showService(DocumentNode doc) {
        if (doc == null) {
            return;
        }
        previewArea.setHtml("");
        tree.removeAll();
        TreeBuilder.buildTree(tree, doc);
        GUIHelper.setServiceModelIcons(tree.getRootItem());
        editorPanel.layout();
        tree.expandPath(tree.getRootItem().getItem(0).getPath());
        updateEditorTitle(doc);
    }
    
    public void updateEditorTitle(DocumentNode doc) {
    	String lab = "WSDL/SAWSDL Editor";
    	if (doc != null) {
    		lab += " - " + doc.getFileName();
    	}
    	editorPanel.setHeading(lab);
    }
    
    public void showOntology(OntologyNode ontoNode) {
        OntologyNode top = new OntologyNode("", "");
        top.add(ontoNode);

        TreeBuilder.buildTree(ontoTree, top);
        for(TreeItem ti : ontoTree.getRootItem().getItems()) {
            if (ontoNode.equals(ti.getModel())) {
                GUIHelper.setServiceModelIcons(ti);
                GUIHelper.setOntologyTooltips(ti);
                ontoTree.getSelectionModel().select(ti);
                ontoTreeHolder.scrollIntoView(ti);
                ti.setExpanded(true);
                break;
            }
        }
        loadSearchIndex(ontoNode);
    }
    
    public void selectOntologyNode(ModelNode sel) {
        TreeItem toSelect = null;
        
        List<TreeItem> todo = new LinkedList<TreeItem>(ontoTree.getRootItem().getItems());
        while (todo.size() > 0) {
            TreeItem item = todo.remove(0);
            if (item.getModel() != null
                    && item.getModel() instanceof ModelNode) {
                if (((ModelNode)item.getModel()).getURI().equals(sel.getURI())) {
                    toSelect = item;
                    break;
                }
            }
            if (item.getItemCount() > 0) {
                todo.addAll(item.getItems());
            }
        }
        if (toSelect == null) {
            return; //never here
        }
        ontoTree.getSelectionModel().select(toSelect);
        ontoTreeHolder.scrollIntoView(toSelect);
    }
    
    private void loadSearchIndex(OntologyNode onto) {
        List<ModelNode> todo = new LinkedList<ModelNode>(onto.getChildren());
        while (todo.size() > 0) {
            ModelNode node = todo.remove(0);
            if (node instanceof ConceptNode
                    || node instanceof InstanceNode
                    || node instanceof RelationNode
                    || node instanceof RelationInstanceNode) {
                if (this.searchStore.contains(node) == false) {
                    this.searchStore.add(node);
                }
                if (node.getChildCount() > 0) {
                    todo.addAll(node.getChildren());
                }
            }
        }
        this.searchStore.sort("lab", SortDir.ASC);
    }
    
    private void clearFromSearchIndex(OntologyNode root) {
        List<ModelNode> todo = new LinkedList<ModelNode>(root.getChildren());
        while (todo.size() > 0) {
            ModelNode node = todo.remove(0);
            if (node instanceof ConceptNode
                    || node instanceof InstanceNode
                    || node instanceof RelationNode
                    || node instanceof RelationInstanceNode) {
                if (this.searchStore.contains(node)) {
                    this.searchStore.remove(node);
                }
                if (node.getChildCount() > 0) {
                    todo.addAll(node.getChildren());
                }
            }
        }
    }
    
    public OntologyNode findOntologyNode(String url) {
        for(TreeItem ti : ontoTree.getRootItem().getItems()) {
            if (ti.getModel() != null 
                    && ti.getModel() instanceof OntologyNode
                    && url.equals(((OntologyNode)ti.getModel()).getSource())) {
                return (OntologyNode)ti.getModel();
            }
        }
        return null;
    }
    
    public void closeService() {
        tree.removeAll();
        updateEditorTitle(null);
        previewArea.setHtml("");
    }
    


}
