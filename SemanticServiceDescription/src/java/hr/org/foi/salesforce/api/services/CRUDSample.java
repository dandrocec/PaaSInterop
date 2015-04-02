/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.salesforce.api.services;

import com.sforce.soap.metadata.*;

/**
 *
 * @author Darko Androcec taken from Salesforce metadata API documentation
 */
/**
 * Sample that logs in and creates a custom object through the metadata api
 */
public class CRUDSample {

    private MetadataConnection metadataConnection;
// one second in milliseconds
    private static final long ONE_SECOND = 1000;

    public CRUDSample() {
    }

    public static void main(String[] args) throws Exception {
        CRUDSample crudSample = new CRUDSample();
        crudSample.runCreate();
    }

    /**
     * Create a custom object. This method demonstrates usage of the create()
     * and checkStatus() calls.
     *
     * @param uniqueName Custom object name should be unique.
     */
    private void createCustomObject(final String uniqueName) throws Exception {
        final String label = "My Custom Object";
        CustomObject customObject = new CustomObject();
        customObject.setFullName(uniqueName);
        customObject.setDeploymentStatus(DeploymentStatus.Deployed);
        customObject.setDescription("Created by the Metadata API Sample");
        customObject.setLabel(label);
        customObject.setPluralLabel(label + "s");
        customObject.setSharingModel(SharingModel.ReadWrite);
// The name field appears in page layouts, related lists, and elsewhere.
        CustomField nf = new CustomField();
        nf.setType(FieldType.Text);
        nf.setDescription("The custom object identifier on page layouts, related lists etc");
        nf.setLabel(label);
        nf.setFullName(uniqueName);
        customObject.setNameField(nf);
        AsyncResult[] asyncResults = metadataConnection.create(
                new CustomObject[]{customObject});
        if (asyncResults == null) {
            System.out.println("The object was not created successfully");
            return;
        }
        long waitTimeMilliSecs = ONE_SECOND;
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
    }

    private void printAsyncResultStatus(AsyncResult[] asyncResults) throws Exception {
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

    private void runCreate() throws Exception {
        metadataConnection = MetadataLoginUtil.login();
// Custom objects and fields must have __c suffix in the full name.
        final String uniqueObjectName = "NewObjectUsingMetadataApi__c";
        createCustomObject(uniqueObjectName);
    }
}
