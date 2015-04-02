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


import java.io.Serializable;
import java.util.*;

import org.soa4all.dashboard.gwt.module.wsmolite.client.WsmoLiteDataServiceAsync;

import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.Style.*;
import com.extjs.gxt.ui.client.binder.TreeBinder;
import com.extjs.gxt.ui.client.data.*;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.*;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.*;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.menu.SeparatorMenuItem;
import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.extjs.gxt.ui.client.widget.tree.TreeItem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;



public class ResourceSelectionDialog extends Dialog {

    private TextField<String> storageURL;
    private Tree tree;
    private String result;
    private TextField<String> nameText;
    private WsmoLiteDataServiceAsync processor;
    private TreeLoader<?> loader;
    private SelectedResourceCallback callback;
    public static String serviceURL = "http://coconut.tie.nl:8080/storage";
//    private static String serviceURL = "http://stronghold.sirma.bg:8080/storage";
    private boolean isSaveDialog = false;
    
    private String repositoryToSelect = null;
    
    private final String moduleURL = GWT.getModuleBaseURL();
    private final String moduleURLForIcons = moduleURL.substring(0, moduleURL.indexOf("WSMOLiteModule/"));
    
    public interface SelectedResourceCallback {
        public void processSelection(String url);
    }
    
    public void setSelection(String url, String rep, String fileName) {
        storageURL.setValue(url);
        nameText.setValue(fileName);
        repositoryToSelect = rep;
        tree.addListener(Events.BeforeRender, new Listener<ComponentEvent>() {

            public void handleEvent(ComponentEvent be) {
                loader.load(null);
                tree.getRootItem().addListener(Events.Add, new Listener<BaseEvent>() {
                    public void handleEvent(BaseEvent be) {
                        if (repositoryToSelect != null) {
                            List<TreeItem> ch = tree.getRootItem().getItems();
                            if (ch == null) {
                                return;
                            }
                            for(TreeItem ti : ch) {
                                if (ti.getModel().toString().equals(repositoryToSelect)) {
                                    tree.getSelectionModel().select(ti);
                                    repositoryToSelect = null;
                                    break;
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    public ResourceSelectionDialog(String title, 
                                   WsmoLiteDataServiceAsync _service,
                                   SelectedResourceCallback editor) {

        this.callback = editor;
        setHeading(title);
        isSaveDialog = title.toLowerCase().indexOf("save") != -1;
   
        setLayout(new BorderLayout());
        setClosable(false);
        
        BorderLayoutData layoutData = new BorderLayoutData(LayoutRegion.NORTH, 100);
        add(createTopPanel(), layoutData);

        layoutData = new BorderLayoutData(LayoutRegion.CENTER);
        add(initTree(), layoutData);
        
        add(createFilenameField(), new BorderLayoutData(LayoutRegion.SOUTH, 30));
         
        setButtons(Dialog.OKCANCEL);
        
        registerContolButtonsHandlers();
        processor = _service;
        setSize(400, 420);
        layout();
    }
    
    public String getSelectedResource() {
        return this.result;
    }
    
    private LayoutContainer createTopPanel() {
        
        LayoutContainer panel = new LayoutContainer(new RowLayout(Orientation.VERTICAL));
        panel.add(new Label("Storage Service URL : "), 
                new RowData(-1, -1, new Margins(10, 3, 3, 3)));
        
        this.storageURL = new TextField<String>();
        this.storageURL.setValue(serviceURL);
        panel.add(this.storageURL, new RowData(1, -1, new Margins(5)));
        
        Button retrieveContent = new Button("List");
        retrieveContent.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                if (storageURL.getValue() != null 
                        && storageURL.getValue().length() > 0) {
                    serviceURL = storageURL.getValue();
                }
                loader.load(null);
            }
        });
        LayoutContainer wrapper = new LayoutContainer();//new BorderLayout());
        wrapper.add(retrieveContent);//, new BorderLayoutData(LayoutRegion.EAST, 80));
        wrapper.setHeight(35);
        
        panel.add(wrapper, new RowData(1, 0.8, new Margins(1)));
        panel.layout();
        return panel;
    }
    
    private LayoutContainer createFilenameField() {
        LayoutContainer wrapper = new LayoutContainer(new RowLayout(Orientation.HORIZONTAL));
        
        wrapper.add(new Label("Filename: "), new RowData(65, -1, new Margins(6)));
        nameText = new TextField<String>();
        nameText.setReadOnly(false == isSaveDialog);
        wrapper.add(nameText, new RowData(1, 1, new Margins(3)));  
        return wrapper;
    }
    
    private LayoutContainer initTree() {
          
        RpcProxy<BaseTreeModel<TreeModel>, List<BaseTreeModel<TreeModel>>> proxy = new RpcProxy<BaseTreeModel<TreeModel>, List<BaseTreeModel<TreeModel>>>() {   
            @Override  
            protected void load(BaseTreeModel<TreeModel> loadConfig, final AsyncCallback<List<BaseTreeModel<TreeModel>>> callback) {   
                if (loadConfig == null) {
                    processor.listRepositories(storageURL.getValue(), new AsyncCallback<List<String>>() {
                        public void onFailure(Throwable caught) {
                            MessageBox.alert("Connection Error", caught.getMessage(), null);
                        }
                        public void onSuccess(List<String> result) {
                            List<BaseTreeModel<TreeModel>> r = new LinkedList<BaseTreeModel<TreeModel>>();
                            for(String s : result) {
                                r.add(new RepositoryNode(s, storageURL.getValue()));
                            }
                            Collections.sort(r, new Comparator<TreeModel>() {
    							public int compare(TreeModel o1, TreeModel o2) {
    								return ((RepositoryNode)o1).getName().compareToIgnoreCase(
    										((RepositoryNode)o2).getName());
    							}
    						});
                            callback.onSuccess(r);
                        }
                    });
                }
                else if (loadConfig instanceof RepositoryNode) {
                    processor.listRepositoryFiles(storageURL.getValue(), ((RepositoryNode) loadConfig).getName(), new AsyncCallback<Map<String, String>>() {
                        public void onFailure(Throwable caught) {
                            callback.onFailure(caught);
                        }
                        public void onSuccess(Map<String, String> result) {
                            List<BaseTreeModel<TreeModel>> r = new LinkedList<BaseTreeModel<TreeModel>>();
                            for(String s : result.keySet()) {
                                r.add(new DocumentNode(s, result.get(s)));
                            }
                            Collections.sort(r, new Comparator<TreeModel>() {
    							public int compare(TreeModel o1, TreeModel o2) {
    								return ((DocumentNode)o1).getName().compareToIgnoreCase(
    										((DocumentNode)o2).getName());
    							}
    						});
                            callback.onSuccess(r);
                        }
                    });
                }
            }   
          };   
        
          // tree loader   
          loader = new BaseTreeLoader(proxy) {   
            @Override  
            public boolean hasChildren(ModelData parent) {   
              return parent instanceof RepositoryNode;   
            }   
          };   
        
          // trees store   
          TreeStore<BaseTreeModel<TreeModel>> store = new TreeStore<BaseTreeModel<TreeModel>>(loader);   
  
          this.tree = new Tree();

          TreeBinder<BaseTreeModel<TreeModel>> binder = new TreeBinder<BaseTreeModel<TreeModel>>(tree, store);   
          binder.setIconProvider(new ModelStringProvider<BaseTreeModel<TreeModel>>() {   

              public String getStringValue(BaseTreeModel model, String property) {   
                  if (model instanceof RepositoryNode) {   
                      return moduleURLForIcons + "icons/rep.gif";
                	//  return "WSMOLiteModule/icons/rep.gif";
                  }
                  String fileName = ((DocumentNode)model).getName();
                  String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
                  if (ext.equalsIgnoreCase("wsdl")) {
                          
                	  return moduleURLForIcons + "icons/wsdl16.gif";
                    //  return "WSMOLiteModule/icons/wsdl16.gif";
                  }
                  return moduleURLForIcons + "icons/file.gif";
                //  return "WSMOLiteModule/icons/file.gif";
              }   

          });   
          binder.setDisplayProperty("name");   

        tree.setBorders(true);
    
        LayoutContainer wrapper = new LayoutContainer(new BorderLayout());
        wrapper.setBorders(true);
        wrapper.add(this.tree, new BorderLayoutData(LayoutRegion.CENTER));
        wrapper.setStyleAttribute("backgroundColor", "white"); 
        wrapper.setScrollMode(Scroll.AUTO);
        
        initTreeMenu();
        
        tree.addListener(Events.SelectionChange, new Listener<TreeEvent>() {

            public void handleEvent(TreeEvent be) {
                final TreeItem selected = tree.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    if (selected.getModel() instanceof DocumentNode) {    
                        nameText.setValue(selected.getModel().toString());
                    }
                    else {
                        if (false == isSaveDialog) {
                            nameText.setValue("");
                        }
                    }
                }
            }
            
        });
        tree.addListener(Events.OnDoubleClick, new Listener<TreeEvent>() {

            public void handleEvent(TreeEvent be) {
                if (false == tree.getSelectedItem().getModel() instanceof DocumentNode) {
                    return;
                }
                result = ((DocumentNode)tree.getSelectedItem().getModel()).getUrl();
                hide();
                callback.processSelection(result);            }
        });
        return wrapper;
    }
    
    public void reloadRepository(String repoID) {
        loader.load(null);
        repositoryToSelect = repoID;
        tree.getRootItem().addListener(Events.Add, new Listener<BaseEvent>() {
            public void handleEvent(BaseEvent be) {
                if (repositoryToSelect != null) {
                    List<TreeItem> ch = tree.getRootItem().getItems();
                    if (ch == null) {
                        return;
                    }
                    for(TreeItem ti : ch) {
                        if (ti.getModel().toString().equals(repositoryToSelect)) {
                            ti.setExpanded(true);
                            repositoryToSelect = null;
                            break;
                        }
                    }
                }
            }
        });

    }
    
    private void initTreeMenu() {
        Menu contextMenu = new Menu();
        contextMenu.setWidth(130);
        tree.setContextMenu(contextMenu);
                
        contextMenu.addListener(Events.BeforeShow,
                new Listener<MenuEvent>() {

            public void handleEvent(MenuEvent be) {
                be.container.removeAll();
                final TreeItem selected = tree.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    if (selected.getModel() instanceof DocumentNode) {
                        MenuItem menuItem = new MenuItem("Preview");
                        menuItem.setIconStyle("icon-preview");
                        menuItem.addSelectionListener(new SelectionListener<ComponentEvent>() {

                            @Override
                            public void componentSelected(ComponentEvent ce) {
                                ActionHandler.previewDocument(processor, selected, storageURL.getValue());
                            }
                        });
                        be.container.add(menuItem);
                        
                        be.container.add(new SeparatorMenuItem());
                        
                        menuItem = new MenuItem("Delete");
                        menuItem.setIconStyle("icon-close");
                        menuItem.addSelectionListener(new SelectionListener<ComponentEvent>() {
                            @Override
                            public void componentSelected(ComponentEvent ce) {
                            	MessageBox.confirm("Confirm Delete", 
                            			"Please confirm '" 
                            			+ ((DocumentNode)selected.getModel()).getName()+ "' deletion!", 
                            			new Listener<WindowEvent>() {  
                            		public void handleEvent(WindowEvent ce) {  
                            			if (ce.buttonClicked != null 
                            					&& "yes".equalsIgnoreCase(ce.buttonClicked.getText()))  
                            				ActionHandler.deleteResource(
                            						processor, 
                            						(DocumentNode)selected.getModel(),
                            						new AsyncCallback<Boolean>() {
                            							@Override
                            							public void onSuccess(Boolean result) {
                            								String repoID = selected.getParentItem().getModel().toString();
                            								reloadRepository(repoID);
                            								MessageBox.info("Delete Resource", "The resource has been deleted successfully!", null);
                            							}
                            							
                            							@Override
                            							public void onFailure(Throwable caught) {
                            								MessageBox.alert("Error", caught.getMessage(), null);
                            							}
                            						});  
                            		}  
                            	});
                                
                            }
                        });
                        be.container.add(menuItem);
                    }
                    else {
                        be.doit = false;
                    }
                }
                else {
                    be.doit = false;
                }
           }
        });
    }
    
    private void registerContolButtonsHandlers() {
        getButtonBar().getButtonById("ok").addSelectionListener(new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                if (isSaveDialog) {
                    
                    /*
                    if (tree.getSelectedItem() == null) {
                        MessageBox.alert("Error", "No repository selected!", null);
                        return;
                    }
                    if (nameText.getValue() == null 
                            || nameText.getValue().trim().length() == 0) {
                        MessageBox.alert("Error", "No file name provided!", null);
                        return;
                    }
                    */
                    ModelData repoNode = tree.getSelectedItem().getModel();
                    if (repoNode instanceof DocumentNode) {
                        repoNode = tree.getSelectedItem().getParentItem().getModel();
                    }
                    System.out.println("DARKO DEBUG");
                     System.out.println(repoNode.toString());
                    /*
                    result = ((RepositoryNode)repoNode).getUrl() 
                              + "repositories/" 
                              + URL.encode(((RepositoryNode)repoNode).getName())
                              + "/files/"
                              + URL.encode(nameText.getValue());
                              */
                    result = "OK";
                             
                }
                else {
                    if (tree.getSelectedItem() == null) {
                        MessageBox.alert("Error", "No resource selected!", null);
                        return;
                    }
                    if (false == tree.getSelectedItem().getModel() instanceof DocumentNode) {
                        MessageBox.alert("Error", "The selected item is not a resource!", null);
                        return;                    
                    }
                    else {
                        result = ((DocumentNode)tree.getSelectedItem().getModel()).getUrl();
                    }
                }
                hide();
                callback.processSelection(result);
            }
        });
        getButtonBar().getButtonById("ok").setText((isSaveDialog) ? "Save" : "Load");
       
        getButtonBar().getButtonById("cancel").addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                hide();
            }
        });
    }
     
    @SuppressWarnings("serial")
    class RepositoryNode extends BaseTreeModel<TreeModel>  implements Serializable {
        public RepositoryNode() {
        }
        public RepositoryNode(String name, String url) {
          set("name", name);
          if (false == url.trim().endsWith("/")) {
              url = url.trim() + "/";
          }
          set("url", url);
        }

        public String getName() {
          return (String) get("name");
        }
        public String getUrl() {
            return (String) get("url");
          }

        public String toString() {
          return getName();
        }
        public boolean isLeaf() {
            return false;
        }
    }
    class DocumentNode extends BaseTreeModel<TreeModel>  implements Serializable {
          
        public DocumentNode() {
        }
        public DocumentNode(String name, String url) {
          set("name", name);
          set("url", url);
        }
        
        public String getUrl() {
            return (String) get("url");
        }

        public String getName() {
          return (String) get("name");
        }

        public String toString() {
          return getName();
        }
        
        public boolean isLeaf() {
            return true;
        }
        
    }

}
