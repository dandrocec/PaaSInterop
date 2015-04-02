/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.test.xslt.transform;

import hr.org.foi.aiplanning.services.WebServicesFromPlanExecutor;
import hr.org.foi.sawsdl.parser.EasySawsdlParser;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.ow2.easywsdl.extensions.sawsdl.SAWSDLFactory;
import org.ow2.easywsdl.extensions.sawsdl.api.AttrExtensions;
import org.ow2.easywsdl.extensions.sawsdl.api.Description;
import org.ow2.easywsdl.extensions.sawsdl.api.Input;
import org.ow2.easywsdl.extensions.sawsdl.api.InterfaceType;
import org.ow2.easywsdl.extensions.sawsdl.api.Operation;
import org.ow2.easywsdl.extensions.sawsdl.api.SAWSDLException;
import org.ow2.easywsdl.extensions.sawsdl.api.SAWSDLReader;
import org.ow2.easywsdl.extensions.sawsdl.api.Types;
import org.ow2.easywsdl.extensions.sawsdl.api.schema.Schema;
import org.ow2.easywsdl.extensions.sawsdl.api.schema.Type;
import org.ow2.easywsdl.schema.api.Element;


/**
 *
 * @author Darko Androcec
 */

// try to manually test user info part of use case 2
public class XSLTTransformerTest {

    private static long timeout = 6000000;
    
     private static final String URL_SALESFORCE_SAWSDL = "http://localhost:8080/SalesForceServices.sawsdl";
      private static final String URL_SALESFORCE_WSDL = "http://localhost:8080/SemanticServiceDescription1/SalesForceServices?wsdl";

    public static void main(String[] args) {
        executeComposition("login", "getUserInfo", "create");
    }

    public static String executeComposition(String operationName1, String operationName2, String operationName3) {
        String rez = "";

        parseSawsdlFile(URL_SALESFORCE_SAWSDL); 
        
        dynamicCallToWebService(URL_SALESFORCE_WSDL, operationName1);
        dynamicCallToWebService(URL_SALESFORCE_WSDL, operationName2);

        return rez;
    }

    public static void parseSawsdlFile(String url) {
        // Read a SAWSDL description

        SAWSDLReader reader;
        try {


            System.out.println("Problematic Url= " + url);

            reader = SAWSDLFactory.newInstance().newSAWSDLReader();
            Description desc = reader.read(new URL(url));
            
            

            URI uri = desc.getDocumentBaseURI();
            System.out.println("Document URI " + uri);

            String uriString = uri.toString();
            String nameOfService = uriString.substring(uriString.lastIndexOf("/") + 1);
            System.out.println("NAME OF A SERVICE = " + nameOfService);

            String allOperations = " ";
            String allIOTypes = " ";
            
            

            List<InterfaceType> interfaceTypes = desc.getInterfaces();
            for (InterfaceType type : interfaceTypes) {
                List<Operation> operations = type.getOperations();
                
          

                System.out.println("DEBUG type name: " + type.getQName().toString());

                for (Operation operation : operations) {
                    // System.out.println("Operation = " + operation.getQName().toString());



                    AttrExtensions attrExtensions = operation.getAttrExtensions();
                    String dataOperationName = "";

                   

                    if (attrExtensions != null) {
                        List<URI> modelReferences = attrExtensions.getModelReference();
                        
                                                    
                        //   System.out.println("modelReferences size " + modelReferences.size());


                        for (URI modelReference : modelReferences) {
                            System.out.println("Model reference = " + modelReference.toString());

                            dataOperationName = modelReference.toString().substring(modelReference.toString().indexOf("#") + 1);
                            System.out.println("DEBUG DATA OPERATION NAME = " + dataOperationName);

                           


                        }
                    }





                    

                }

                
            
            }
            
            
           // get annotations on types
           List<Schema> schemas = desc.getTypes().getSchemas();
            for (Schema schema : schemas) {
                List<Type> types = schema.getTypes();
                for (Type type : types) {
                   // System.out.println("DEBUG type " + type);
                    
                    List<URI> modelReferencesTypes = type.getModelReference();
                    for (URI modelReference : modelReferencesTypes) {
                      //  System.out.println("Model reference on type = " + modelReference.toString());

                        String outputType = modelReference.toString().substring(modelReference.toString().indexOf("#") + 1);
                        System.out.println("Model reference on output = " + outputType);
                        
                        


                    }
                    
                    // lifting schemas
                    List<URI> liftingSchemaOnTypes = type.getLiftingSchemaMapping();
                    for (URI liftingSchema : liftingSchemaOnTypes) {
                   //     System.out.println("Lifting schema on type = " + liftingSchema.toString());

                        String liftingSchemaString = liftingSchema.toString().substring(liftingSchema.toString().indexOf("#") + 1);
                        System.out.println("Lifting schema = " + liftingSchemaString);


                    }
                    // lowering schemas
                    List<URI> loweringSchemaOnTypes =  type.getLoweringSchemaMapping();
                     for (URI loweringSchema : loweringSchemaOnTypes) {
                        
                        String loweringSchemaString = loweringSchema.toString().substring(loweringSchema.toString().indexOf("#") + 1);
                        System.out.println("Lowering schema = " + loweringSchemaString);

                    }
                    
                    
                }
                
            }
            
           
           
           

        } catch (IOException ex) {
            Logger.getLogger(XSLTTransformerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(XSLTTransformerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAWSDLException ex) {
            Logger.getLogger(XSLTTransformerTest.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public static Object dynamicCallToWebService(String serviceUrl, String serviceName) {
        
        Object result = null;

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
        
 
        
        
        // disable JAXB validation
        /*
         java.util.Map<String, Object> requestContext =
         client.getRequestContext();
         requestContext.put("set-jaxb-validation-event-handler",  "false");
          
         */
         
         
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
        try {
            res = client.invoke(serviceName);
         //   System.out.println("Service responselength: " + res.length);
            System.out.println("Service response: " + res[0]);
            result = res[0];
            
        } catch (Exception ex) {
            Logger.getLogger(XSLTTransformerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;

    }

    public static void dynamicCallToWebService(String serviceUrl, String serviceName, String argument) {

        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(serviceUrl);

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
        try {
            res = client.invoke(serviceName, argument);
           
            System.out.println("Service response: " + res[0]);
        } catch (Exception ex) {
            Logger.getLogger(XSLTTransformerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
