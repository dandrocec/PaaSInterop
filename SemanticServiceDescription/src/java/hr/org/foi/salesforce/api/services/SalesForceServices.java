/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.salesforce.api.services;

import au.com.bytecode.opencsv.CSVReader;
import com.sforce.soap.enterprise.EnterpriseConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.ConnectionException;
import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.DataCategory;
import com.sforce.soap.enterprise.DataCategoryGroupSobjectTypePair;
import com.sforce.soap.enterprise.DeleteResult;
import com.sforce.soap.enterprise.DeletedRecord;
import com.sforce.soap.enterprise.DescribeDataCategoryGroupResult;
import com.sforce.soap.enterprise.DescribeDataCategoryGroupStructureResult;
import com.sforce.soap.enterprise.DescribeGlobalResult;
import com.sforce.soap.enterprise.DescribeGlobalSObjectResult;
import com.sforce.soap.enterprise.DescribeLayout;
import com.sforce.soap.enterprise.DescribeLayoutItem;
import com.sforce.soap.enterprise.DescribeLayoutResult;
import com.sforce.soap.enterprise.DescribeLayoutRow;
import com.sforce.soap.enterprise.DescribeLayoutSection;
import com.sforce.soap.enterprise.DescribeSObjectResult;
import com.sforce.soap.enterprise.DescribeSearchScopeOrderResult;
import com.sforce.soap.enterprise.DescribeSoftphoneLayoutCallType;
import com.sforce.soap.enterprise.DescribeSoftphoneLayoutInfoField;
import com.sforce.soap.enterprise.DescribeSoftphoneLayoutItem;
import com.sforce.soap.enterprise.DescribeSoftphoneLayoutResult;
import com.sforce.soap.enterprise.DescribeSoftphoneLayoutSection;
import com.sforce.soap.enterprise.DescribeTab;
import com.sforce.soap.enterprise.DescribeTabSetResult;
import com.sforce.soap.enterprise.EmailPriority;
import com.sforce.soap.enterprise.EmptyRecycleBinResult;
import com.sforce.soap.enterprise.Field;
import com.sforce.soap.enterprise.FieldType;
import com.sforce.soap.enterprise.GetDeletedResult;
import com.sforce.soap.enterprise.GetServerTimestampResult;
import com.sforce.soap.enterprise.GetUpdatedResult;
import com.sforce.soap.enterprise.GetUserInfoResult;
import com.sforce.soap.enterprise.InvalidateSessionsResult;
import com.sforce.soap.enterprise.LeadConvert;
import com.sforce.soap.enterprise.LeadConvertResult;
import com.sforce.soap.enterprise.MergeRequest;
import com.sforce.soap.enterprise.MergeResult;
import com.sforce.soap.enterprise.PicklistEntry;
import com.sforce.soap.enterprise.ProcessResult;
import com.sforce.soap.enterprise.ProcessSubmitRequest;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.ResetPasswordResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.SearchRecord;
import com.sforce.soap.enterprise.SearchResult;
import com.sforce.soap.enterprise.SendEmailResult;
import com.sforce.soap.enterprise.SetPasswordResult;
import com.sforce.soap.enterprise.SingleEmailMessage;
import com.sforce.soap.enterprise.UndeleteResult;
import com.sforce.soap.enterprise.UpsertResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.metadata.AsyncResult;
import com.sforce.soap.metadata.CustomField;
import com.sforce.soap.metadata.CustomObject;
import com.sforce.soap.metadata.DeploymentStatus;
import com.sforce.soap.metadata.DescribeMetadataObject;
import com.sforce.soap.metadata.DescribeMetadataResult;
import com.sforce.soap.metadata.Metadata;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.metadata.SharingModel;
import com.sforce.soap.metadata.UpdateMetadata;
import com.sforce.soap.partner.PartnerConnection;
import hr.org.foi.jena.DataOntologyReader;
import hr.org.foi.jena.DataTypeConverter;
import hr.org.foi.jena.SalesforceDataOntologyCreator;
import hr.org.foi.jena.TableUtil;
import hr.org.foi.jena.TypeConstants;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.jws.WebParam;

/**
 *
 * @author Darko Androcec
 */
@WebService(serviceName = "SalesForceServices")
public class SalesForceServices {

    static EnterpriseConnection conn;
    static PartnerConnection partnerConnection = null;
    String authEndPoint = "url";

    // START: CALL BASIC OPERATIONS OF SALEFORCE SOAP API
    /*
     convertLead() 
     Converts a Lead into an Account, Contact, or (optionally) an Opportunity.
     LeadConvertResult[] = connection.convertLead(leadConverts LeadConvert[]);
     **/
    @WebMethod(operationName = "convertLeadRecords")
    public String convertLeadRecords(@WebParam(name = "leadsToConvert") LeadConvert[] leadsToConvert) {
        String result = "";

        try {

// Convert the leads and iterate through the results
            LeadConvertResult[] lcResults = conn.convertLead(leadsToConvert);
            for (int j = 0; j < lcResults.length; ++j) {
                if (lcResults[j].isSuccess()) {
                    result = result + "\n" + "Lead converted successfully!";
                    result = result + "Account ID: " + lcResults[j].getAccountId();
                    result = result + "Contact ID: " + lcResults[j].getContactId();
                    result = result + "Opportunity ID: "
                            + lcResults[j].getOpportunityId() + "\n";
                } else {
                    result = result + "\nError converting new Lead: "
                            + lcResults[j].getErrors()[0].getMessage() + "\n";
                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }
        return result;
    }

    /*
     * create() Adds one or more new individual objects to your organization’s data.
     SaveResult[] = connection.create(sObject[] sObjects);

     */
    @WebMethod(operationName = "create")
    public String create(@WebParam(name = "sObjects") SObject[] sObjects) {
        String result = "";

        try {
            SaveResult[] saveResults = conn.create(sObjects);
            for (int i = 0; i < saveResults.length; i++) {
                if (saveResults[i].isSuccess()) {
                    result = "The objects are successfully created. ";

                } else {
                    result = " The error reported was: "
                            + saveResults[i].getErrors()[0].getMessage() + "\n";
                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        return result;
    }

    /**
     * delete() Deletes one or more records from your organization’s data.
     * Syntax DeleteResult[] = connection.delete(ID[] ids);
     */
    @WebMethod(operationName = "delete")
    public String delete(@WebParam(name = "ids") String[] ids) {
        String result = "";

        try {
            DeleteResult[] deleteResults = conn.delete(ids);
            for (int i = 0; i < deleteResults.length; i++) {
                DeleteResult deleteResult = deleteResults[i];
                if (deleteResult.isSuccess()) {
                    result = "Deleted Record ID: " + deleteResult.getId() + "\n";
                } else {
                    result = result + "Error: could not delete " + "Record ID "
                            + deleteResult.getId()
                            + "\n";
                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        return result;
    }

    /**
     * emptyRecycleBin() Delete records from the recycle bin immediately.
     */
    @WebMethod(operationName = "emptyRecycleBin")
    public String emptyRecycleBin(@WebParam(name = "ids") String[] ids) {
        String result = "";

        try {
            EmptyRecycleBinResult[] emptyRecycleBinResults = conn.emptyRecycleBin(ids);
            for (int i = 0; i < emptyRecycleBinResults.length; i++) {
                EmptyRecycleBinResult emptyRecycleBinResult = emptyRecycleBinResults[i];
                if (emptyRecycleBinResult.isSuccess()) {
                    result = result + "Recycled ID: "
                            + emptyRecycleBinResult.getId() + "\n";
                } else {
                    com.sforce.soap.enterprise.Error[] errors = emptyRecycleBinResult.getErrors();
                    if (errors.length > 0) {
                        result = result + "Error code: " + errors[0].getStatusCode();
                        result = result + "Error message: " + errors[0].getMessage() + "\n";
                    }
                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        return result;
    }

    /**
     * getDeleted() Retrieves the IDs of individual objects of the specified
     * object that have been deleted since the specified time. For information
     * on IDs, see ID Field Type. GetDeletedResult =
     * connection.getDeleted(string sObjectType, dateTime startDate, dateTime
     * EndDate);
     *
     */
    @WebMethod(operationName = "getDeleted")
    public String getDeleted(@WebParam(name = "objectType") String objectType, @WebParam(name = "startDate") Calendar startDate, @WebParam(name = "endDate") Calendar endDate) {
        String result = "";

        try {

            GetDeletedResult gdResult = conn.getDeleted(objectType,
                    startDate, endDate);

            DeletedRecord[] deletedRecords = gdResult.getDeletedRecords();
            if (deletedRecords != null && deletedRecords.length > 0) {
                for (int i = 0; i < deletedRecords.length; i++) {
                    DeletedRecord dr = deletedRecords[i];
                    result = result + dr.getId() + " was deleted on "
                            + dr.getDeletedDate().getTime().toString() + "\n";
                }
            } else {
                result = result + "No deletions of " + objectType + " records from "
                        + startDate + "to" + endDate;
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        return result;
    }

    /**
     * getUpdated() Retrieves the list of individual objects that have been
     * updated (added or changed) within the given timespan for the specified
     * object.
     *
     * Core Calls GetDeletedResult Syntax GetUpdatedResult[] =
     * connection.getUpdated(string sObjectType, dateTime startDate, dateTime
     * EndDate);
     *
     */
    @WebMethod(operationName = "getUpdated")
    public String getUpdated(@WebParam(name = "objectType") String objectType, @WebParam(name = "startDate") Calendar startDate, @WebParam(name = "endDate") Calendar endDate) {
        String result = "";

        try {

            GetUpdatedResult uResult = conn.getUpdated(objectType,
                    startDate, endDate);

            if (uResult.getIds() != null && uResult.getIds().length > 0) {
                for (int i = 0; i < uResult.getIds().length; i++) {
                    result = result + uResult.getIds()[i] + " was updated between "
                            + startDate.getTime().toString() + " and "
                            + endDate.getTime().toString();
                }
            } else {
                result = result + "No updates of " + objectType + " records from "
                        + startDate + "to" + endDate;
            }

        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        return result;
    }

    /**
     * invalidateSessions() Ends one or more sessions specified by a sessionId.
     * Syntax InvalidateSessionsResult = connection.invalidateSessions(string[]
     * sessionIds);
     */
    @WebMethod(operationName = "invalidateSessions")
    public String invalidateSessions(@WebParam(name = "sessionIds") String[] sessionIds) {
        String finalResult = "";

        try {
            InvalidateSessionsResult[] results;
            results = conn.invalidateSessions(sessionIds);
            for (InvalidateSessionsResult result : results) {

                if (!result.isSuccess()) {
                    if (result.getErrors().length > 0) {
                        finalResult = finalResult + "Status code: "
                                + result.getErrors()[0].getStatusCode();
                        finalResult = finalResult + "Error message: "
                                + result.getErrors()[0].getMessage() + "\n";
                    }
                } else {
                    finalResult = finalResult + "Success.";
                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        return finalResult;
    }
    /*
     * login() Logs in to the login server and starts a client session.
     LoginResult = connection.login(string username, string password);
     */

    @WebMethod(operationName = "login")
    public String login() {

        ConnectorConfig config = new ConnectorConfig();
        config.setUsername(Constants.SALESFORCE_USERNAME);
        config.setPassword(Constants.SALESFORCE_PASSWORD);
        config.setTraceMessage(true);

        try {

            conn = Connector.newConnection(config);

            // display some current settings
            System.out.println("Auth EndPoint: " + config.getAuthEndpoint());
            System.out.println("Service EndPoint: " + config.getServiceEndpoint());
            System.out.println("Username: " + config.getUsername());
            System.out.println("SessionId: " + config.getSessionId());

        } catch (ConnectionException e1) {
            e1.printStackTrace();
        }


        return "Login successed";
    }

    // login for partner connection to Salesforce
    // it enables us to dynamicaly create, delete, and updates custom objects
    @WebMethod(operationName = "loginParnerConnection")
    public String loginParnerConnection() {

        String username = Constants.SALESFORCE_USERNAME;
        String password = Constants.SALESFORCE_PASSWORD;
        String authEndPoint = Constants.SALESFORCE_AUTH_ENPOINT_FOR_PARTNER;

        try {
            ConnectorConfig config = new ConnectorConfig();
            config.setUsername(username);
            config.setPassword(password);

            config.setAuthEndpoint(authEndPoint);
            config.setTraceFile("traceLogs.txt");
            config.setTraceMessage(true);
            config.setPrettyPrintXml(true);

            partnerConnection = new PartnerConnection(config);

        } catch (ConnectionException ce) {
            ce.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }

        return "Partner login successed";
    }

    /**
     * logout() Ends the session of the logged-in user. connection.logout();
     *
     */
    @WebMethod(operationName = "logout")
    public String logout() {
        try {

            conn.logout();
        } catch (ConnectionException ex) {
            Logger.getLogger(SalesForceServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "Logout successed";
    }

    @WebMethod(operationName = "logoutPartnerConnection")
    public String logoutPartnerConnection() {
        try {

            if (partnerConnection != null) {
                partnerConnection.logout();
            }
        } catch (ConnectionException ex) {
            Logger.getLogger(SalesForceServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "Logout successed";
    }

    /**
     * merge() Merges records of the same object type. MergeResult[]=
     * connection.merge(MergeRequest[] mergeRequests);
     *
     */
    @WebMethod(operationName = "merge")
    public String merge(@WebParam(name = "mergeRequests") MergeRequest[] mergeRequests) {
        String finalResult = "";

        try {
            MergeResult[] mRes = conn.merge(mergeRequests);

            for (MergeResult result : mRes) {

                if (result.isSuccess()) {
                    finalResult = finalResult + "Merge successful.";

                } else {
                    finalResult = finalResult + "Failed to merge records. Error message is: "
                            + result.getErrors().toString();

                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }
        return finalResult;
    }

    /**
     * process() Submits an array of approval process instances for approval, or
     * processes an array of approval process instances to be approved,
     * rejected, or removed. For more information, see “Getting Started with
     * Approval Processes” in the Salesforce online help. Syntax ProcessResult =
     * connection.process( processType processRequest[]) processType can be
     * either ProcessSubmitRequest or ProcessWorkitemRequest
     *
     */
    @WebMethod(operationName = "process")
    public String process(@WebParam(name = "id") String id, @WebParam(name = "approverIds") String[] approverIds) {

        String finalResult = "";

        ProcessSubmitRequest request = new ProcessSubmitRequest();
        request.setComments("A comment about this approval.");
        request.setObjectId(id);
        request.setNextApproverIds(approverIds);
        try {
            ProcessResult[] processResults = conn.process(new ProcessSubmitRequest[]{request});
            for (ProcessResult processResult : processResults) {
                if (processResult.isSuccess()) {
                    finalResult = finalResult + "Approval submitted for: " + id + ":";
                    for (int i = 0; i < approverIds.length; i++) {
                        finalResult = finalResult + "\tBy: " + approverIds[i] + " successful.";
                    }
                    finalResult = finalResult + "Process Instance Status: "
                            + processResult.getInstanceStatus();
                } else {
                    finalResult = finalResult + "Approval submitted for: " + id
                            + ", approverIds: " + approverIds.toString() + " FAILED.";
                    finalResult = finalResult + "Error: "
                            + processResult.getErrors().toString();
                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }


        return finalResult;
    }

    /*
     * query()
     Executes a query against the specified object and returns data that matches the specified
     criteria.
     QueryResult = connection.query(string queryString);

     */
    // Salesforce koristi Salesforce Object Query Language (SOQL)
    @WebMethod(operationName = "query")
    public String query(@WebParam(name = "query") String query) {

        QueryResult queryResult = null;
        try {

            queryResult = conn.query(query);
        } catch (ConnectionException ex) {
            Logger.getLogger(SalesForceServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (queryResult != null) {

            SalesforceDataOntologyCreator.getListOfEntityList().add(queryResult.getRecords());


            return queryResult.toString();
        }
        return "Empty result";
    }

    /*
     * queryAll() Same as query(), but includes deleted and archived items.
     QueryResult = connection.queryAll(string queryString);

     */
    @WebMethod(operationName = "queryAll")
    public String queryAll(@WebParam(name = "query") String query) {

        QueryResult queryResult = null;
        try {

            queryResult = conn.queryAll(query);
        } catch (ConnectionException ex) {
            Logger.getLogger(SalesForceServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (queryResult != null) {

            SalesforceDataOntologyCreator.getListOfEntityList().add(queryResult.getRecords());


            return queryResult.toString();
        }
        return "Empty result";
    }

    // For now, I didn't implement queryMore API call
    /*
     * queryMore() Retrieves the next batch of objects from a query.
     QueryResult = connection.queryMore( QueryLocator QueryLocator);

     */
    /*
     * retrieve() Retrieves one or more objects based on the specified object IDs.
     sObject[] result = connection.retrieve(string fieldList, string sObjectType, ID ids[]);

     */
    @WebMethod(operationName = "retrieve")
    public String retrieve(@WebParam(name = "fieldList") String fieldList, @WebParam(name = "objectType") String objectType, @WebParam(name = "ids") String[] ids) {

        String finalResult = "";

        try {
            SObject[] sObjects = conn.retrieve(fieldList,
                    objectType, ids);

            if (sObjects != null) {
                for (int i = 0; i < sObjects.length; i++) {

                    SObject object = sObjects[i];
                    finalResult = finalResult + object.toString();
                }
            } else {
                finalResult = "None of the object were retrieved!";
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        return finalResult;
    }

    /*
     * search() Executes a text search in your organization’s data.
     SearchResult = connection.search(String searchString);

     */
    // Perform the search using the SOSL query.
    @WebMethod(operationName = "search")
    public String search(@WebParam(name = "searchString") String searchString) {
        String result = "";
        try {

            SearchResult sr = conn.search(
                    searchString);
// Get the records from the search results.
            SearchRecord[] records = sr.getSearchRecords();

            if (records.length > 0) {
                for (int i = 0; i < records.length; i++) {
                    SObject record = records[i].getRecord();
                    result = result + record.toString() + "\n";
                }

            } else {
                result = result + "No records were found for the search.";
            }
        } catch (Exception ce) {
            ce.printStackTrace();
        }

        return result;
    }

    /**
     * undelete() Undelete records identified with queryAll(). UndeleteResult[]
     * = connection.undelete(ID[] ids );
     *
     */
    @WebMethod(operationName = "undelete")
    public String undelete(@WebParam(name = "ids") String[] ids) {
        String finalResult = "";

        try {

            UndeleteResult[] undelResults = conn.undelete(ids);
            for (UndeleteResult result : undelResults) {
                if (result.isSuccess()) {
                    finalResult = finalResult + "Undeleted Account ID: " + result.getId() + "\n";
                } else {
                    if (result.getErrors().length > 0) {
                        finalResult = finalResult + "Error message: "
                                + result.getErrors()[0].getMessage() + "\n";
                    }
                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }
        return finalResult;
    }

    /*
     * update()
     Updates one or more existing records in your organization’s data.
     Syntax
     SaveResult[] = connection.update(sObject[] sObjects);

     */
    @WebMethod(operationName = "update")
    public String update(@WebParam(name = "sObjects") SObject[] sObjects) {
        String finalResult = "";

        // Invoke the update call and save the results
        try {
            SaveResult[] saveResults = conn.update(sObjects);
            for (SaveResult saveResult : saveResults) {
                if (saveResult.isSuccess()) {
                    finalResult = finalResult + "Successfully updated object ID: "
                            + saveResult.getId() + "\n";
                } else {

                    com.sforce.soap.enterprise.Error[] errors = saveResult.getErrors();
                    if (errors.length > 0) {
                        finalResult = finalResult + "Error: could not update " + "Account ID "
                                + saveResult.getId() + ".";
                        finalResult = finalResult + "\tThe error reported was: ("
                                + errors[0].getStatusCode() + ") "
                                + errors[0].getMessage() + "." + "\n";
                    }
                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        return finalResult;
    }

    /*
     * upsert() Creates new objects and updates existing objects; matches on a custom field to determine
     the presence of existing objects.
     UpsertResult[] = connection.upsert(String externalIdFieldName, sObject[] sObjects);


     */
    @WebMethod(operationName = "upsert")
    public String upsert(@WebParam(name = "externalIdFieldName") String externalIdFieldName, @WebParam(name = "sObjects") SObject[] sObjects) {
        String finalResult = "";

        try {
// Invoke the upsert call and save the results.
// Use External_Id custom field for matching records.
            UpsertResult[] upsertResults = conn.upsert(
                    externalIdFieldName, sObjects);
            for (UpsertResult result : upsertResults) {
                if (result.isSuccess()) {
                    finalResult = finalResult + "\nUpsert succeeded.";
                    finalResult = finalResult + (result.isCreated() ? "Insert" : "Update")
                            + " was performed.";
                    finalResult = finalResult + "Object ID: " + result.getId() + "\n";
                } else {
                    finalResult = finalResult + "The Upsert failed because: "
                            + result.getErrors()[0].getMessage();
                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        return finalResult;
    }

// END: CALL BASIC OPERATIONS OF SALEFORCE SOAP API
// START: DESCRIBE CALL OPERATIONS OF SALEFORCE SOAP API
    /**
     * describeGlobal() Retrieves a list of available objects for your
     * organization’s data. DescribeGlobalResult = connection.describeGlobal();
     *
     */
    @WebMethod(operationName = "describeGlobal")
    public String describeGlobal() {

        String result = "";
        //TODO write your implementation code here:
        try {
            DescribeGlobalResult describeGlobalResult =
                    conn.describeGlobal();
            DescribeGlobalSObjectResult[] sobjectResults =
                    describeGlobalResult.getSobjects();


            for (int i = 0; i < sobjectResults.length; i++) {
                // get object names
                result = result + sobjectResults[i].getName();
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        return result;
    }

    /**
     * describeDataCategoryGroups() Retrieves available category groups for
     * entities specified in the request. DescribeDataCategoryGroupResult[] =
     * connection.describeDataCategoryGroups()(string[] sObjectTypes);
     */
    @WebMethod(operationName = "describeDataCategoryGroups")
    public String describeDataCategoryGroups(@WebParam(name = "sObjectTypes") String[] sObjectTypes) {
        String finalResult = "";

        try {
// Make the describe call for data category groups
            DescribeDataCategoryGroupResult[] results =
                    conn.describeDataCategoryGroups(sObjectTypes);
// Get the properties of each data category group
            for (int i = 0; i < results.length; i++) {
                finalResult = finalResult + "sObject: "
                        + results[i].getSobject();
                finalResult = finalResult + "Group name: "
                        + results[i].getName();
                finalResult = finalResult + "Group label: "
                        + results[i].getLabel();
                finalResult = finalResult + "Group description: "
                        + (results[i].getDescription() == null ? ""
                        : results[i].getDescription());
                finalResult = finalResult + "Number of categories: "
                        + results[i].getCategoryCount() + "\n";
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        return finalResult;
    }

    /**
     * describeDataCategoryGroupStructures()Retrieves available category groups
     * along with their data category structure for entities specified in the
     * request. DescribeDataCategoryGroupStructureResult[] = connection.
     * describeDataCategoryGroupStructures()(DataCategoryGroupSObjectTypePair[]
     * pairs, boolean topCategoriesOnly)
     *
     */
    @WebMethod(operationName = "describeDataCategoryGroupStructures")
    public String describeDataCategoryGroupStructures(@WebParam(name = "pairs") DataCategoryGroupSobjectTypePair[] pairs,
            @WebParam(name = "topCategoriesOnly") boolean topCategoriesOnly) {
        String finalResult = "";
        try {
            // Get the list of top level categories using the describe call
            DescribeDataCategoryGroupStructureResult[] results =
                    conn.describeDataCategoryGroupStructures(
                    pairs,
                    topCategoriesOnly);
            // Iterate through each result and get some properties
// including top categories and child categories
            for (int i = 0; i < results.length; i++) {
                DescribeDataCategoryGroupStructureResult result =
                        results[i];

                finalResult = finalResult + "Group name: " + result.getName();
                finalResult = finalResult + "Group label: " + result.getLabel();
                finalResult = finalResult + "Group description: "
                        + result.getDescription() + "\n";
// Get the top-level categories
                DataCategory[] topCategories = result.getTopCategories();
// Iterate through the top level categories and retrieve
// some information
                for (int j = 0; j < topCategories.length; j++) {
                    DataCategory topCategory = topCategories[j];
                    finalResult = finalResult + "Category name: "
                            + topCategory.getName();
                    finalResult = finalResult + "Category label: "
                            + topCategory.getLabel();
                    DataCategory[] childCategories =
                            topCategory.getChildCategories();
                    finalResult = finalResult + "Child categories: ";
                    for (int k = 0; k < childCategories.length; k++) {
                        finalResult = finalResult + "\t" + k + ". Category name: "
                                + childCategories[k].getName();
                        finalResult = finalResult + "\t" + k + ". Category label: "
                                + childCategories[k].getLabel();
                    }
                }
                finalResult = finalResult + "\n";
            }
        } catch (ConnectionException ex) {
            Logger.getLogger(SalesForceServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        return finalResult;

    }

    /**
     * describeLayout() Retrieves metadata about page layouts for the specified
     * object type. DescribeLayoutResult = connection.describeLayout(string
     * sObjectType, ID recordTypeID[]);
     *
     *
     */
    @WebMethod(operationName = "describeLayout")
    public String describeLayout(@WebParam(name = "objectType") String objectType) {

        String finalResult = "";

        try {

            DescribeLayoutResult dlr =
                    conn.describeLayout(objectType, null);
            finalResult = finalResult + "There are " + dlr.getLayouts().length
                    + " layouts for the " + objectType + " object.";
// Get all the layouts for the sObject
            for (int i = 0; i < dlr.getLayouts().length; i++) {
                DescribeLayout layout = dlr.getLayouts()[i];
                DescribeLayoutSection[] detailLayoutSectionList =
                        layout.getDetailLayoutSections();
                finalResult = finalResult + " There are "
                        + detailLayoutSectionList.length
                        + " detail layout sections";
                DescribeLayoutSection[] editLayoutSectionList =
                        layout.getEditLayoutSections();
                finalResult = finalResult + " There are "
                        + editLayoutSectionList.length
                        + " edit layout sections";
// Write the headings of the detail layout sections
                for (int j = 0; j < detailLayoutSectionList.length; j++) {
                    finalResult = finalResult + j
                            + " This detail layout section has a heading of "
                            + detailLayoutSectionList[j].getHeading();
                }
// Write the headings of the edit layout sections
                for (int x = 0; x < editLayoutSectionList.length; x++) {
                    finalResult = finalResult + x
                            + " This edit layout section has a heading of "
                            + editLayoutSectionList[x].getHeading();
                }
// For each edit layout section, get its details.
                for (int k = 0; k < editLayoutSectionList.length; k++) {
                    DescribeLayoutSection els =
                            editLayoutSectionList[k];
                    finalResult = finalResult + "Edit layout section heading: "
                            + els.getHeading();
                    DescribeLayoutRow[] dlrList = els.getLayoutRows();
                    finalResult = finalResult + "This edit layout section has "
                            + dlrList.length + " layout rows.";
                    for (int m = 0; m < dlrList.length; m++) {
                        DescribeLayoutRow lr = dlrList[m];
                        finalResult = finalResult + " This row has "
                                + lr.getNumItems() + " layout items.";
                        DescribeLayoutItem[] dliList = lr.getLayoutItems();
                        for (int n = 0; n < dliList.length; n++) {
                            DescribeLayoutItem li = dliList[n];
                            if ((li.getLayoutComponents() != null)
                                    && (li.getLayoutComponents().length > 0)) {
                                finalResult = finalResult + "\tLayout item " + n
                                        + li.getLayoutComponents()[0].getValue();
                            } else {
                                finalResult = finalResult + "\tLayout item " + n
                                        + ", no layout component";
                            }
                        }
                    }
                }
            }
// Get record type mappings
            if (dlr.getRecordTypeMappings() != null) {
                finalResult = finalResult + "There are "
                        + dlr.getRecordTypeMappings().length
                        + " record type mappings for the "
                        + objectType + " object";
            } else {
                finalResult = finalResult
                        + "There are no record type mappings for the "
                        + objectType + " object.";
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        return finalResult;
    }

    /**
     * describeSearchScopeOrder() Retrieves an ordered list of objects in the
     * logged-in user’s default global search scope.
     * DescribeSearchScopeOrderResult[] describeSearchScopeOrderResults =
     * connection.describeSearchScopeOrder();
     *
     *
     *
     */
    @WebMethod(operationName = "describeSearchScopeOrder")
    public String describeSearchScopeOrder() {

        String finalResult = "";

        try {
//Get the order of objects in search smart scope for the logged-in user
            DescribeSearchScopeOrderResult[] describeSearchScopeOrderResults =
                    conn.describeSearchScopeOrder();
//Iterate through the results and display the name of each object
            for (int i = 0; i < describeSearchScopeOrderResults.length; i++) {
                finalResult = finalResult + describeSearchScopeOrderResults[i].getName();
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        return finalResult;
    }

    /**
     * describeSObject()Retrieves metadata (field list and object properties)
     * for the specified object type. Superseded by describeSObjects().
     * DescribeSObjectResult = connection.describeSObject(string sObjectType);
     *
     *
     *
     */
    @WebMethod(operationName = "describeSObject")
    public String describeSObject(@WebParam(name = "objectType") String objectType) {

        String finalResult = "";

        try {
// Make the describe call
            DescribeSObjectResult describeSObjectResult =
                    conn.describeSObject(objectType);
// Get sObject metadata
            if (describeSObjectResult != null) {
                finalResult = finalResult + "sObject name: "
                        + describeSObjectResult.getName();
                if (describeSObjectResult.isCreateable()) {
                    finalResult = finalResult + " Createable";
                }
// Get the fields
                Field[] fields = describeSObjectResult.getFields();
                finalResult = finalResult + "Has " + fields.length + " fields";
// Iterate through each field and gets its properties
                for (int i = 0; i < fields.length; i++) {

                    Field field = fields[i];
                    finalResult = finalResult + "Field name: " + field.getName();
                    finalResult = finalResult + "Field label: " + field.getLabel();
// If this is a picklist field, show the picklist values
                    if (field.getType().equals(FieldType.picklist)) {
                        PicklistEntry[] picklistValues =
                                field.getPicklistValues();
                        if (picklistValues != null) {
                            finalResult = finalResult + "Picklist values: ";
                            for (int j = 0; j < picklistValues.length; j++) {
                                if (picklistValues[j].getLabel() != null) {
                                    finalResult = finalResult + "\tItem: "
                                            + picklistValues[j].getLabel();
                                }
                            }
                        }
                    }
// If a reference field, show what it references
                    if (field.getType().equals(FieldType.reference)) {
                        finalResult = finalResult + "Field references the "
                                + "following objects:";
                        String[] referenceTos = field.getReferenceTo();
                        for (int j = 0; j < referenceTos.length; j++) {
                            finalResult = finalResult + "\t" + referenceTos[j];
                        }
                    }
                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        return finalResult;
    }

    /**
     * describeSObjects() An array-based version of describeSObject.
     * DescribeSObjectResult [] = connection.describeSObjects(string
     * sObjectType[] );
     *
     *
     *
     *
     */
    @WebMethod(operationName = "describeSObjects")
    public String describeSObjects(@WebParam(name = "objectTypes") String[] objectTypes) {

        String finalResult = "";

        try {
// Call describeSObjectResults and pass it an array with
// the names of the objects to describe.
            DescribeSObjectResult[] describeSObjectResults =
                    conn.describeSObjects(
                    objectTypes);
// Iterate through the list of describe sObject results
            for (int k = 0; k < describeSObjectResults.length; k++) {
                DescribeSObjectResult describeSObjectResult = describeSObjectResults[k];

// Get sObject metadata
                if (describeSObjectResult != null) {
                    finalResult = finalResult + "sObject name: "
                            + describeSObjectResult.getName();
                    if (describeSObjectResult.isCreateable()) {
                        finalResult = finalResult + " Createable";
                    }
// Get the fields
                    Field[] fields = describeSObjectResult.getFields();
                    finalResult = finalResult + "Has " + fields.length + " fields";
// Iterate through each field and gets its properties
                    for (int i = 0; i < fields.length; i++) {

                        Field field = fields[i];
                        finalResult = finalResult + "Field name: " + field.getName();
                        finalResult = finalResult + "Field label: " + field.getLabel();
// If this is a picklist field, show the picklist values
                        if (field.getType().equals(FieldType.picklist)) {
                            PicklistEntry[] picklistValues =
                                    field.getPicklistValues();
                            if (picklistValues != null) {
                                finalResult = finalResult + "Picklist values: ";
                                for (int j = 0; j < picklistValues.length; j++) {
                                    if (picklistValues[j].getLabel() != null) {
                                        finalResult = finalResult + "\tItem: "
                                                + picklistValues[j].getLabel();
                                    }
                                }
                            }
                        }
// If a reference field, show what it references
                        if (field.getType().equals(FieldType.reference)) {
                            finalResult = finalResult + "Field references the "
                                    + "following objects:";
                            String[] referenceTos = field.getReferenceTo();
                            for (int j = 0; j < referenceTos.length; j++) {
                                finalResult = finalResult + "\t" + referenceTos[j];
                            }
                        }
                    }
                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        return finalResult;

    }

    /**
     * describeSoftphoneLayout() Describes the softPhone layout(s) created for
     * an organization. DescribeSoftphoneLayoutResult[] =
     * connection.describeSoftphoneLayout();
     *
     */
    @WebMethod(operationName = "describeSoftphoneLayout")
    public String describeSoftphoneLayout() {

        String finalResult = "";

        try {
            DescribeSoftphoneLayoutResult result =
                    conn.describeSoftphoneLayout();
            finalResult = finalResult + "ID of retrieved Softphone layout: "
                    + result.getId();
            finalResult = finalResult + "Name of retrieved Softphone layout: "
                    + result.getName();
            finalResult = finalResult + "\nContains following "
                    + "Call Type Layouts\n";
            for (DescribeSoftphoneLayoutCallType type :
                    result.getCallTypes()) {
                finalResult = finalResult + "Layout for " + type.getName()
                        + " calls";
                finalResult = finalResult + "\tCall-related fields:";
                for (DescribeSoftphoneLayoutInfoField field :
                        type.getInfoFields()) {
                    finalResult = finalResult + "\t\t{" + field.getName();
                }
                finalResult = finalResult + "\tDisplayed Objects:";
                for (DescribeSoftphoneLayoutSection section :
                        type.getSections()) {
                    finalResult = finalResult + "\t\tFor entity "
                            + section.getEntityApiName()
                            + " following records are displayed:";
                    for (DescribeSoftphoneLayoutItem item :
                            section.getItems()) {
                        finalResult = finalResult + "\t\t\t" + item.getItemApiName();
                    }

                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        return finalResult;
    }

    /**
     * describeTabs() Describes the apps and tabs that have been configured for
     * the user. describeTabSetResult [] = connection.describeTabs();
     *
     *
     */
    @WebMethod(operationName = "describeTabs")
    public String describeTabs() {

        String finalResult = "";

        try {
// Describe tabs
            DescribeTabSetResult[] dtsrs = conn.describeTabs();
            finalResult = finalResult + "There are " + dtsrs.length
                    + " tab sets defined.";
// For each tab set describe result, get some properties
            for (int i = 0; i < dtsrs.length; i++) {
                finalResult = finalResult + "Tab Set " + (i + 1) + ":";
                DescribeTabSetResult dtsr = dtsrs[i];
                finalResult = finalResult + "Label: " + dtsr.getLabel();
                finalResult = finalResult + "\tLogo URL: " + dtsr.getLogoUrl();
                finalResult = finalResult + "\tTab selected: "
                        + dtsr.isSelected();
// Describe the tabs for the tab set
                DescribeTab[] tabs = dtsr.getTabs();
                System.out.println("\tTabs defined: " + tabs.length);
// Iterate through the returned tabs
                for (int j = 0; j < tabs.length; j++) {
                    DescribeTab tab = tabs[j];
                    finalResult = finalResult + "\tTab " + (j + 1) + ":";
                    finalResult = finalResult + "\t\tName: "
                            + tab.getSobjectName();
                    finalResult = finalResult + "\t\tLabel: " + tab.getLabel();
                    finalResult = finalResult + "\t\tURL: " + tab.getUrl();
                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        return finalResult;
    }

    // END: DESCRIBE CALL OPERATIONS OF SALEFORCE SOAP API
    // START: UTILITY CALLS OF SALEFORCE SOAP API
    /**
     * getServerTimestamp() Retrieves the current system timestamp from the API.
     * GetServerTimestampResult timestamp = connection.getServerTimestamp();
     *
     */
    @WebMethod(operationName = "getServerTimeStamp")
    public String getServerTimeStamp() {

        GetServerTimestampResult timestamp = null;
        String serverTime = null;

        try {
            //TODO write your implementation code here:
            timestamp = conn.getServerTimestamp();
            serverTime = timestamp.getTimestamp().getTime().toString();


        } catch (ConnectionException ex) {
            Logger.getLogger(SalesForceServices.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return serverTime;
    }

    /**
     * getUserInfo() Retrieves personal information for the user associated with
     * the current session. getUserInfoResult result = connection.getUserInfo();
     *
     */
    @WebMethod(operationName = "getUserInfo")
    public GetUserInfoResult getUserInfo() {

        GetUserInfoResult result = null;
        try {
            result = conn.getUserInfo();
            System.out.println("\nUser Information\n");
            System.out.println("\tCurrency symbol: "
                    + result.getCurrencySymbol());
            System.out.println("\tOrganization name: "
                    + result.getOrganizationName());
            System.out.println("\tDefault currency code: "
                    + result.getUserDefaultCurrencyIsoCode());
            System.out.println("\tEmail: "
                    + result.getUserEmail());
            System.out.println("\tFull name: "
                    + result.getUserFullName());
            System.out.println("\tUser record ID: "
                    + result.getUserId());
            System.out.println("\tLanguage: "
                    + result.getUserLanguage());
            System.out.println("\tLocale: "
                    + result.getUserLocale());
            System.out.println("\tTimezone: "
                    + result.getUserTimeZone());
            System.out.println("\tOrganization is multi-currency: "
                    + result.isOrganizationMultiCurrency());
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        if (result == null) {
            return null;
        } else {
            return result;
        }
    }

    /*
     * resetPassword() Changes a user’s password to a system-generated value.
     string password = connection.resetPassword(ID userID);

     * 
     */
    @WebMethod(operationName = "resetPassword")
    public String resetPassword(@WebParam(name = "userId") String userId) {

        String finalResult = "";

        try {
            ResetPasswordResult rpr = conn.resetPassword(userId);
            finalResult = rpr.getPassword();
            System.out.println("The temporary password for user ID " + userId
                    + " is " + finalResult);
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }

        return finalResult;
    }

    /*
     * sendEmail() Immediately sends an email message.
     For single email messages:
     SendEmailResult = connection.sendEmail( BaseEmail SingleEmailMessage emails[]);
     For mass email messages:
     SendEmailResult = connection.sendEmail( BaseEmail MassEmailMessage emails[]);

     */
    @WebMethod(operationName = "sendEmail")
    public String sendEmail(@WebParam(name = "message") SingleEmailMessage message) {

        try {
            SingleEmailMessage[] messages = {message};
            SendEmailResult[] results = conn.sendEmail(messages);
            if (results[0].isSuccess()) {
                System.out.println("The email was sent successfully.");
                return "The email was sent successfully.";
            } else {
                System.out.println("The email failed to send: "
                        + results[0].getErrors()[0].getMessage());
                return "The email failed to send: "
                        + results[0].getErrors()[0].getMessage();
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }
        return null;
    }

    // old implementation of the method
    /*
     @WebMethod(operationName = "sendEmail")
     public String sendEmail(@WebParam(name = "from") String from,
     @WebParam(name = "to") String to,
     @WebParam(name = "subject") String subject,
     @WebParam(name = "text") String text) {

     try {

     SingleEmailMessage message = new SingleEmailMessage();
     message.setBccSender(true);
     message.setEmailPriority(EmailPriority.High);
     message.setReplyTo(from);

     message.setSubject(subject);

     GetUserInfoResult guir = conn.getUserInfo();
     message.setTargetObjectId(guir.getUserId());
     message.setUseSignature(true);
     message.setPlainTextBody(text);
     message.setSaveAsActivity(Boolean.FALSE);

     SingleEmailMessage[] messages = {message};
     SendEmailResult[] results = conn.sendEmail(messages);
     if (results[0].isSuccess()) {
     System.out.println("The email was sent successfully.");
     return "The email was sent successfully.";
     } else {
     System.out.println("The email failed to send: "
     + results[0].getErrors()[0].getMessage());
     return "The email failed to send: "
     + results[0].getErrors()[0].getMessage();
     }
     } catch (ConnectionException ce) {
     ce.printStackTrace();
     }
     return null;
     }
     */

    /*
     *setPassword() Sets the specified user’s password to the specified value.
     * SetPasswordResult setPasswordResult = connection.setPassword(ID userID, string password);
     * 
     */
    @WebMethod(operationName = "setPassword")
    public void setPassword(@WebParam(name = "userId") String userId, @WebParam(name = "password") String password) {

        try {
            SetPasswordResult result = conn.setPassword(userId, password);
            System.out.println("The password for user ID " + userId + " changed to "
                    + password);
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }
    }

    // END: UTILITY CALLS OF SALEFORCE SOAP API
    // START: SALESFORCE METADATA API
    // TODO: for now, I will not implement file-based calls
    /*
     *create()
     Adds one or more new metadata components to your organization’s data. This call can be used to create any of the objects that extend Metadata.
     Syntax
     AsyncResult[] = metadatabinding.create(Metadata[] metadata);

     */
    @WebMethod(operationName = "createMetadata")
    public String createMetadata(@WebParam(name = "metadata") Metadata[] metadata) {

        String finalResult = "";
        try {
            MetadataConnection metadataConnection = MetadataLoginUtil.login();
            AsyncResult[] results = metadataConnection.create(metadata);
            AsyncResult asyncResult = results[0];
            long waitTimeMilliSecs = 1000;
            while (!asyncResult.isDone()) {
                Thread.sleep(waitTimeMilliSecs);
// double the wait time for the next iteration
                waitTimeMilliSecs *= 2;
                asyncResult = metadataConnection.checkStatus(
                        new String[]{asyncResult.getId()})[0];
                finalResult = "Status is: " + asyncResult.getState();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(SalesForceServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectionException ex) {
            Logger.getLogger(SalesForceServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finalResult;
    }

    /*
     *delete()
     Deletes one or more components from your organization’s data. This call can be used to delete any of the objects that extend Metadata. 
     Syntax
     AsyncResult[] = metadataConnection.delete(Metadata[] metadata);


     */
    @WebMethod(operationName = "deleteMetadata")
    public String deleteMetadata(@WebParam(name = "metadata") Metadata[] metadata) {

        String finalResult = "";
        try {
            MetadataConnection metadataConnection = MetadataLoginUtil.login();
            AsyncResult[] results = metadataConnection.delete(metadata);
            AsyncResult asyncResult = results[0];
            long waitTimeMilliSecs = 1000;
            while (!asyncResult.isDone()) {
                Thread.sleep(waitTimeMilliSecs);
// double the wait time for the next iteration
                waitTimeMilliSecs *= 2;
                asyncResult = metadataConnection.checkStatus(
                        new String[]{asyncResult.getId()})[0];
                finalResult = "Status is: " + asyncResult.getState();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(SalesForceServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectionException ex) {
            Logger.getLogger(SalesForceServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finalResult;
    }

    /*
     update()
     Updates one or more components in your organization’s data. This call can be used to update any of the objects that extend Metadata. 
     Syntax
     AsyncResult[] = metadataConnection.update(UpdateMetadata[] metadata);


     */
    @WebMethod(operationName = "updateMetadatas")
    public String updateMetadatas(@WebParam(name = "metadata") UpdateMetadata[] metadata) {

        String finalResult = "";
        try {
            MetadataConnection metadataConnection = MetadataLoginUtil.login();
            AsyncResult[] results = metadataConnection.update(metadata);
            AsyncResult asyncResult = results[0];
            long waitTimeMilliSecs = 1000;
            while (!asyncResult.isDone()) {
                Thread.sleep(waitTimeMilliSecs);
// double the wait time for the next iteration
                waitTimeMilliSecs *= 2;
                asyncResult = metadataConnection.checkStatus(
                        new String[]{asyncResult.getId()})[0];
                finalResult = "Status is: " + asyncResult.getState();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(SalesForceServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectionException ex) {
            Logger.getLogger(SalesForceServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finalResult;
    }

    /*
     checkStatus()
     Checks the status of asynchronous metadata calls create(), update(), or delete(), or the declarative metadata calls
     deploy() or retrieve().
     Syntax
     AsyncResult[] = metadatabinding.checkStatus(ID[] ids);
     */
    @WebMethod(operationName = "checkStatusMetadata")
    public String checkStatusMetadata(@WebParam(name = "ids") String[] ids) {

        String finalResult = "";

        MetadataConnection metadataConnection;
        try {
            metadataConnection = MetadataLoginUtil.login();
            AsyncResult asyncResult = metadataConnection.checkStatus(ids)[0];
            finalResult = "Status is: " + asyncResult.getState();
        } catch (ConnectionException ex) {
            Logger.getLogger(SalesForceServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        return finalResult;
    }

    /*
     describeMetadata()
     This call retrieves the metadata which describes your organization. This information includes Apex classes and triggers, custom
     objects, custom fields on standard objects, tab sets that define an app, and many other components.
     Syntax
     DescribeMetadataResult[] = metadataConnection.describeMetadata(double apiVersion);

     */
    @WebMethod(operationName = "describeMetadata")
    public String describeMetadata(@WebParam(name = "apiVersion") double apiVersion) {

        String finalResult = "";

        MetadataConnection metadataConnection;
        try {
            metadataConnection = MetadataLoginUtil.login();
            DescribeMetadataResult res =
                    metadataConnection.describeMetadata(apiVersion);
            StringBuffer sb = new StringBuffer();
            if (res != null && res.getMetadataObjects().length > 0) {
                for (DescribeMetadataObject obj : res.getMetadataObjects()) {
                    sb.append("***************************************************\n");
                    sb.append("XMLName: " + obj.getXmlName() + "\n");
                    sb.append("DirName: " + obj.getDirectoryName() + "\n");
                    sb.append("Suffix: " + obj.getSuffix() + "\n");
                    sb.append("***************************************************\n");
                }
            } else {
                sb.append("Failed to obtain metadata types.");
            }
            finalResult = sb.toString();
        } catch (ConnectionException ex) {
            Logger.getLogger(SalesForceServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        return finalResult;
    }

    // END: SALESFORCE METADATA API
    /**
     * Web service operation
     */
    // method to insert data to previosly created new CustomObjects from data ontology
    @WebMethod(operationName = "insertDataIntoCreatedCustomObjects")
    public String insertDataIntoCreatedCustomObjects(@WebParam(name = "pathToOntology") String pathToOntology) {
        String result = "OK";

        // sve se radi u createCustomObjectsFromDataOntology

        return result;
    }

    // method to create new custom objects using Metadata API from data ontology
    @WebMethod(operationName = "createCustomObjectsFromDataOntology")
    public String createCustomObjectsFromDataOntology(@WebParam(name = "pathToOntology") String pathToOntology) throws Exception {
        String result = "";

        MetadataConnection metadataConnection = MetadataLoginUtil.login();

        DataOntologyReader.readOntology(pathToOntology);

        List<TableUtil> tables = DataOntologyReader.getTables();

        System.out.println("DEBUG Tables size " + tables.size());

        CustomObject[] objects = new CustomObject[tables.size() + 1];

        int t = 0;

        for (TableUtil tableUtil : tables) {


            System.out.println("DEBUG table name == " + tableUtil.getName());

            String uniqueName = "A_" + tableUtil.getName() + "__c";
            String label = "A_" + tableUtil.getName();
            CustomObject customObject = new CustomObject();
            customObject.setFullName(uniqueName);
            customObject.setDeploymentStatus(DeploymentStatus.Deployed);
            customObject.setDescription("Created by my cloud interoperability framework.");
            customObject.setLabel(label);
            customObject.setPluralLabel(label + "s");
            customObject.setSharingModel(SharingModel.ReadWrite);

            // The name field appears in page layouts, related lists, and elsewhere.
            CustomField nf = new CustomField();
            nf.setType(com.sforce.soap.metadata.FieldType.Text);
            nf.setDescription("The custom object identifier on page layouts, related lists etc");
            nf.setLabel(label);
            nf.setFullName(uniqueName);
            customObject.setNameField(nf);

            AsyncResult[] asyncResults = metadataConnection.create(
                    new CustomObject[]{customObject});

            if (asyncResults == null) {
                System.out.println("Objects were not created successfully");

            }
            long waitTimeMilliSecs = 1000;
// After the create() call completes, we must poll the results of the checkStatus()
// call until it indicates that the create operation has completed.
            do {
                printAsyncResultStatus(asyncResults);
                waitTimeMilliSecs *= 2;
                Thread.sleep(waitTimeMilliSecs);
                asyncResults = metadataConnection.checkStatus(new String[]{
                            asyncResults[0].getId()
                        });
            } while (!asyncResults[0].isDone());
            printAsyncResultStatus(asyncResults);

            List<String> properties = tableUtil.getProperties();
            List<String> propertyTypes = tableUtil.getPropertyTypes();
            String indentifier = tableUtil.getIdentifier();
            List<String> linksToOtherObjects = tableUtil.getListOfLinksBetweenDataObjects();
            List<String> identifiersValues = tableUtil.getIdentifiersValues();

            int i = 0;

            CustomField[] fields = new CustomField[properties.size() + 1];


            System.out.println("DEBUG properties size = " + properties.size() + " for entity => " + tableUtil.getName());

            for (String property : properties) {

                String type = "XsdString";
                if (i < propertyTypes.size()) {
                    type = propertyTypes.get(i);
                }
                System.out.println("DEBUG property = " + property);

                System.out.println("DEBUG type = " + type);

                // TODO convert types from XSD type to Azure type
                String convertedType = DataTypeConverter.convertFromOwlDataType(TypeConstants.SALESFORCE, type);

                System.out.println("DEBUG converted type = " + convertedType);

                CustomField field = new CustomField();

                // convert from Salesforce dataType to SalesforceCustomFieldsTypes used in creation of custom objects and custom fields
                field.setType(DataTypeConverter.convertIntoSalesforceCustomFieldsTypes(convertedType));

                field.setDescription(property);



                System.out.println("DEBUG: full name = " + uniqueName + "." + property + "__c");

                field.setLabel(property);
                field.setFullName(uniqueName + "." + property + "__c");
                // set length if comparedType is a string

                //   if (linksToOtherObjects.size() == 0) {
                if (convertedType.compareTo("String") == 0) {
                    field.setLength(255);
                }

                if (convertedType.contains("Checkbox")) {
                    System.out.println("DEBUG: Pronađeno je polje tipa Checkbox");
                    field.setDefaultValue("0");
                }

                if (convertedType.contains("Boolean")) {
                    System.out.println("DEBUG: Pronađeno je polje tipa Boolean");
                    field.setDefaultValue("false");
                }
                //   }

                System.out.println("DEBUG FIELD TYPE to String == " + field.getType());

                if (field.getType().toString().contains(
                        "Number")) {

                    field.setPrecision(18);
                    field.setScale(2);

                }

                if (field.getType().toString().contains(
                        "Text")) {

                    field.setLength(255);

                }

                // TODO foreign key ne radi

                /*
                 // it is of reference type - foreign key
                 for (String linkString : linksToOtherObjects) {
                 System.out.println("DEBUG linkString " + linkString);

                 String tableName = linkString.substring(0, linkString.indexOf("("));
                 System.out.println("DEBUG link string table name = " + tableName);




                 if (tableUtil.getName().compareTo(tableName) == 0) {
                 String foreignKeyTableString = linkString.substring(linkString.indexOf("#") + 1, linkString.lastIndexOf("_"));

                 // we get foreign key table property as a property wich contains the name of the table to which foreign key connects
                 if (property.toLowerCase().contains(foreignKeyTableString.toLowerCase())) {
                 // setting the reference to other table
                 field.setType(com.sforce.soap.metadata.FieldType.MasterDetail);
                 field.setReferenceTo(foreignKeyTableString);
                 field.setRelationshipLabel( uniqueName + "." + "LinkToOtherObject_" + property + "__c");
                 field.setRelationshipName(uniqueName + "." + "LinkToOtherObject_" + property + "__c");
                 } else {
                 if (convertedType.compareTo("String") == 0) {
                 field.setLength(255);
                 }
                 }
                 } else {
                 if (convertedType.compareTo("String") == 0) {
                 field.setLength(255);
                 }
                 }
                 }
                
                 */

                //  System.out.println("DEBUG field type -> " + com.sforce.soap.metadata.FieldType.Text);

                fields[i] = field;


                i++;
            }

            // add custom field for primary key
            if (indentifier != null) {
                CustomField field = new CustomField();
                field.setType(com.sforce.soap.metadata.FieldType.Text);
                field.setDescription(indentifier);
                field.setLabel(tableUtil.getName() + indentifier + "__c");
                field.setFullName(uniqueName + "." + tableUtil.getName() + indentifier + "__c");
                field.setLength(255);

                fields[i] = field;

            }

            // add foreign keys (references to other objects)


            // ovo radim samo zbog ove greške: EXCEEDED_ID_LIMIT: record limit reached. cannot submit more than 10 records in this operation
            // u novom update-u to ne može biti više od 6 - API version 31  - 10.10.2014.
            if (fields.length > 6) {
                CustomField[] fieldsChunks1 = Arrays.copyOfRange(fields, 0, 6);

                System.out.println("DEBUG fieldsChunks1 size " + fieldsChunks1.length);
                AsyncResult[] asyncResults1 = metadataConnection.create(fieldsChunks1);

                if (asyncResults1 == null) {
                    System.out.println("Fields were not created successfully");

                }

                waitTimeMilliSecs = 1000;
// After the create() call completes, we must poll the results of the checkStatus()
// call until it indicates that the create operation has completed.
                do {
                    printAsyncResultStatus(asyncResults1);
                    waitTimeMilliSecs *= 2;
                    Thread.sleep(waitTimeMilliSecs);
                    asyncResults1 = metadataConnection.checkStatus(new String[]{
                                asyncResults1[0].getId()
                            });
                } while (!asyncResults1[0].isDone());
                printAsyncResultStatus(asyncResults1);

                if (fields.length < 13) {
                    CustomField[] fieldsChunks2 = Arrays.copyOfRange(fields, 6, fields.length);
                    System.out.println("DEBUG fieldsChunks2 size " + fieldsChunks2.length);

                    AsyncResult[] asyncResults2 = metadataConnection.create(fieldsChunks2);

                    waitTimeMilliSecs = 1000;
// After the create() call completes, we must poll the results of the checkStatus()
// call until it indicates that the create operation has completed.
                    do {
                        printAsyncResultStatus(asyncResults2);
                        waitTimeMilliSecs *= 2;
                        Thread.sleep(waitTimeMilliSecs);
                        asyncResults2 = metadataConnection.checkStatus(new String[]{
                                    asyncResults2[0].getId()
                                });
                    } while (!asyncResults2[0].isDone());
                    printAsyncResultStatus(asyncResults2);

                    if (asyncResults2 == null) {
                        System.out.println("Fields were not created successfully");

                    }


                }

                if (fields.length >= 12 && fields.length < 18) {
                    CustomField[] fieldsChunks3 = Arrays.copyOfRange(fields, 6, 12);
                    System.out.println("DEBUG fieldsChunks3 size " + fieldsChunks3.length);

                    AsyncResult[] asyncResults3 = metadataConnection.create(fieldsChunks3);

                    waitTimeMilliSecs = 1000;
// After the create() call completes, we must poll the results of the checkStatus()
// call until it indicates that the create operation has completed.
                    do {
                        printAsyncResultStatus(asyncResults3);
                        waitTimeMilliSecs *= 2;
                        Thread.sleep(waitTimeMilliSecs);
                        asyncResults3 = metadataConnection.checkStatus(new String[]{
                                    asyncResults3[0].getId()
                                });
                    } while (!asyncResults3[0].isDone());
                    printAsyncResultStatus(asyncResults3);

                    if (asyncResults3 == null) {
                        System.out.println("Fields were not created successfully");

                    }

                    CustomField[] fieldsChunks4 = Arrays.copyOfRange(fields, 12, fields.length);
                    System.out.println("DEBUG fieldsChunks4 size " + fieldsChunks4.length);
                    if (fieldsChunks4.length > 0) {
                        AsyncResult[] asyncResults4 = metadataConnection.create(fieldsChunks4);

                        waitTimeMilliSecs = 1000;
// After the create() call completes, we must poll the results of the checkStatus()
// call until it indicates that the create operation has completed.
                        do {
                            printAsyncResultStatus(asyncResults4);
                            waitTimeMilliSecs *= 2;
                            Thread.sleep(waitTimeMilliSecs);
                            asyncResults4 = metadataConnection.checkStatus(new String[]{
                                        asyncResults4[0].getId()
                                    });
                        } while (!asyncResults4[0].isDone());
                        printAsyncResultStatus(asyncResults4);

                        if (asyncResults4 == null) {
                            System.out.println("Fields were not created successfully");

                        }
                    }
                } else if (fields.length >= 18 && fields.length < 24) {
                    CustomField[] fieldsChunks3 = Arrays.copyOfRange(fields, 6, 12);
                    System.out.println("DEBUG fieldsChunks3 size " + fieldsChunks3.length);

                    AsyncResult[] asyncResults3 = metadataConnection.create(fieldsChunks3);

                    waitTimeMilliSecs = 1000;
// After the create() call completes, we must poll the results of the checkStatus()
// call until it indicates that the create operation has completed.
                    do {
                        printAsyncResultStatus(asyncResults3);
                        waitTimeMilliSecs *= 2;
                        Thread.sleep(waitTimeMilliSecs);
                        asyncResults3 = metadataConnection.checkStatus(new String[]{
                                    asyncResults3[0].getId()
                                });
                    } while (!asyncResults3[0].isDone());
                    printAsyncResultStatus(asyncResults3);

                    if (asyncResults3 == null) {
                        System.out.println("Fields were not created successfully");

                    }

                    CustomField[] fieldsChunks4 = Arrays.copyOfRange(fields, 12, 18);
                    System.out.println("DEBUG fieldsChunks4 size " + fieldsChunks4.length);

                    if (fieldsChunks4.length > 0) {

                        AsyncResult[] asyncResults4 = metadataConnection.create(fieldsChunks4);

                        waitTimeMilliSecs = 1000;
// After the create() call completes, we must poll the results of the checkStatus()
// call until it indicates that the create operation has completed.
                        do {
                            printAsyncResultStatus(asyncResults4);
                            waitTimeMilliSecs *= 2;
                            Thread.sleep(waitTimeMilliSecs);
                            asyncResults4 = metadataConnection.checkStatus(new String[]{
                                        asyncResults4[0].getId()
                                    });
                        } while (!asyncResults4[0].isDone());
                        printAsyncResultStatus(asyncResults4);

                        if (asyncResults4 == null) {
                            System.out.println("Fields were not created successfully");

                        }
                    }

                    CustomField[] fieldsChunks5 = Arrays.copyOfRange(fields, 18, fields.length);
                    System.out.println("DEBUG fieldsChunks5 size " + fieldsChunks5.length);

                    if (fieldsChunks5.length > 0) {
                        AsyncResult[] asyncResults5 = metadataConnection.create(fieldsChunks5);

                        waitTimeMilliSecs = 1000;
// After the create() call completes, we must poll the results of the checkStatus()
// call until it indicates that the create operation has completed.
                        do {
                            printAsyncResultStatus(asyncResults5);
                            waitTimeMilliSecs *= 2;
                            Thread.sleep(waitTimeMilliSecs);
                            asyncResults5 = metadataConnection.checkStatus(new String[]{
                                        asyncResults5[0].getId()
                                    });
                        } while (!asyncResults5[0].isDone());
                        printAsyncResultStatus(asyncResults5);

                        if (asyncResults5 == null) {
                            System.out.println("Fields were not created successfully");

                        }
                    }
                }


            } else {

                asyncResults = metadataConnection.create(fields);

                if (asyncResults == null) {
                    System.out.println("Fields were not created successfully");

                }

// After the create() call completes, we must poll the results of the checkStatus()
// call until it indicates that the create operation has completed.
                do {
                    printAsyncResultStatus(asyncResults);
                    waitTimeMilliSecs *= 2;
                    Thread.sleep(waitTimeMilliSecs);
                    asyncResults = metadataConnection.checkStatus(new String[]{
                                asyncResults[0].getId()
                            });
                } while (!asyncResults[0].isDone());
                printAsyncResultStatus(asyncResults);


                customObject.setFields(fields);



                objects[t] = customObject;
                t++;
            }
        }



        long waitTimeMilliSecs = 60000;
        System.out.println("DEBUG Thread sleeping for 60s");
        Thread.sleep(waitTimeMilliSecs);
        System.out.println("DEBUG Wake up and login partner connection");
        loginParnerConnection();

        System.out.println("DEBUG Thread sleeping for 60s");
        Thread.sleep(waitTimeMilliSecs);
        System.out.println("DEBUG Wake up");

        // INSERT RECORDS INTO NEW CREATED CUSTOM OBJECTS
        for (TableUtil tableUtil : tables) {
            List<String> properties = tableUtil.getProperties();
            List<String> propertyTypes = tableUtil.getPropertyTypes();
            List<ArrayList<String>> rows = tableUtil.getRows();
            List<String> identifiersValues = tableUtil.getIdentifiersValues();
            List<String> linksToOtherObjects = tableUtil.getListOfLinksBetweenDataObjects();
            String identifier = tableUtil.getIdentifier();

            String uniqueName = "A_" + tableUtil.getName() + "__c";

            int rowCount = 0;

            com.sforce.soap.partner.sobject.SObject[] salesforceObjects = new com.sforce.soap.partner.sobject.SObject[rows.size()];

            for (ArrayList<String> rowData : rows) {

                int colNum = 0;


                com.sforce.soap.partner.sobject.SObject objekt = new com.sforce.soap.partner.sobject.SObject();
                objekt.setType(uniqueName);

                for (String column : rowData) {

                    //  System.out.println("DEBUG identifier = " + identifier);
                    //  System.out.println("DEBUG identifiersValues.get(rowCount) = " + identifiersValues.get(rowCount));
                    if (identifiersValues.get(rowCount) != null || identifiersValues.get(rowCount).trim().compareTo("") != 0) {
                        objekt.setField(tableUtil.getName() + "identifier__c", identifiersValues.get(rowCount));
                    }

                    if (properties.size() > colNum) {
                        String property = properties.get(colNum);

                        System.out.println("DEBUG property = " + property + "__c");
                        System.out.println("DEBUG column = " + column);

                        if (!linksToOtherObjects.contains(property)) {

                            String propertyType = propertyTypes.get(colNum);
                            System.out.println("DEBUG property type in SalesforceService " + propertyType);
                            objekt.setField(property.trim() + "__c", DataTypeConverter.convertIntoJavaObjectTypes(propertyType, column));

                        }

                        System.out.println("DEBUG test 3");
                    }

                    colNum++;

                }



                salesforceObjects[rowCount] = objekt;

                rowCount++;
            }

            com.sforce.soap.partner.SaveResult[] results = null;






            if (salesforceObjects != null && salesforceObjects.length > 0) {
                results = partnerConnection.create(salesforceObjects);
            }


            if (results != null) {
                for (int j = 0; j < results.length; j++) {
                    if (results[j].isSuccess()) {
                        result = results[j].getId();
                        System.out.println(
                                "\nA object was created with an ID of: " + result);
                    } else {
                        // There were errors during the create call,
                        // go through the errors array and write
                        // them to the console
                        for (int i = 0; i < results[j].getErrors().length; i++) {
                            com.sforce.soap.partner.Error err = results[j].getErrors()[i];
                            System.out.println("Errors were found on item " + j);
                            System.out.println("Error code: "
                                    + err.getStatusCode().toString());
                            System.out.println("Error message: " + err.getMessage());
                        }
                    }
                }
            }



        }


        return result;

    }

    public void printAsyncResultStatus(AsyncResult[] asyncResults) throws Exception {
        if (asyncResults == null || asyncResults.length == 0 || asyncResults[0] == null) {
            throw new Exception("The object status cannot be retrieved");
        }
        AsyncResult asyncResult = asyncResults[0]; //we are creating only 1 metadata object
        if (asyncResult.getStatusCode() != null) {
            System.out.println("Error status code: "
                    + asyncResult.getStatusCode());
            System.out.println("Error message: " + asyncResult.getMessage());
        }
        System.out.println("Object with id:" + asyncResult.getId() + " is "
                + asyncResult.getState());
    }

    /**
     * This method check if user is logged in
     */
    @WebMethod(operationName = "isUserLoggedIn")
    public boolean isUserLoggedIn() {
        if (conn == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "describeSalesforceObject")
    public String describeSalesforceObject(String objectName) {
        try {
// Make the describe call
            DescribeSObjectResult describeSObjectResult =
                    conn.describeSObject(objectName);
// Get sObject metadata
            if (describeSObjectResult != null) {
                System.out.println("sObject name: "
                        + describeSObjectResult.getName());
                if (describeSObjectResult.isCreateable()) {
                    System.out.println("Createable");
                    SalesforceDataOntologyCreator.getDescribeList().add(describeSObjectResult);
                }
// Get the fields
                Field[] fields = describeSObjectResult.getFields();
                System.out.println("Has " + fields.length + " fields");
// Iterate through each field and gets its properties
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    System.out.println("Field name: " + field.getName());
                    System.out.println("Field label: " + field.getLabel());
                    System.out.println("Field type: " + field.getType());
// If this is a picklist field, show the picklist values
                    if (field.getType().equals(FieldType.picklist)) {
                        PicklistEntry[] picklistValues =
                                field.getPicklistValues();
                        if (picklistValues != null) {
                            System.out.println("Picklist values: ");
                            for (int j = 0; j < picklistValues.length; j++) {
                                if (picklistValues[j].getLabel() != null) {


                                    System.out.println("\tItem: "
                                            + picklistValues[j].getLabel());
                                }
                            }
                        }
                    }
// If a reference field, show what it references
                    if (field.getType().equals(FieldType.reference)) {
                        System.out.println("Field references the "
                                + "following objects:");
                        String[] referenceTos = field.getReferenceTo();
                        for (int j = 0; j < referenceTos.length; j++) {
                            System.out.println("\t" + referenceTos[j]);
                        }
                    }
                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }


        return "Success";
    }

    @WebMethod(operationName = "createSalesforceDataModelOntology")
    public boolean createSalesforceDataModelOntology() {

        getDataTypesInCurrentData();
        SalesforceDataOntologyCreator.create();
        return true;
    }

    // get data types from current Google App Engine datastorage
    @WebMethod(operationName = "getDataTypesInCurrentData")
    public List<String> getDataTypesInCurrentData() {


        File[] files = new File(Constants.SALESFORCE_CSV_DIRECTORY).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.getName().contains("FILTERED_D_C");
            }
        });

        // nije nađeni filtrirani data container, operaciju treba provesti za sve
        if (files == null || files.length == 0) {
            files = new File(Constants.SALESFORCE_CSV_DIRECTORY).listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return file.getName().endsWith(".csv");
                }
            });
        }


        if (files != null && files.length > 0) {

            String[] customObjectFields = new String[files.length];

            for (int i = 0; i < files.length; i++) {



                String salesForceObjectName = "";
                String filename = files[i].getName();
                System.out.println("DEBUG FILENAME:" + filename);

                if (filename.contains("FILTERED_D_C")) {
                    filename = filename.replaceAll("FILTERED_D_C", "");
                }
                // TODO i renamati tu datoteku opet bez ovog prefixa

                salesForceObjectName = filename.substring(0, filename.indexOf("."));
                System.out.println("DEBUG salesForceObjectName:" + salesForceObjectName);

                // get important object fields - custom field end with __c
                CSVReader reader;


                try {
                    reader = new CSVReader(new FileReader(files[i].getAbsolutePath()));
                    List t1 = reader.readAll();
                    System.out.println("DEBUG: i counter = " + i);
                    customObjectFields[i] = "";
                    for (int j = 0; j < t1.size(); j++) {
                        String[] strings = (String[]) t1.get(j);
                        for (int k = 0; k < strings.length; k++) {
                            // System.out.print(strings[k] + " ");
                            if (strings[k].endsWith("__c")) {

                                if (customObjectFields[i].compareTo("") == 0) {
                                    customObjectFields[i] = strings[k];
                                } else {
                                    customObjectFields[i] = customObjectFields[i] + ", " + strings[k];
                                }

                                System.out.println("DEBUG: Custom object field -->> " + strings[k]);
                            }
                        }
                        System.out.println();
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SalesForceServices.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(SalesForceServices.class.getName()).log(Level.SEVERE, null, ex);
                }



                if (salesForceObjectName != null && salesForceObjectName.length() > 1) {
                    System.out.println("DEBUG SALESFORCE OBJECT NAME:" + salesForceObjectName);
                    if (!isUserLoggedIn()) {
                        login();
                    }
                    // get meta data about Salesforce objects
                    describeSalesforceObject(salesForceObjectName);

                    // query all objects of a specific object type

                    // I need a list of important object fields - by parsing csv file
                    // add Id to preserve Id field
                    String sqlQuery = "SELECT Id, " + customObjectFields[i] + " FROM " + salesForceObjectName;

                    System.out.println("DEBUG: sql query before invoking service = " + sqlQuery);
                    String queryResult = query(sqlQuery);
                    System.out.println("DEBUG QUERY RESULT ==>> " + queryResult);
                }

            }
        }


        return SalesforceDataOntologyCreator.getDataTypesInCurrentData();
    }
}
