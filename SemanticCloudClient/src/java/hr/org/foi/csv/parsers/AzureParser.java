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
public class AzureParser {

    public static final String AZURE_CSV_DIRECTORY = "D:\\DOKTORAT\\exportPodataka\\MicrosoftAzure";

    public static void main(String[] args) {


        File[] files = new File(AZURE_CSV_DIRECTORY).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.getName().endsWith(".csv");
            }
        });

        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {

                String filename = files[i].getName();
                System.out.println(filename);

                String azureTableName = filename.substring(0, filename.indexOf("."));

                String sqlQuery = "SELECT * FROM " + azureTableName;

                System.out.println("DEBUG: sql query before invoking service = " + sqlQuery);
                String queryResult = selectQuery(sqlQuery);
                System.out.println("DEBUG QUERY RESULT ==>> " + queryResult);



                /*
                 CSVReader reader;
                 try {
                 reader = new CSVReader(new FileReader(files[i].getAbsolutePath()));
                 List t1 = reader.readAll();
                 for (int j = 0; j < t1.size(); j++) {
                 String[] strings = (String[]) t1.get(j);
                 for (int k = 0; k < strings.length; k++) {
                 System.out.print(strings[k] + " ");
                 }
                 System.out.println();
                 }
                 } catch (FileNotFoundException ex) {
                 Logger.getLogger(GoogleAppEngineParser.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (IOException ex) {
                 Logger.getLogger(GoogleAppEngineParser.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 */


            }

            //Get Azure primary and foreign keys

           
            List<String> findKeysResult = findKeys();
            System.out.println("DEBUG FIND KEYS RESULT ==>> " + findKeysResult);

        }

        // on the end, you call a web service to create Azure Data Model OWL ontology
        createAzureDataModelOntology();
    }

    private static String selectQuery(java.lang.String query) {
        hr.org.foi.azure.api.services.AzureServices_Service service = new hr.org.foi.azure.api.services.AzureServices_Service();
        hr.org.foi.azure.api.services.AzureServices port = service.getAzureServicesPort();
        return port.selectQuery(query);
    }

 

    private static boolean createAzureDataModelOntology() {
        hr.org.foi.azure.api.services.AzureServices_Service service = new hr.org.foi.azure.api.services.AzureServices_Service();
        hr.org.foi.azure.api.services.AzureServices port = service.getAzureServicesPort();
        return port.createAzureDataModelOntology();
    }

    private static java.util.List<java.lang.String> findKeys() {
        hr.org.foi.azure.api.services.AzureServices_Service service = new hr.org.foi.azure.api.services.AzureServices_Service();
        hr.org.foi.azure.api.services.AzureServices port = service.getAzureServicesPort();
        return port.findKeys();
    }

    
}
