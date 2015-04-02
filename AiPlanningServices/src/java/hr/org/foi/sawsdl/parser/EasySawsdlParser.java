/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.sawsdl.parser;

import com.google.common.io.Files;
import com.hp.hpl.jena.graph.query.Expression;
import hr.org.foi.aiplanning.services.Constants;
import hr.org.foi.aiplanning.services.DataTypeMappingToAiGenerator;
import hr.org.foi.aiplanning.services.ServiceTypeMappingToAiGenerator;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.ow2.easywsdl.extensions.sawsdl.api.Input;
import org.ow2.easywsdl.extensions.sawsdl.api.InterfaceType;
import org.ow2.easywsdl.extensions.sawsdl.api.Operation;
import org.ow2.easywsdl.extensions.sawsdl.api.Output;
import org.ow2.easywsdl.extensions.sawsdl.api.SAWSDLException;
import org.ow2.easywsdl.extensions.sawsdl.api.SAWSDLReader;
import org.ow2.easywsdl.extensions.sawsdl.api.Service;
import org.ow2.easywsdl.extensions.sawsdl.api.schema.Schema;
import org.ow2.easywsdl.extensions.sawsdl.api.schema.Type;
import org.ow2.easywsdl.schema.api.XmlException;

/**
 *
 * @author Darko Androcec
 *
 * Try to use EasySAWDL jar
 */
public class EasySawsdlParser {

    public static String hasApiOperation = "";
    public static String hasIOType = "";
    public static String hasInputs = "";
    public static String hasOutputs = "";
    public static String hasLiftingSchemas = "";
    public static String hasLoweringSchemas = "";
    public static String allDataTypeMappings = "";
    private static final String URL_FILE_AZURE = "http://localhost:8080/AzureServices.sawsdl";
    private static final String URL_FILE_APPENGINE = "http://localhost:8080/GoogleAppEngineServices.sawsdl";
    private static final String URL_FILE_SALESFORCE = "http://localhost:8080/SalesForceServices.sawsdl";

    // TODO: add to problem files dataTypeMappingExists entries 
    public static String createProblemFile(String aiGoal) {

        String defProblemCloud = "(defproblem problem cloud \n(\n";

        System.out.println("DEBUG AI goal = " + aiGoal);
        // AI goal is = (migrateData Azure GoogleAppEngine)
        //  AI goal is = (migrateData SalesForce GoogleAppEngine)

        // if AI goal contains migrateData the get data types of source cloud to determine whether
        // data type mapping interoperability problem exists

        // MIGRATE COMPLETE DATA
        if (aiGoal.contains(Constants.ACTION_MIGRATE_DATA)) {

            String dataTypePrefix = "";
            List<String> dataTypes = new ArrayList();
            if (aiGoal.contains("migrateData")) {
                String sourceCloud = aiGoal.substring(aiGoal.indexOf(" ") + 1, aiGoal.lastIndexOf(" "));
                System.out.println("DEBUG source cloud = " + sourceCloud);

                if (sourceCloud.contains("Azure")) {
                    dataTypes = getAzureDataTypesInCurrentData();
                    dataTypePrefix = "azure";
                } else if (sourceCloud.contains("SalesForce")) {
                    dataTypes = getSalesforceDataTypesInCurrentData();
                    dataTypePrefix = "salesforce";
                } else if (sourceCloud.contains("GoogleAppEngine")) {
                    dataTypes = getGoogleAppEngineDataTypesInCurrentData();
                    dataTypePrefix = "google";
                }
            }

            String datatypesForAI = "";
            Iterator<String> iterator = dataTypes.iterator();
            while (iterator.hasNext()) {
                String datatype = iterator.next();
                datatype = datatype.replace(".", "_");
                datatypesForAI = datatypesForAI + "(typeInCurrentData " + dataTypePrefix + datatype.toLowerCase() + ")\n";
            }

            //get source cloud data types

            parseSawsdlFile(URL_FILE_AZURE);

            parseSawsdlFile(URL_FILE_APPENGINE);

            parseSawsdlFile(URL_FILE_SALESFORCE);



            // System.out.println("DEBUG hasApiOperation string = " + hasApiOperation);

            // operations from parsed sawsdl files
            defProblemCloud = defProblemCloud + hasApiOperation + "\n";

            // add data types from source cloud's current data

            defProblemCloud = defProblemCloud + datatypesForAI + "\n";

            // get all data type mappings from the PaaS omntology
            allDataTypeMappings = DataTypeMappingToAiGenerator.getAllDataTypeMappings();
            allDataTypeMappings = allDataTypeMappings.replace(".", "_");

            defProblemCloud = defProblemCloud + allDataTypeMappings + "\n";



        } // MIGRATE DATA CONTAINER
        else if (aiGoal.contains(Constants.ACTION_MIGRATE_DATA_CONTAINER)) {


            System.out.println("DEBUG MIGRATE DATA CONTAINER)");



            parseSawsdlFile(URL_FILE_AZURE);

            parseSawsdlFile(URL_FILE_APPENGINE);

            parseSawsdlFile(URL_FILE_SALESFORCE);

            // operations from parsed sawsdl files
            defProblemCloud = defProblemCloud + hasApiOperation + "\n";

            // I/O types of operations from parsed sawsdl files
            defProblemCloud = defProblemCloud + hasIOType + "\n";

            //hasInputs
            defProblemCloud = defProblemCloud + hasInputs + "\n";

            //hasOutputs
            defProblemCloud = defProblemCloud + hasOutputs + "\n";


            // temporarly adding fixed text
            //     String fixedText = "(isOfType Customer__c SObject)(isOfType GoogleCustomer GoogleEntity)(hasServiceIOMapping GoogleCustomer Customer__c)";
            //     defProblemCloud = defProblemCloud + fixedText + "\n";

            // obtain this text automatically

            String allServiceDataTypeMappings = ServiceTypeMappingToAiGenerator.getAllServiceTypeMappings();


            defProblemCloud = defProblemCloud + allServiceDataTypeMappings + "\n";



        } // ADD EXISTING USER TO ANOTHER PAAS
        else if (aiGoal.contains(Constants.ACTION_ADDUSER_TO_ANOTHERPAAS)) {
            System.out.println("DEBUG ADD USER TO ANOTHER PAAS)");

            parseSawsdlFile(URL_FILE_AZURE);

            parseSawsdlFile(URL_FILE_APPENGINE);

            parseSawsdlFile(URL_FILE_SALESFORCE);

            // operations from parsed sawsdl files
            defProblemCloud = defProblemCloud + hasApiOperation + "\n";

            // I/O types of operations from parsed sawsdl files
            defProblemCloud = defProblemCloud + hasIOType + "\n";

            //hasInputs
            defProblemCloud = defProblemCloud + hasInputs + "\n";

            //hasOutputs
            defProblemCloud = defProblemCloud + hasOutputs + "\n";
            
            // has lifting schemas
             defProblemCloud = defProblemCloud + hasLiftingSchemas + "\n";
             
            // has lowering schemas
            defProblemCloud = defProblemCloud + hasLoweringSchemas + "\n";

        }


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


            System.out.println("Problematic Url= " + url);

            reader = SAWSDLFactory.newInstance().newSAWSDLReader();
            Description desc = reader.read(new URL(url));


            URI uri = desc.getDocumentBaseURI();
            System.out.println("Document URI " + uri);

            String uriString = uri.toString();
            String nameOfService = uriString.substring(uriString.lastIndexOf("/") + 1, uriString.indexOf("Services"));
            System.out.println("NAME OF A SERVICE = " + nameOfService);

            String allOperations = " ";
            String allIOTypes = " ";

            List<InterfaceType> interfaceTypes = desc.getInterfaces();
            for (InterfaceType type : interfaceTypes) {
                List<Operation> operations = type.getOperations();


                System.out.println("DEBUG type name: " + type.getQName().toString());

                for (Operation operation : operations) {
                    System.out.println("Operation = " + operation.getQName().toString());



                    AttrExtensions attrExtensions = operation.getAttrExtensions();
                    String dataOperationName = "";



                    if (attrExtensions != null) {
                        List<URI> modelReferences = attrExtensions.getModelReference();
                        //   System.out.println("modelReferences size " + modelReferences.size());


                        for (URI modelReference : modelReferences) {
                            System.out.println("Model reference = " + modelReference.toString());

                            dataOperationName = modelReference.toString().substring(modelReference.toString().indexOf("#") + 1);
                            System.out.println("DEBUG DATA OPERATION NAME = " + dataOperationName);

                            if (!allOperations.contains(dataOperationName)) {
                                hasApiOperation = hasApiOperation + "(hasApiOperation " + nameOfService + " " + dataOperationName + ")\n";
                                allOperations = allOperations + dataOperationName;
                            }


                        }
                    }





                    // get model references on operation's inputs
                    List<URI> modelReferencesInput = operation.getInput().getModelReference();

                    //       System.out.println("DEBUG operation.getInput().getMessageName = " + operation.getInput().getMessageName());


                    for (URI modelReference : modelReferencesInput) {
                        System.out.println("Model reference on input = " + modelReference.toString());

                        String inputType = modelReference.toString().substring(modelReference.toString().indexOf("#") + 1);
                        System.out.println("DEBUG TYPE OF OPERATION INPUT = " + inputType);

                        String hasInputsString = "(operationHasInput " + nameOfService + " " + dataOperationName + " " + inputType + ")\n";

                        if (!hasInputs.contains(hasInputsString)) {
                            hasInputs = hasInputs + hasInputsString;
                        }


                        if (!allIOTypes.contains(inputType)) {
                            hasIOType = hasIOType + "(ServiceIOType " + inputType + ")\n";
                            allIOTypes = allOperations + inputType;
                        }

                    }


                    // get model references on operation's outputs
                    List<URI> modelReferencesOutput = operation.getOutput().getModelReference();


                    for (URI modelReference : modelReferencesOutput) {
                        System.out.println("Model reference on output = " + modelReference.toString());

                        String outputType = modelReference.toString().substring(modelReference.toString().indexOf("#") + 1);
                        System.out.println("DEBUG TYPE OF OPERATION OUTPUT = " + outputType);

                        String hasOutputString = "(operationHasOutput " + nameOfService + " " + dataOperationName + " " + outputType + ")\n";

                        if (!hasOutputs.contains(hasOutputString)) {
                            hasOutputs = hasOutputs + hasOutputString;
                        }
                        if (!allIOTypes.contains(outputType)) {
                            hasIOType = hasIOType + "(ServiceIOType " + outputType + ")\n";
                            allIOTypes = allOperations + outputType;
                        }

                    }


                }

                // get lifting and lowering schemas from types

                List<Schema> schemas = desc.getTypes().getSchemas();
                for (Schema schema : schemas) {
                    List<Type> types = schema.getTypes();
                    for (Type xsdType : types) {
                        
                        String inputOrOutputType = null;
                        // System.out.println("DEBUG type " + type);
                        List<URI> modelReferencesTypes = xsdType.getModelReference();
                        for (URI modelReference : modelReferencesTypes) {
                            //  System.out.println("Model reference on type = " + modelReference.toString());

                            inputOrOutputType = modelReference.toString().substring(modelReference.toString().indexOf("#") + 1);
                            System.out.println("Model reference on input or output = " + inputOrOutputType);

                        }

                        // lifting schemas
                        List<URI> liftingSchemaOnTypes = xsdType.getLiftingSchemaMapping();
                        for (URI liftingSchema : liftingSchemaOnTypes) {
                            //     System.out.println("Lifting schema on type = " + liftingSchema.toString());

                            String liftingSchemaString = liftingSchema.toString().substring(liftingSchema.toString().indexOf("#") + 1);
                            System.out.println("Lifting schema = " + liftingSchemaString);
                            liftingSchemaString = liftingSchemaString.substring(liftingSchemaString.lastIndexOf("/")+1, liftingSchemaString.indexOf(".xslt"));
                            
                            hasLiftingSchemas = hasLiftingSchemas + "(TypeHasLiftingSchema " + nameOfService + " " + inputOrOutputType + " " + liftingSchemaString + ")\n";


                        }
                        // lowering schemas
                        List<URI> loweringSchemaOnTypes = xsdType.getLoweringSchemaMapping();
                        for (URI loweringSchema : loweringSchemaOnTypes) {

                            String loweringSchemaString = loweringSchema.toString().substring(loweringSchema.toString().indexOf("#") + 1);
                            System.out.println("Lowering schema = " + loweringSchemaString);
                            loweringSchemaString = loweringSchemaString.substring(loweringSchemaString.lastIndexOf("/")+1, loweringSchemaString.indexOf(".xslt"));
                            
                            hasLoweringSchemas = hasLoweringSchemas + "(TypeHasLoweringSchema " + nameOfService + " " + inputOrOutputType + " " + loweringSchemaString + ")\n";


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

                 
                 */
            }

        } catch (IOException ex) {
            Logger.getLogger(EasySawsdlParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(EasySawsdlParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAWSDLException ex) {
            Logger.getLogger(EasySawsdlParser.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    private static java.util.List<java.lang.String> getGoogleAppEngineDataTypesInCurrentData() {
        hr.org.foi.appengine.api.services.GoogleAppEngineServices_Service service = new hr.org.foi.appengine.api.services.GoogleAppEngineServices_Service();
        hr.org.foi.appengine.api.services.GoogleAppEngineServices port = service.getGoogleAppEngineServicesPort();
        return port.getDataTypesInCurrentData();
    }

    private static java.util.List<java.lang.String> getAzureDataTypesInCurrentData() {
        hr.org.foi.azure.api.services.AzureServices_Service service = new hr.org.foi.azure.api.services.AzureServices_Service();
        hr.org.foi.azure.api.services.AzureServices port = service.getAzureServicesPort();
        return port.getDataTypesInCurrentData();
    }

    private static java.util.List<java.lang.String> getSalesforceDataTypesInCurrentData() {
        hr.org.foi.salesforce.api.services.SalesForceServices_Service service = new hr.org.foi.salesforce.api.services.SalesForceServices_Service();
        hr.org.foi.salesforce.api.services.SalesForceServices port = service.getSalesForceServicesPort();
        return port.getDataTypesInCurrentData();
    }
}
