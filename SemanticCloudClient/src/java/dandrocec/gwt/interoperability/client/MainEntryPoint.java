/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dandrocec.gwt.interoperability.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Main entry point.
 *
 * @author Darko Androcec
 */
public class MainEntryPoint implements EntryPoint {

    /**
     * Creates a new instance of MainEntryPoint
     */
    final Frame frame1 = new Frame("http://localhost:8091/SowerWeb/");
    DecoratedTabPanel panel = new DecoratedTabPanel();
    final HorizontalPanel dropBoxPanel2 = new HorizontalPanel();
    final HorizontalPanel dropBoxPanel3 = new HorizontalPanel();
    final Button ok = new Button("Find a plan");
    final Button rebuild = new Button("Rebuild plan domain and goal");
    HorizontalPanel dropBoxPanel = new HorizontalPanel();
    final ListBox dropBox = new ListBox(false);
    VerticalPanel rpcResponsePanel = new VerticalPanel();
    Label rpcServerReplyLabel = new Label("Waiting for server reply...");
    Label rpcServerReplyLabel2 = new Label(" ");
    Label rpcServerReplyLabel3 = new Label(" ");
    final ListBox fromDropBox = new ListBox(false);
    final ListBox toDropBox = new ListBox(false);
    Button solve = new Button("Solve interoperability problem");
    final HorizontalPanel dataContainerNamePanel = new HorizontalPanel();
    final Label dataContainerNameLabel = new Label("Name of data container (e.g., entity, table, etc.):");
    TextBox dataContainerName = new TextBox();
    final HorizontalPanel addUserPanel = new HorizontalPanel();
    final Label addUserLabel = new Label("Name of user data container of target PaaS offer: ");
    TextBox addUserTextBox = new TextBox();

    public MainEntryPoint() {
    }

    public void onModuleLoad() {


        // Create a panel to align the Widgets
        FlowPanel vPanel = new FlowPanel();

        // Add a drop box with the list types

        dropBox.addItem("Migrate data", Constants.ACTION_MIGRATE_DATA);
        dropBox.addItem("Migrate data container (e.g., table, entity, etc.)", Constants.ACTION_MIGRATE_DATA_CONTAINER);
        dropBox.addItem("Add current user from one PaaS to another PaaS", Constants.ACTION_ADDUSER_TO_ANOTHERPAAS);
        dropBox.addItem("Other action");
        dropBox.ensureDebugId("cwListBox-dropBox");


        dropBoxPanel.setSpacing(4);
        dropBoxPanel.add(new HTML("<b>Choose action:</b>"));
        dropBoxPanel.add(dropBox);

        final Button next = new Button("next");
        // add click handler for next button
        addClickHandlerNextButton(next);


        dropBoxPanel.add(next);

        vPanel.add(dropBoxPanel);

        renderDropBoxToFromPaaSProviders();
        vPanel.add(dropBoxPanel2);

        renderDataContainerName();
        vPanel.add(dataContainerNamePanel);

        renderAddUser();
        vPanel.add(addUserPanel);

        rebuild.setVisible(false);
        addClickHandlerRebuildButton(rebuild);
        vPanel.add(rebuild);

        ok.setVisible(
                false);

        addClickHandlerOkButton(ok);


        vPanel.add(ok);

        renderRpcServerResponse();

        vPanel.add(rpcResponsePanel);

        dropBoxPanel3.add(rpcServerReplyLabel3);
        dropBoxPanel3.setVisible(false);
        vPanel.add(dropBoxPanel3);


        //---------------------------------
        panel.add(vPanel,
                "Interoperability actions");
        panel.add(frame1,
                "Manage semantic annotations");


        panel.selectTab(
                0);
        panel.setAnimationEnabled(
                true);

        RootPanel root = RootPanel.get();

        root.add(panel);
    }

    public void renderAddUser() {

        addUserLabel.setVisible(false);
        addUserTextBox.setVisible(false);
        addUserPanel.setSpacing(3);
        addUserPanel.add(addUserLabel);
        addUserPanel.add(addUserTextBox);

    }

    public void renderDataContainerName() {


        dataContainerNameLabel.setVisible(false);
        dataContainerName.setVisible(false);
        dataContainerNamePanel.setSpacing(3);
        dataContainerNamePanel.add(dataContainerNameLabel);
        dataContainerNamePanel.add(dataContainerName);

    }

    // panel consisting of from and to PaaS provider selects
    public void renderDropBoxToFromPaaSProviders() {

        fromDropBox.addItem(
                "Salesforce", "SalesForce");
        fromDropBox.addItem(
                "Microsoft Azure", "Azure");
        fromDropBox.addItem(
                "Google App Engine", "GoogleAppEngine");
        fromDropBox.ensureDebugId(
                "cwListBox-fromDropBox");

        dropBoxPanel2.setSpacing(
                4);
        dropBoxPanel2.add(
                new HTML("<b>From:</b>"));
        dropBoxPanel2.add(fromDropBox);

        toDropBox.addItem(
                "Google App Engine", "GoogleAppEngine");
        toDropBox.addItem(
                "Microsoft Azure", "Azure");
        toDropBox.addItem(
                "Salesforce", "SalesForce");

        toDropBox.ensureDebugId(
                "cwListBox-toDropBox");


        dropBoxPanel2.add(
                new HTML("<b>To:</b>"));
        dropBoxPanel2.add(toDropBox);

        dropBoxPanel2.setVisible(
                false);
    }

    public void renderRpcServerResponse() {
        rpcResponsePanel.add(rpcServerReplyLabel);
        rpcResponsePanel.add(rpcServerReplyLabel2);
        solve.setVisible(false);
        addClickHandlerSolveButton(solve);
        rpcResponsePanel.add(solve);
        rpcResponsePanel.setVisible(false);

    }

    // add click handler to Next button
    public void addClickHandlerNextButton(final Button next) {
        next.addClickHandler(
                new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        dropBoxPanel2.setVisible(true);
                        rebuild.setVisible(true);

                        int index = dropBox.getSelectedIndex();
                        String selectedValue = dropBox.getValue(index);
                        if (selectedValue.compareTo(Constants.ACTION_MIGRATE_DATA_CONTAINER) == 0) {
                            dataContainerNameLabel.setVisible(true);
                            dataContainerName.setVisible(true);
                        }

                        if (selectedValue.compareTo(Constants.ACTION_ADDUSER_TO_ANOTHERPAAS) == 0) {
                            addUserLabel.setVisible(true);
                            addUserTextBox.setVisible(true);
                        }

                        next.setVisible(false);
                    }
                });
    }

    // get GWT RPC Service
    public static GwtInteroperabilityServiceAsync getService() {

        return GWT.create(GwtInteroperabilityService.class);
    }

    // add click handler to Rebuild plan domain and goal action
    public void addClickHandlerRebuildButton(final Button rebuild) {

        final AsyncCallback<String> callback = new AsyncCallback<String>() {
            public void onSuccess(String result) {
                ok.setVisible(true);
                rpcServerReplyLabel.setText(result);
            }

            public void onFailure(Throwable caught) {
                rpcServerReplyLabel.setText("Communication with RPC server failed");
            }
        };

        rebuild.addClickHandler(
                new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        rpcResponsePanel.setVisible(true);
                        // Make remote call. Control flow will continue immediately and later
                        // 'callback' will be invoked when the RPC completes.
                        String aiGoal = constructAIGoal(dropBox.getValue(dropBox.getSelectedIndex()));
                        getService().prepareAiplanning(aiGoal, callback);


                    }
                });



    }

    // add click handler to Find a plan action 
    public void addClickHandlerOkButton(final Button ok) {

        final AsyncCallback<String> callback = new AsyncCallback<String>() {
            public void onSuccess(String result) {

                if (result.contains("There are interoperability problems!")) {

                    solve.setText("List interoperability problems");
                } else {
                    solve.setText("Execute action");
                }
                solve.setVisible(true);
                rpcServerReplyLabel.setText(result);
            }

            public void onFailure(Throwable caught) {
                rpcServerReplyLabel.setText("Communication with RPC server failed");
            }
        };

        final AsyncCallback<String> callback2 = new AsyncCallback<String>() {
            public void onSuccess(String result) {
                rpcServerReplyLabel2.setText(result);
                getService().doesPlanExist(callback);
            }

            public void onFailure(Throwable caught) {
                rpcServerReplyLabel2.setText("Communication with RPC server failed");
            }
        };

        ok.addClickHandler(
                new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        rpcResponsePanel.setVisible(true);
                        // Make remote call. Control flow will continue immediately and later
                        // 'callback' will be invoked when the RPC completes.
                        String aiGoal = constructAIGoal(dropBox.getValue(dropBox.getSelectedIndex()));
                        getService().executeAiplanning(aiGoal, callback2);


                    }
                });
    }

    // add click handler to List interoperability problems
    public void addClickHandlerSolveButton(final Button solve) {

        final AsyncCallback<String> callback2 = new AsyncCallback<String>() {
            public void onSuccess(String result) {
                rpcServerReplyLabel3.setText(result);

            }

            public void onFailure(Throwable caught) {
                rpcServerReplyLabel3.setText("Communication with RPC server failed");
            }
        };

        final AsyncCallback<String> callback = new AsyncCallback<String>() {
            public void onSuccess(String result) {

                if (solve.getText().compareTo("List interoperability problems") == 0) {

                    solve.setText("List interoperability problems");
                } else {
                    // Execute action
                    System.out.println("Result in MainEntryPoint = " + result);

                }
                solve.setVisible(false);
                rpcServerReplyLabel3.setText(result);
            }

            public void onFailure(Throwable caught) {
                rpcServerReplyLabel3.setText("Communication with RPC server failed");
            }
        };


        solve.addClickHandler(
                new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        dropBoxPanel3.setVisible(true);

                        System.out.println("DEBUG solve text " + solve.getText());

                        if (solve.getText().compareTo("Execute action") == 0) {
                            getService().executeFoundPlan(rpcServerReplyLabel2.getText(), callback2);
                        } else {
                            String aiGoal = constructAIGoal(dropBox.getValue(dropBox.getSelectedIndex()));
                            getService().getInteroperabilityProblems(aiGoal, callback);

                        }


                    }
                });
    }

    public String constructAIGoal(String action) {
        String aiGoal = "";

        // migrate data action
        if (action.compareTo(Constants.ACTION_MIGRATE_DATA) == 0) {
            aiGoal = "(" + action + " "
                    + fromDropBox.getValue(fromDropBox.getSelectedIndex()) + " "
                    + toDropBox.getValue(toDropBox.getSelectedIndex())
                    + ")";
        } else if (action.compareTo(Constants.ACTION_MIGRATE_DATA_CONTAINER) == 0) {
            aiGoal = "(" + Constants.ACTION_MIGRATE_DATA + " "
                    + fromDropBox.getValue(fromDropBox.getSelectedIndex()) + " "
                    + toDropBox.getValue(toDropBox.getSelectedIndex()) + " "
                    //    + dataContainerName.getValue()
                    + ")";

            // obrisati suvišne CSV-ove tako da se migrira samo ono potrebno
            String directory = "";
            if (fromDropBox.getValue(fromDropBox.getSelectedIndex()).toLowerCase().contains("google")) {
                directory = Constants.GOOGLE_APP_ENGINE_CSV_DIRECTORY;
            } else if (fromDropBox.getValue(fromDropBox.getSelectedIndex()).toLowerCase().contains("salesforce")) {
                directory = Constants.SALESFORCE_CSV_DIRECTORY;
            } else {
                directory = Constants.AZURE_CSV_DIRECTORY;
            }
            
            
            // call web service to remove unrelated CSV file - it will leave only relevant
            // CSV file of a chosen data container
            getService().removeUnrelatedCsvFiles(directory, dataContainerName.getValue(), null);
        } else if (action.compareTo(Constants.ACTION_ADDUSER_TO_ANOTHERPAAS) == 0) {
            aiGoal = "(" + action + " "
                    + fromDropBox.getValue(fromDropBox.getSelectedIndex()) + " "
                    + toDropBox.getValue(toDropBox.getSelectedIndex()) + " "
                    + addUserTextBox.getValue()
                    + ")";
        }


        return aiGoal;

    }
}
