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
import com.google.appengine.api.datastore.Entity;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.Dispatch;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Service;
import java.io.StringReader;

/**
 *
 * @author Darko Androcec
 */
public class GoogleAppEngineParser {

    //public static final String GOOGLE_APP_ENGINE_CSV_DIRECTORY = "D:\\DOKTORAT\\exportPodataka\\GoogleAppEngine";
    
    // for migrating vosaoCms
    public static final String GOOGLE_APP_ENGINE_CSV_DIRECTORY = "D:\\DOKTORAT\\exportPodataka\\Vosao";
    
    public static final String GOOGLE_KEY_HEADER = "key";

    public static void main(String[] args) {


        File[] files = new File(GOOGLE_APP_ENGINE_CSV_DIRECTORY).listFiles(new FileFilter() {

            public boolean accept(File file) {
                return file.getName().endsWith(".csv");
            }
        });

        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {


                String entityKind = "";
                String filename = files[i].getName();
                System.out.println("DEBUG FILENAME:" + filename);

                entityKind = filename.substring(0, filename.indexOf("s."));


                // capitalize the first letter of a string


                if (entityKind != null && entityKind.length() > 1) {
                    entityKind = entityKind.substring(0, 1).toUpperCase() + entityKind.substring(1);
                    System.out.println("DEBUG ENTITYKIND:" + entityKind);
                    getEntities(entityKind);
                }
                
                
                

                /* For Google App Engine, there is no need to parse CSV file, I only need entity kind and I can get it from .csv file name
                
                
                CSVReader reader;
                try {
                reader = new CSVReader(new FileReader(files[i].getAbsolutePath()));
                List t1 = reader.readAll();
                
                int keyPosition = -1;
                
                for (int j = 0; j < t1.size(); j++) {
                String[] strings = (String[]) t1.get(j);
                
                for (int k = 0; k < strings.length; k++) {
                if (keyPosition != -1 && k==keyPosition){
                //  System.out.println("Key value is: " + strings[k]);
                getEntity(strings[k]);
                }
                if (strings[k].compareTo(GOOGLE_KEY_HEADER) == 0){
                //   System.out.println("Key is on position " + k);
                keyPosition=k;
                }
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
            
            // only for specific debugging purposes, later remove
           // getEntities("Migrated4Customer");
        }
        
        // on the end, you call a web service to create Google Data Model OWL ontology
        createDataModelOntology();

    }

    private static void getEntities(String entityKind) {
        
        
        // Calling web service that returns list of strings is doing fine...
        
        /*
        List<String> lista = testReturnOfList("Test");

        System.out.println("Debug veličina liste stringa ==>> " + lista.size());
         
         
         */
        
        
        
        List<String> entities = getEntitiesOfKind(entityKind);
         for (String e : entities) {
                   System.out.println("DEBUG Entity String = " + e); 
         }           
        


        if (entities == null) {
            System.out.println("Lista entiteta je null");
        } else {
            System.out.println("Veličina liste entiteta je " + entities.size());
        }
         
        


        /*
        for (Entity e : entities) {
        System.out.println("DEBUG e.toString() = " + e.toString().trim());              
        }
         * 
         */
        

    }

    

    private static java.util.List<java.lang.String> getEntitiesOfKind(java.lang.String entityKind) {
        hr.org.foi.appengine.api.services.GoogleAppEngineServices_Service service = new hr.org.foi.appengine.api.services.GoogleAppEngineServices_Service();
        hr.org.foi.appengine.api.services.GoogleAppEngineServices port = service.getGoogleAppEngineServicesPort();
        return port.getEntitiesOfKind(entityKind);
    }

    private static boolean createDataModelOntology() {
        hr.org.foi.appengine.api.services.GoogleAppEngineServices_Service service = new hr.org.foi.appengine.api.services.GoogleAppEngineServices_Service();
        hr.org.foi.appengine.api.services.GoogleAppEngineServices port = service.getGoogleAppEngineServicesPort();
        return port.createDataModelOntology();
    }

   

   
   
    
    
}
