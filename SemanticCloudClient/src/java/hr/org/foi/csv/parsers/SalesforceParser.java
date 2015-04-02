/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.csv.parsers;

import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Darko Androcec
 */
public class SalesforceParser {
     public static final String SALESFORCE_CSV_DIRECTORY = "D:\\DOKTORAT\\exportPodataka\\Salesforce";

    public static void main(String[] args) {
       

        File[] files = new File(SALESFORCE_CSV_DIRECTORY).listFiles(new FileFilter() {

            public boolean accept(File file) {
                return file.getName().endsWith(".csv");
            }
        });

        if (files != null && files.length > 0) {
            
            String[] customObjectFields = new String[files.length]; 
            
            for (int i = 0; i < files.length; i++) {
                
               
                 
                String salesForceObjectName = "";
                String filename = files[i].getName();
                System.out.println("DEBUG FILENAME:" + filename);

                salesForceObjectName = filename.substring(0, filename.indexOf("."));
                
                // get important object fields - custom field ending in  __c and primary key (ID)
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
                            
                            
                            // to add primary key ad important column
                            if (strings[k].compareTo("Id")==0){
                                 if (customObjectFields[i].compareTo("") == 0){
                                    customObjectFields[i] = strings[k];
                                }
                                else{
                                    customObjectFields[i] = customObjectFields[i] + ", " +strings[k];
                                }
                                  
                                System.out.println("DEBUG: Custom primary key -->> " + strings[k]);
                            }
                            
                            if (strings[k].endsWith("__c")){
                                
                                if (customObjectFields[i].compareTo("") == 0){
                                    customObjectFields[i] = strings[k];
                                }
                                else{
                                    customObjectFields[i] = customObjectFields[i] + ", " +strings[k];
                                }
                                  
                                System.out.println("DEBUG: Custom object field -->> " + strings[k]);
                            }
                            
                        }
                        System.out.println();
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GoogleAppEngineParser.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GoogleAppEngineParser.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                
                if (salesForceObjectName != null && salesForceObjectName.length() > 1) {
                    System.out.println("DEBUG SALESFORCE OBJECT NAME:" + salesForceObjectName);
                    if (!isUserLoggedIn()){
                        login();
                    }
                    // get meta data about Salesforce objects
                    describeSalesforceObject(salesForceObjectName);
                    
                    // query all objects of a specific object type
                    
                    // I need a list of important object fields - by parsing csv file
                    String sqlQuery = "SELECT " + customObjectFields[i] + " FROM " + salesForceObjectName;
                    
                    System.out.println("DEBUG: sql query before invoking service = " + sqlQuery);
                    String queryResult = query(sqlQuery);
                    System.out.println("DEBUG QUERY RESULT ==>> " + queryResult);
                }
               
            }
        }
        
        // on the end, you call a web service to create Salesforce Data Model OWL ontology
        createSalesforceDataModelOntology();

    }

    
    private static String describeSalesforceObject(java.lang.String arg0) {
        hr.org.foi.salesforce.api.services.SalesForceServices_Service service = new hr.org.foi.salesforce.api.services.SalesForceServices_Service();
        hr.org.foi.salesforce.api.services.SalesForceServices port = service.getSalesForceServicesPort();
        return port.describeSalesforceObject(arg0);
    }

    private static boolean isUserLoggedIn() {
        hr.org.foi.salesforce.api.services.SalesForceServices_Service service = new hr.org.foi.salesforce.api.services.SalesForceServices_Service();
        hr.org.foi.salesforce.api.services.SalesForceServices port = service.getSalesForceServicesPort();
        return port.isUserLoggedIn();
    }

    private static String login() {
        hr.org.foi.salesforce.api.services.SalesForceServices_Service service = new hr.org.foi.salesforce.api.services.SalesForceServices_Service();
        hr.org.foi.salesforce.api.services.SalesForceServices port = service.getSalesForceServicesPort();
        return port.login();
    }

    private static String query(java.lang.String query) {
        hr.org.foi.salesforce.api.services.SalesForceServices_Service service = new hr.org.foi.salesforce.api.services.SalesForceServices_Service();
        hr.org.foi.salesforce.api.services.SalesForceServices port = service.getSalesForceServicesPort();
        return port.query(query);
    }

    private static boolean createSalesforceDataModelOntology() {
        hr.org.foi.salesforce.api.services.SalesForceServices_Service service = new hr.org.foi.salesforce.api.services.SalesForceServices_Service();
        hr.org.foi.salesforce.api.services.SalesForceServices port = service.getSalesForceServicesPort();
        return port.createSalesforceDataModelOntology();
    }

    
 
    
    
}
