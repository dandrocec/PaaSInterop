/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.sawsdl.parser;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.Element;
import org.ow2.easywsdl.extensions.sawsdl.SAWSDLFactory;
import org.ow2.easywsdl.extensions.sawsdl.api.AttrExtensions;
import org.ow2.easywsdl.wsdl.api.Binding;
import org.ow2.easywsdl.extensions.sawsdl.api.BindingOperation;
import org.ow2.easywsdl.extensions.sawsdl.api.Description;
import org.ow2.easywsdl.extensions.sawsdl.api.Endpoint;
import org.ow2.easywsdl.extensions.sawsdl.api.Include;
import org.ow2.easywsdl.extensions.sawsdl.api.InterfaceType;
import org.ow2.easywsdl.extensions.sawsdl.api.Operation;
import org.ow2.easywsdl.extensions.sawsdl.api.SAWSDLException;
import org.ow2.easywsdl.extensions.sawsdl.api.SAWSDLReader;
import org.ow2.easywsdl.extensions.sawsdl.api.Service;
import org.ow2.easywsdl.schema.api.XmlException;

/**
 *
 * @author Darko Androcec
 *
 * Try to use EasySAWDL jar
 */
public class EasySawsdlParser {

    public static String hasApiOperation = "";
    private static final String URL_FILE_AZURE = "http://localhost:8080/AzureServices.sawsdl";
    private static final String URL_FILE_APPENGINE = "http://localhost:8080/GoogleAppEngineServices.sawsdl";
    private static final String URL_FILE_SALESFORCE = "http://localhost:8080/SalesForceServices.sawsdl";

     public String createProblemFileFromRpcService (String aiGoal){

        parseSawsdlFile(URL_FILE_AZURE);

        parseSawsdlFile(URL_FILE_APPENGINE);

        parseSawsdlFile(URL_FILE_SALESFORCE);

        String defProblemCloud = "(defproblem problem cloud \n(\n";

        // System.out.println("DEBUG hasApiOperation string = " + hasApiOperation);

        // operation from parsed sawsdl files
        defProblemCloud = defProblemCloud + hasApiOperation + "\n";
        defProblemCloud = defProblemCloud
                + " ) \n"
                + "\n"
                + "   (" + aiGoal + ")\n"
                + ")\n"
                + "";
        System.out.println("DEBUG defProblemCloud = " + defProblemCloud);

        File destination = new File("D:\\DOKTORAT\\JShop2CloudSourceFiles\\problem");

        System.out.println(destination.getAbsolutePath());
        try {
            Files.write(defProblemCloud, destination, Charset.forName("UTF-8"));
        } catch (IOException e) {
            System.out.println(e);
        }

        return defProblemCloud;
        
        
    }
    
    
    
    public static void main(String[] args) throws XmlException {

        parseSawsdlFile(URL_FILE_AZURE);

        parseSawsdlFile(URL_FILE_APPENGINE);

        parseSawsdlFile(URL_FILE_SALESFORCE);

        String defProblemCloud = "(defproblem problem cloud \n(\n";

        // System.out.println("DEBUG hasApiOperation string = " + hasApiOperation);

        // operation from parsed sawsdl files
        defProblemCloud = defProblemCloud + hasApiOperation + "\n";

    

        defProblemCloud = defProblemCloud
                + " ) \n"
                + "\n"
                + "   ((migrateData GoogleAppEngine SalesForce))\n"
                + ")\n"
                + "";
        System.out.println("DEBUG defProblemCloud = " + defProblemCloud);

        File destination = new File("D:\\DOKTORAT\\JShop2CloudSourceFiles\\problem");

        System.out.println(destination.getAbsolutePath());
        try {
            Files.write(defProblemCloud, destination, Charset.forName("UTF-8"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void parseSawsdlFile(String url) {
        // Read a SAWSDL description

        SAWSDLReader reader;
        try {

            reader = SAWSDLFactory.newInstance().newSAWSDLReader();
            Description desc = reader.read(new URL(url));

            URI uri = desc.getDocumentBaseURI();
            System.out.println("Document URI " + uri);

            String uriString = uri.toString();
            String nameOfService = uriString.substring(uriString.lastIndexOf("/") + 1, uriString.indexOf("Services"));
            System.out.println("NAME OF A SERVICE = " + nameOfService);
            
            String allOperations = " ";

            List<InterfaceType> interfaceTypes = desc.getInterfaces();
            for (InterfaceType type : interfaceTypes) {
                List<Operation> operations = type.getOperations();

                System.out.println("DEBUG type name: " + type.getQName().toString());

                for (Operation operation : operations) {
                    System.out.println("Operation = " + operation.getQName().toString());



                    AttrExtensions attrExtensions = operation.getAttrExtensions();

                    if (attrExtensions != null) {
                        List<URI> modelReferences = attrExtensions.getModelReference();
                        //   System.out.println("modelReferences size " + modelReferences.size());


                        for (URI modelReference : modelReferences) {
                            System.out.println("Model reference = " + modelReference.toString());

                            String dataOperationName = modelReference.toString().substring(modelReference.toString().indexOf("#") + 1);
                            System.out.println("DEBUG DATA OPERATION NAME = " + dataOperationName);
                            
                            if (!allOperations.contains(dataOperationName)){
                                hasApiOperation = hasApiOperation + "(hasApiOperation " + nameOfService + " " + dataOperationName + ")\n";
                                allOperations = allOperations + dataOperationName;
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
