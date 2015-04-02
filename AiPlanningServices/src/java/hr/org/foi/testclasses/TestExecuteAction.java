/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.testclasses;

import hr.org.foi.sawsdl.parser.EasySawsdlParser;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.ow2.easywsdl.extensions.sawsdl.SAWSDLFactory;
import org.ow2.easywsdl.extensions.sawsdl.api.AttrExtensions;
import org.ow2.easywsdl.extensions.sawsdl.api.Description;
import org.ow2.easywsdl.extensions.sawsdl.api.InterfaceType;
import org.ow2.easywsdl.extensions.sawsdl.api.Operation;
import org.ow2.easywsdl.extensions.sawsdl.api.SAWSDLException;
import org.ow2.easywsdl.extensions.sawsdl.api.SAWSDLReader;

/**
 *
 * @author Darko Androcec
 */
public class TestExecuteAction {

    private static final String URL_FILE_AZURE = "http://localhost:8080/AzureServices.sawsdl";
    private static final String URL_FILE_APPENGINE = "http://localhost:8080/GoogleAppEngineServices.sawsdl";
    private static final String URL_FILE_SALESFORCE = "http://localhost:8080/SalesForceServices.sawsdl";
    private static final String AINAME_AZURE = "azure";
    private static final String AINAME_APPENGINE = "googleappengine";
    private static final String AINAME_SALESFORCE = "salesforce";
    private static final String WSDL_AZURE_URL = "http://localhost:8080/SemanticServiceDescription1/AzureServices?wsdl";
    private static final String WSDL_APPENGINE_URL = "http://localhost:8080/SemanticServiceDescription1/GoogleAppEngineServices?wsdl";
    private static final String WSDL_SALESFORCE_URL = "http://localhost:8080/SemanticServiceDescription1/SalesForceServices?wsdl";
    
    public static final String GOOGLE_APP_ENGINE_ONTOLOGY = "D:\\DOKTORAT\\generiraneOntologije\\GoogleAppEngineDataModel.owl";
    public static final String WINDOWS_AZURE_ONTOLOGY = "D:\\DOKTORAT\\generiraneOntologije\\AzureDataModel.owl";
    public static final String SALESFORCE_ONTOLOGY = "D:\\DOKTORAT\\generiraneOntologije\\SalesforceDataModel.owl";
    
    private static String argument;

    public static void dynamicCallToWebService(String serviceUrl, String serviceName){
        
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(serviceUrl);

        Object[] res;
        try {
            res = client.invoke(serviceName);
            System.out.println("Service response: " + res[0]);
        } catch (Exception ex) {
            Logger.getLogger(TestExecuteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
     public static void dynamicCallToWebService(String serviceUrl, String serviceName, String argument){
        
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(serviceUrl);

        Object[] res;
        try {
            res = client.invoke(serviceName, argument);
            System.out.println("Service response: " + res[0]);
        } catch (Exception ex) {
            Logger.getLogger(TestExecuteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public static void main(String[] args) {
        // parse appropriate sawsdl file to get information what action should be executed
        String action = "[(!createdatamodelontology azure) (!createdataelementsfromontology googleappengine) --------------- ]";


        while (action.contains("(!")) {

            String foundAction = action.substring(action.indexOf("(!") + 2, action.indexOf(")"));

            //     System.out.println("Debug found action = " + foundAction);


            if (foundAction.contains(AINAME_AZURE)) {
                findWebServiceFromSAWSDL(URL_FILE_AZURE, foundAction, WSDL_AZURE_URL);
                argument = WINDOWS_AZURE_ONTOLOGY;
            }

            if (foundAction.contains(AINAME_APPENGINE)) {

                findWebServiceFromSAWSDL(URL_FILE_APPENGINE, foundAction, WSDL_APPENGINE_URL);
                argument = GOOGLE_APP_ENGINE_ONTOLOGY;
            }

            if (foundAction.contains(AINAME_SALESFORCE)) {
                findWebServiceFromSAWSDL(URL_FILE_SALESFORCE, foundAction, WSDL_SALESFORCE_URL);
                argument = SALESFORCE_ONTOLOGY;
            }

            action = action.substring(action.indexOf(")") + 1);
        }
    }

    public static void findWebServiceFromSAWSDL(String url, String action, String wsdlUrl) {
        // Read a SAWSDL description

        System.out.println("--------------------------------");

        SAWSDLReader reader;
        try {

            reader = SAWSDLFactory.newInstance().newSAWSDLReader();
            Description desc = reader.read(new URL(url));


            URI uri = desc.getDocumentBaseURI();
            // System.out.println("Document URI " + uri);

            String uriString = uri.toString();
            String nameOfService = uriString.substring(uriString.lastIndexOf("/") + 1, uriString.indexOf("Services"));
            // System.out.println("NAME OF A SERVICE = " + nameOfService);

            String allOperations = " ";

            List<InterfaceType> interfaceTypes = desc.getInterfaces();
            for (InterfaceType type : interfaceTypes) {
                List<Operation> operations = type.getOperations();

                //    System.out.println("DEBUG type name: " + type.getQName().toString());

                for (Operation operation : operations) {
                    //System.out.println("Operation = " + operation.getQName().toString());



                    AttrExtensions attrExtensions = operation.getAttrExtensions();

                    if (attrExtensions != null) {
                        List<URI> modelReferences = attrExtensions.getModelReference();
                        //   System.out.println("modelReferences size " + modelReferences.size());


                        for (URI modelReference : modelReferences) {
                            // System.out.println("Model reference = " + modelReference.toString());

                            String dataOperationName = modelReference.toString().substring(modelReference.toString().indexOf("#") + 1);
                            dataOperationName = dataOperationName.toLowerCase();
                            dataOperationName = dataOperationName.substring(0, dataOperationName.indexOf("operation"));

                            //  System.out.println("DEBUG action = " + action);
                            // System.out.println("DEBUG DATA OPERATION NAME = " + dataOperationName);

                            String actionName = action.substring(0, action.indexOf(" "));
                             System.out.println("DEBUG actionName " + actionName);

                            if (actionName.toLowerCase().compareTo(dataOperationName.toLowerCase()) == 0) {

                                //      System.out.println("Pronašao sam traženu operaciju!");

                                String operationToExecute = operation.getQName().toString();

                                operationToExecute = operationToExecute.substring(operationToExecute.indexOf("}")+1);
                                System.out.println("Operation to execute = " + operationToExecute);
                                System.out.println("WSDL url = " + wsdlUrl);
                                if (argument==null){
                                    dynamicCallToWebService(wsdlUrl, operationToExecute)
                                        ;
                                }
                                else{
                                    dynamicCallToWebService(wsdlUrl, operationToExecute, argument);
                                }

                            }


                        }
                    }
                }
            }


            /*

             List<Service> services = desc.getServices();
             System.out.println(services.size());
             for (Service service : services) {

             System.out.println(service.getQName().toString());



             List<Endpoint> endpoints = service.getEndpoints();
             for (Endpoint endpoint : endpoints) {
             System.out.println(endpoint.getName());


             }

             }
             */


        } catch (IOException ex) {
            Logger.getLogger(EasySawsdlParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(EasySawsdlParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAWSDLException ex) {
            Logger.getLogger(EasySawsdlParser.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
    
    
}