/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.aiplanning.services;

import JSHOP2.JSHOP2;
import JSHOP2.PlanStepInfo;
import hr.org.foi.generated.aifiles.problem;
import hr.org.foi.sawsdl.parser.EasySawsdlParser;
import hr.org.foi.test.xslt.transform.LogInterceptor;
import hr.org.foi.testclasses.TestExecuteAction;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
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
public class WebServicesFromPlanExecutor {

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
    private static long timeout = 6000000;
    // state of the world after found plan was executed
    private static ArrayList<String> stepStatesAfterPlanExecution = null;

    public static ArrayList<String> getStepStatesAfterPlanExecution() {
        initializeStepStates();
        return stepStatesAfterPlanExecution;
    }

    private static void initializeStepStates() {
        String result = "";


        if (problem.getPlans() == null || problem.getPlans().isEmpty()) {
            result = "A plan was not found!";
        } else {
            result = problem.getPlans().toString();
        }

        System.out.println("Plan result: " + result);


        ArrayList<PlanStepInfo> planSteps = JSHOP2.getPlanStepList();

        //   System.out.println(planSteps);


        for (Iterator<PlanStepInfo> step = planSteps.iterator(); step.hasNext();) {

            PlanStepInfo stepInfo = step.next();


            // za sada pretpostavka da se problem nalazi u onoj metodi / operatoru gdje se prvi put pojavljuje backtracking - ovo naravno vrijedi ako nije pronađen plan
            System.out.println("Step info: " + stepInfo);
            System.out.println("Step info plan found: " + stepInfo.planFound);
            System.out.println("Step info plan found: " + stepInfo.action);


            if (stepInfo.state != null) {
                stepStatesAfterPlanExecution = stepInfo.state;
            }

            //  System.out.println("stepStates: " + stepStatesAfterPlanExecution);
            if (stepStatesAfterPlanExecution != null) {
                //     System.out.println("DEBUG step states:");
                for (String oneState : stepStatesAfterPlanExecution) {
                    System.out.println(oneState);
                }
            }


        }
    }

    public static void dynamicCallToWebService(String serviceUrl, String serviceName) throws Exception {



        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(serviceUrl);

        // customely defined LogInterceptor is defined to intercept SOAP response message and save it into the file serviceResultSoap.xml
        client.getInInterceptors().add(new LogInterceptor());

        if (client != null) {
            HTTPConduit conduit = (HTTPConduit) client.getConduit();
            HTTPClientPolicy policy = new HTTPClientPolicy();
            policy.setConnectionTimeout(timeout);
            policy.setReceiveTimeout(timeout);
            conduit.setClient(policy);
        }


        /*
         // Set request context property.
         java.util.Map<String, Object> requestContext =
         client.getRequestContext();

         requestContext.put("com.sun.xml.internal.ws.connect.timeout", new Long(6000000));
         requestContext.put("com.sun.xml.internal.ws.request.timeout", new Long(6000000));
         requestContext.put("com.sun.xml.ws.request.timeout", new Long(6000000));
         requestContext.put("com.sun.xml.ws.connect.timeout", new Long(6000000));
         */

        Object[] res;

        res = client.invoke(serviceName);
        System.out.println("Service response: " + res[0]);


    }

    public static void dynamicCallToWebService(String serviceUrl, String serviceName, Object argument) throws Exception {

        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(serviceUrl);

        // customely defined LogInterceptor is defined to intercept SOAP response message and save it into the file serviceResultSoap.xml
        client.getInInterceptors().add(new LogInterceptor());

        if (client != null) {
            HTTPConduit conduit = (HTTPConduit) client.getConduit();
            HTTPClientPolicy policy = new HTTPClientPolicy();
            policy.setConnectionTimeout(timeout);
            policy.setReceiveTimeout(timeout);
            conduit.setClient(policy);
        }

        // Set request context property.

        /*
         java.util.Map<String, Object> requestContext =
         client.getRequestContext();
   

         requestContext.put("com.sun.xml.internal.ws.connect.timeout", new Long(6000000));
         requestContext.put("com.sun.xml.internal.ws.request.timeout", new Long(6000000));
         requestContext.put("com.sun.xml.ws.request.timeout", new Long(6000000));
         requestContext.put("com.sun.xml.ws.connect.timeout", new Long(6000000));
         */

        Object[] res;
        
        System.out.println("DEBUG Service name: " + serviceName);
         System.out.println("DEBUG argument: " + argument);
         
        res = client.invoke(serviceName, argument);
        System.out.println("Service response: " + res[0]);


    }

    public static String execute(String planString) {
        // parse appropriate sawsdl file to get information what action should be executed

        String result = "ok";
        System.out.println("DEBUG plan string " + planString);
        String action = planString;


        // Check whether input/output transformation are needed

        List<String> neededTransformations = new ArrayList();
        if (getStepStatesAfterPlanExecution() != null) {
            System.out.println("DEBUG step states:");
            for (String oneState : stepStatesAfterPlanExecution) {
                if (oneState != null && oneState.contains("transformationduringexecution")) {
                    System.out.println("Transformation is needed: " + oneState);
                    neededTransformations.add(oneState);
                }

            }
        }


        while (action.contains("(!")) {

            String foundAction = action.substring(action.indexOf("(!") + 2, action.indexOf(")"));

            System.out.println("Debug found action = " + foundAction);

            try {
                if (foundAction.contains(AINAME_AZURE)) {

                    findAndCallWebServiceFromSAWSDL(URL_FILE_AZURE, foundAction, WSDL_AZURE_URL, neededTransformations);
                    argument = WINDOWS_AZURE_ONTOLOGY;

                }

                if (foundAction.contains(AINAME_APPENGINE)) {

                    findAndCallWebServiceFromSAWSDL(URL_FILE_APPENGINE, foundAction, WSDL_APPENGINE_URL, neededTransformations);
                    argument = GOOGLE_APP_ENGINE_ONTOLOGY;
                }

                if (foundAction.contains(AINAME_SALESFORCE)) {
                    findAndCallWebServiceFromSAWSDL(URL_FILE_SALESFORCE, foundAction, WSDL_SALESFORCE_URL, neededTransformations);
                    argument = SALESFORCE_ONTOLOGY;
                }
            } catch (Exception ex) {
                System.out.println("DEBUG TU SAM : Exception is thrown!");
                Logger.getLogger(WebServicesFromPlanExecutor.class.getName()).log(Level.SEVERE, null, ex);
                result = "Error occurs during service composition execution! Error details: ";
                if (ex.toString().contains("org.apache.cxf.interceptor.Fault")) {
                    result = result + " There is a problem with input for the operation " + foundAction + "! Please check lifting and lowering schema mappings!";
                }
                result = result + ex.toString();
                return result;
            }

            action = action.substring(action.indexOf(")") + 1);
        }

        if (result.compareTo("ok") == 0) {
            result = "Action is succesfully executed";
        }

        return result;
    }

    public static void findAndCallWebServiceFromSAWSDL(String url, String action, String wsdlUrl, List<String> neededTransformations) throws Exception {
        // Read a SAWSDL description

        System.out.println("--------------------------------");

        SAWSDLReader reader;

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

                boolean isOperationCalled = false;

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

                            Object transformedInput = null;
                            Object transformedOutput = null;
                            String operationToExecute = operation.getQName().toString();


                            operationToExecute = operationToExecute.substring(operationToExecute.indexOf("}") + 1);
                            System.out.println("Operation to execute = " + operationToExecute);
                            System.out.println("WSDL url = " + wsdlUrl);
                            System.out.println("dEBUG neededTransformations = " + neededTransformations);

                            for (String transformation : neededTransformations) {
                                System.out.println("DEBUG transformation: " + transformation );
                                System.out.println("DEBUG actionName : " + actionName );
                                if (transformation.contains(actionName)) {
                                    System.out.println("DEBUG transformation is found on action "
                                            + actionName + " and its definition is " + transformation);
                                    String transformationScript = transformation.substring(transformation.lastIndexOf(" ") + 1,
                                            transformation.lastIndexOf(")")) + ".xslt";
                                    System.out.println("Debug transformation script: " + transformationScript);

                                    if (!transformationScript.contains("lifting")) {
                                        transformedInput = InputOutputMapperExec.processSchemaMapping(transformationScript);
                                    } else {
                                        // if output needs to be transformed, then we first call web service and then perform transformation
                                        // if operation is called, isOperationCalled is set to true

                                        if (argument == null) {
                                            // internet connection doesn't work right now, so I comment this one for now
                                            dynamicCallToWebService(wsdlUrl, operationToExecute);
                                        } else {
                                            dynamicCallToWebService(wsdlUrl, operationToExecute, argument);
                                        }

                                        transformedOutput = InputOutputMapperExec.processSchemaMapping(transformationScript);
                                    }


                                }
                            }

                            // if there input in transformed, then we dynamically call operation with this input
                            
                            if (transformedInput != null) {
                                dynamicCallToWebService(wsdlUrl, operationToExecute, transformedInput);
                            } else if (!isOperationCalled) {

                                if (argument == null) {
                                    // internet connection doesn't work right now, so I comment this one for now
                                    dynamicCallToWebService(wsdlUrl, operationToExecute);
                                } else {
                                    dynamicCallToWebService(wsdlUrl, operationToExecute, argument);
                                }
                            }

                        }


                    }
                }
            }
        }






    }
}
