/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.migrate.client;

import hr.org.foi.salesforce.api.services.Exception_Exception;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Darko Androcec
 *
 * pitanje da li ću tako ovo organizirati
 */
public class ReadOntologyTest {

    public static final String GOOGLE_APP_ENGINE_ONTOLOGY = "D:\\DOKTORAT\\generiraneOntologije\\GoogleAppEngineDataModel.owl";
    public static final String WINDOWS_AZURE_ONTOLOGY = "D:\\DOKTORAT\\generiraneOntologije\\AzureDataModel.owl";
    public static final String SALESFORCE_ONTOLOGY = "D:\\DOKTORAT\\generiraneOntologije\\SalesforceDataModel.owl";

    public static void main(String[] args) {
        System.out.println("READ ONTOLOGY TEST");


        // read Google App Engine ontology
        //   String res = readOntology(GOOGLE_APP_ENGINE_ONTOLOGY);
        //   System.out.println(res);
        try {
            /*
             // read Windows Azure ontology
             String res2 = readOntology(WINDOWS_AZURE_ONTOLOGY);
             System.out.println(res2);
             */

            /*
             // read Salesforce data ontology
             String res3 = readOntology(SALESFORCE_ONTOLOGY);
             System.out.println(res3);
             */

            //this code for Azure
            //  createTableFromDataOntology(GOOGLE_APP_ENGINE_ONTOLOGY);

            // this is code for Salesforce
            login();
            
            createCustomObjectsFromDataOntology(GOOGLE_APP_ENGINE_ONTOLOGY);
        } catch (Exception_Exception ex) {
            Logger.getLogger(ReadOntologyTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        insertDataIntoCreatedCustomObjects(GOOGLE_APP_ENGINE_ONTOLOGY);

        // this is code for Google App Engine
        //  createEntitiesFromDataOntology(GOOGLE_APP_ENGINE_ONTOLOGY);

    }

    private static String readOntology(java.lang.String pathToOntology) {
        hr.org.foi.ontology.services.OntologyServices_Service service = new hr.org.foi.ontology.services.OntologyServices_Service();
        hr.org.foi.ontology.services.OntologyServices port = service.getOntologyServicesPort();
        return port.readOntology(pathToOntology);
    }

    private static String createTableFromDataOntology(java.lang.String pathToOntology) {
        hr.org.foi.azure.api.services.AzureServices_Service service = new hr.org.foi.azure.api.services.AzureServices_Service();
        hr.org.foi.azure.api.services.AzureServices port = service.getAzureServicesPort();
        return port.createTableFromDataOntology(pathToOntology);
    }

    private static String createEntitiesFromDataOntology(java.lang.String pathToOntology) {
        hr.org.foi.appengine.api.services.GoogleAppEngineServices_Service service = new hr.org.foi.appengine.api.services.GoogleAppEngineServices_Service();
        hr.org.foi.appengine.api.services.GoogleAppEngineServices port = service.getGoogleAppEngineServicesPort();
        return port.createEntitiesFromDataOntology(pathToOntology);
    }

    private static String createCustomObjectsFromDataOntology(java.lang.String pathToOntology) throws Exception_Exception {
        hr.org.foi.salesforce.api.services.SalesForceServices_Service service = new hr.org.foi.salesforce.api.services.SalesForceServices_Service();
        hr.org.foi.salesforce.api.services.SalesForceServices port = service.getSalesForceServicesPort();
        return port.createCustomObjectsFromDataOntology(pathToOntology);
    }

    private static String insertDataIntoCreatedCustomObjects(java.lang.String pathToOntology) {
        hr.org.foi.salesforce.api.services.SalesForceServices_Service service = new hr.org.foi.salesforce.api.services.SalesForceServices_Service();
        hr.org.foi.salesforce.api.services.SalesForceServices port = service.getSalesForceServicesPort();
        return port.insertDataIntoCreatedCustomObjects(pathToOntology);
    }

    private static String login() {
        hr.org.foi.salesforce.api.services.SalesForceServices_Service service = new hr.org.foi.salesforce.api.services.SalesForceServices_Service();
        hr.org.foi.salesforce.api.services.SalesForceServices port = service.getSalesForceServicesPort();
        return port.login();
    }

    private static String loginParnerConnection() {
        hr.org.foi.salesforce.api.services.SalesForceServices_Service service = new hr.org.foi.salesforce.api.services.SalesForceServices_Service();
        hr.org.foi.salesforce.api.services.SalesForceServices port = service.getSalesForceServicesPort();
        return port.loginParnerConnection();
    }
    
    
    
}

