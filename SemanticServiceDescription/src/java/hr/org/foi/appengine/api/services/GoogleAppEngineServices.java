/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.appengine.api.services;

import com.google.appengine.api.capabilities.CapabilitiesService;
import com.google.appengine.api.capabilities.CapabilitiesServiceFactory;
import com.google.appengine.api.capabilities.Capability;
import com.google.appengine.api.capabilities.CapabilityStatus;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebMethod;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;
import hr.org.foi.jena.DataOntologyReader;
import hr.org.foi.jena.DataTypeConverter;
import hr.org.foi.jena.GoogleDataOntologyCreator;
import hr.org.foi.jena.TableUtil;
import hr.org.foi.jena.TypeConstants;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.List;
import com.google.appengine.api.log.*;
import hr.org.foi.binding.google.GoogleEntity;
import hr.org.foi.binding.google.GoogleKey;
import hr.org.foi.binding.google.GoogleProperty;
import java.io.File;
import java.io.FileFilter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Darko Androcec
 */
@WebService(serviceName = "GoogleAppEngineServices")
public class GoogleAppEngineServices {

    RemoteApiInstaller installer = new RemoteApiInstaller();

    // START: APP ENGINE DATASTORE API
    /**
     * Creating an Entity In Java, you create a new entity by constructing an
     * instance of class Entity, supplying the entity's kind as an argument to
     * the Entity() constructor. After populating the entity's properties if
     * necessary, you save it to the datstore by passing it as an argument to
     * the DatastoreService.put() method.
     *
     */
    // put is also used for entity update
    @WebMethod(operationName = "put")
    public String put(@WebParam(name = "entity") GoogleEntity googleEntity) {

        String finalResult = "";
        Key entityKey = null;

        try {
            DatastoreService ds = getDataStore();
            Entity entity = new Entity(googleEntity.getGoogleEntityKind());

            if (googleEntity.getGoogleEntityProperties() != null && googleEntity.getGoogleEntityProperties().size() > 0) {
                List<GoogleProperty> lList = googleEntity.getGoogleEntityProperties();
                for (GoogleProperty temp : lList) {
                    entity.setProperty(temp.getGoogleProperyName(), temp.getGoogleProperyValue());
                }
            }

            entityKey = ds.put(entity);
            finalResult = "Key of new entity is "
                    + entityKey.toString();


        } finally {

            installer.uninstall();
        }

        return finalResult;

    }

    /**
     * Retrieving an Entity To retrieve an entity identified by a given key,
     * pass the Key object to the DatastoreService.get() method:
     *
     *
     */
    @WebMethod(operationName = "retrieve")
    public String retrieve(@WebParam(name = "entity") GoogleKey key) {

        String finalResult = "";
        Key entityKey;
        System.out.println("DEBUG key.getGoogleKeyKind() = " + key.getGoogleKeyKind());
        System.out.println("DEBUG key.getGoogleKeyId() = " + key.getGoogleKeyId());



        try {
            DatastoreService ds = getDataStore();

            // exception: No API environment is registered for this thread. - ispravljeno mora biti iza inicijalizacije DatastoreService 
            entityKey = KeyFactory.createKey(key.getGoogleKeyKind(), key.getGoogleKeyId());

            Entity entity = ds.get(entityKey);
            finalResult = entity.toString();


        } catch (EntityNotFoundException ex) {
            Logger.getLogger(GoogleAppEngineServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            installer.uninstall();
        }

        return finalResult;

    }

    /**
     * Deleting an Entity Given an entity's key, you can delete the entity with
     * the DatastoreService.delete() method
     *
     *
     *
     */
    @WebMethod(operationName = "delete")
    public String delete(@WebParam(name = "entity") GoogleKey key) {

        String finalResult = "";
        Key entityKey;

        try {
            DatastoreService ds = getDataStore();
            entityKey = KeyFactory.createKey(key.getGoogleKeyKind(), key.getGoogleKeyId());
            ds.delete(entityKey);
            finalResult = "An entity was successfully deleted!";

        } catch (Exception ex) {
            finalResult = "Unsuccessful deletion!";
            Logger.getLogger(GoogleAppEngineServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            installer.uninstall();
        }

        return finalResult;

    }

    @WebMethod(operationName = "query")
    public String query(@WebParam(name = "GQLquery") String gqlQuery) {
        String result = "";

        System.out.println("DEBUG gqlQuery " + gqlQuery);
        try {

            DatastoreService ds = getDataStore();
            //    entityKey = ds.put(new Entity(key));



            Query q = new Query(gqlQuery);

            PreparedQuery pq = ds.prepare(q);
            List<Entity> entities = pq.asList(FetchOptions.Builder.withLimit(1000));

            if (entities == null) {
                System.out.println("Nema traženog entiteta");
                return null;
            } else {

                if (entities.size() > 0) {
                    for (Entity e : entities) {
                        result = result + "\n" + e.toString().trim();

                    }

                }
            }


        } finally {
            installer.uninstall();
        }

        return result;
    }

    // END: APP ENGINE DATASTORE API
    // START: The Logs API
    /*
     * get logs
     */
    // this is not a remote API operations call
    /*
     @WebMethod(operationName = "getLogs")
     public String getLogs() {
     String finalResult = "";

     LogQuery query = LogQuery.Builder.withDefaults();
     query.includeAppLogs(true);

     Iterable<RequestLogs> records = getLogs(query);


     if (records != null) {
     // Display a few properties of each request log.
     for (RequestLogs record : records) {
     finalResult = finalResult + "\nREQUEST LOG \n";
     Calendar cal = Calendar.getInstance();
     cal.setTimeInMillis(record.getStartTimeUsec() / 1000);

     finalResult = finalResult + "IP: " + record.getIp() + "\n";
     finalResult = finalResult + "Method: " + record.getMethod() + "\n";
     finalResult = finalResult + "Resource " + record.getResource() + "\n";
     finalResult = finalResult + String.format("\nDate: %s", cal.getTime().toString());


     // Display all the app logs for each request log.
     for (AppLogLine appLog : record.getAppLogLines()) {
     finalResult = finalResult + "\n" + "APPLICATION LOG" + "\n";
     Calendar appCal = Calendar.getInstance();
     appCal.setTimeInMillis(appLog.getTimeUsec() / 1000);
     finalResult = finalResult + String.format("\nDate: %s",
     appCal.getTime().toString());
     finalResult = finalResult + "\nLevel: " + appLog.getLogLevel() + "\n";
     finalResult = finalResult + "Message: " + appLog.getLogMessage() + "\n\n";
     } //for each log line


     } // for each record

     }

     return finalResult;

     }
     // END: The Logs API
   
     */
    /*
     * The Capabilities Java API
     With the Capabilities API, your application can detect outages and scheduled downtime for specific API capabilities. With this information, you can disable the unavailable capability in your application before it impacts your users.

     */
    @WebMethod(operationName = "getCapabilityStatus")
    public String getCapabilityStatus(@WebParam(name = "capability") Capability capability) {
        String result = "";
        CapabilitiesService service =
                CapabilitiesServiceFactory.getCapabilitiesService();
        CapabilityStatus status = service.getStatus(capability).getStatus();


        if (status == CapabilityStatus.DISABLED) {
            result = capability.getName() + " is not available.";
        } else {
            result = capability.getName() + " is available.";
        }
        return result;
    }

    @WebMethod(operationName = "createEntitiesFromDataOntology")
    public String createEntitiesFromDataOntology(@WebParam(name = "pathToOntology") String pathToOntology) {
        String result = "";

        DataOntologyReader.readOntology(pathToOntology);

        List<TableUtil> tables = DataOntologyReader.getTables();

        System.out.println("DEBUG Tables size " + tables.size());


        try {

            DatastoreService datastore = getDataStore();

            for (TableUtil tableUtil : tables) {

                List<String> properties = tableUtil.getProperties();
                List<String> propertyTypes = tableUtil.getPropertyTypes();
                List<ArrayList<String>> rows = tableUtil.getRows();
                List<String> identifiersValues = tableUtil.getIdentifiersValues();
                List<String> linksToOtherObjects = tableUtil.getListOfLinksBetweenDataObjects();

                System.out.println("DEBUG tableUtil " + tableUtil.toString());
                System.out.println("DEBUG properties size " + properties.size());
                System.out.println("DEBUG propertyTypes size " + propertyTypes.size());
                System.out.println("DEBUG rows size " + rows.size());
                System.out.println("DEBUG idenfiersValues " + identifiersValues.size());
                System.out.println("DEBUG linksToOtherObjects " + linksToOtherObjects.size());


                // TODO: see how to solve relationship among objects - maybe with parent keys...

                int rowCount = 0;
                for (ArrayList<String> rowData : rows) {

                    int colNum = 0;



                    Entity entity = new Entity("Migrated_" + tableUtil.getName(), identifiersValues.get(rowCount));



                    for (String column : rowData) {



                        if (properties.size() > colNum) {
                            String property = properties.get(colNum);
                            System.out.println("DEBUG PROPERTY: " + property);


                            // TODO - look at types
                            String propertyType = propertyTypes.get(colNum);
                            System.out.println("DEBUG PROPERTY TYPE: " + propertyType);

                            String convertedType = DataTypeConverter.convertFromOwlDataType(TypeConstants.GOOGLE_APP_ENGINE, propertyType);

                            System.out.println("DEBUG converted type = " + convertedType);


                            System.out.println("DEBUG ROW DATA: " + column);



                            // cut string to 250 characters - this is problem with date representation that is long string
                            if (column != null && column.length() > 250) {
                                column = column.substring(0, 250);
                            }

                            System.out.println("DEBUG test 1");

                            Object propertyValue = DataTypeConverter.convertIntoGoogleAppEngineTypes(convertedType, column);

                            System.out.println("DEBUG test 2");

                            entity.setProperty(property, propertyValue);

                            System.out.println("DEBUG test 3");
                        }

                        colNum++;

                    }



                    System.out.println("DEBUG test 4");

                    datastore.put(entity);

                    rowCount++;
                }



            }





        } finally {

            installer.uninstall();
        }


        return result;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "get")
    public String get(@WebParam(name = "keyName") String keyName) {



        try {

            DatastoreService ds = getDataStore();
            //    entityKey = ds.put(new Entity(key));

            Query q = new Query(keyName);
            q.setKeysOnly();

            PreparedQuery pq = ds.prepare(q);
            List<Entity> entities = pq.asList(FetchOptions.Builder.withLimit(1000));

            if (entities == null) {
                System.out.println("Nema traženog entiteta");
                return null;
            } else {
                System.out.println("Veličina liste entiteta " + entities.size());
                if (entities.size() == 1) {
                    for (Entity e : entities) {
                        System.out.println("DEBUG e.toString() = " + e.toString().trim());
                        return e.toString().trim();
                    }
                } else {
                    return null;
                }
            }


        } finally {
            installer.uninstall();
        }

        return null;
    }

    // for some reason public Entity getEntity (...) doesn't work - failure during deployment
    // so I used Object instead
    // and when method returns List or Entity[] there is also error
    @WebMethod(operationName = "getEntitiesOfKind")
    public List<String> getEntitiesOfKind(@WebParam(name = "entityKind") String entityKind) {

        System.out.println("Debug: Entity kind is------>>" + entityKind);

        try {

            DatastoreService ds = getDataStore();

            Query q = new Query(entityKind);
            //    q.setKeysOnly();

            PreparedQuery pq = ds.prepare(q);
            List<Entity> entities = pq.asList(FetchOptions.Builder.withLimit(1000));


            if (entities != null && entities.size() > 0) {
                System.out.println("DEBUG: Veličina liste entiteta = " + entities.size());
                // return entities;

                GoogleDataOntologyCreator.getListOfEntityList().add(entities);

                List<String> listaStringa = new ArrayList<String>();

                for (Entity e : entities) {
                    // System.out.println("DEBUG e.toString() = " + e.toString().trim()); 
                    listaStringa.add(e.toString().trim());
                }
                return listaStringa;

                /*
                 Object [] entityArray = new Object[entities.size()];
                 entities.toArray(entityArray); 
                 System.out.println("DEBUG: prvi element polja koje se vraća " + entityArray[0]);
                 return entityArray;
                 */




            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {

            installer.uninstall();

        }

        return null;

    }

    /*
     @WebMethod(operationName = "sentEmail")
     public String sentEmail()  {

     String finalResult = "";
        
     Properties props = new Properties();
     Session session = Session.getDefaultInstance(props, null);

     String msgBody = "...";

     try {
     Message msg = new MimeMessage(session);
     try {
     msg.setFrom(new InternetAddress("darkoandr@yahoo.com", "http://creditsdarko.appspot.com Admin"));
     } catch (UnsupportedEncodingException ex) {
     Logger.getLogger(GoogleAppEngineServices.class.getName()).log(Level.SEVERE, null, ex);
     }
     try {
     msg.addRecipient(Message.RecipientType.TO,
     new InternetAddress("dandrocec@foi.hr", "Darko Androcec"));
     } catch (UnsupportedEncodingException ex) {
     Logger.getLogger(GoogleAppEngineServices.class.getName()).log(Level.SEVERE, null, ex);
     }
     msg.setSubject("This is test email message from creditsdarko.appspot.com!");
     msg.setText(msgBody);
     Transport.send(msg);
    
     } catch (AddressException e) {
     // ...
     } catch (MessagingException e) {
     // ...
     }
     return finalResult;
     }
     */
    @WebMethod(operationName = "testReturnOfList")
    public List<String> testReturnOfList(@WebParam(name = "entityKind") String entityKind) {

        List<String> lista = new ArrayList<String>();
        lista.add("Darko");
        lista.add("Andročec");
        return lista;
    }

    @WebMethod(operationName = "deleteKeyName")
    public String deleteKeyName(@WebParam(name = "keyName") String keyName) {






// ... all API calls executed remotely

        // Update the options with reusable credentials so we can skip
        // authentication on subsequent calls.
        try {

            DatastoreService ds = getDataStore();
            //    entityKey = ds.put(new Entity(key));



            Query q = new Query(keyName);
            q.setKeysOnly();

            PreparedQuery pq = ds.prepare(q);
            List<Entity> entities = pq.asList(FetchOptions.Builder.withLimit(1000));

            if (entities == null) {
                System.out.println("Nema traženog entiteta");
                return null;
            } else {
                System.out.println("Veličina liste entiteta " + entities.size());
                if (entities.size() == 1) {
                    for (Entity e : entities) {
                        System.out.println("DEBUG e.toString() = " + e.toString().trim());

                        ds.delete(e.getKey());
                        return "Successful delete";
                    }
                } else {
                    return "There is no entity with specified key name";
                }
            }


        } finally {
            installer.uninstall();
        }

        return "Unsuccessful delete";
    }

    private DatastoreService getDataStore() {
        RemoteApiOptions options = new RemoteApiOptions().server(Constants.APPENGINE_APPLICATION_URI, 443).credentials(Constants.APPENGINE_USERNAME, Constants.APPENGINE_PASSWORD);

        try {
            installer.install(options);
        } catch (IOException ex) {
            Logger.getLogger(GoogleAppEngineServices.class.getName()).log(Level.SEVERE, null, ex);
        }


        // Update the options with reusable credentials so we can skip
        // authentication on subsequent calls.

        options.reuseCredentials(Constants.APPENGINE_USERNAME, installer.serializeCredentials());
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

        return ds;
    }

    private Iterable<RequestLogs> getLogs(LogQuery query) {
        RemoteApiOptions options = new RemoteApiOptions().server(Constants.APPENGINE_APPLICATION_URI, 443).credentials(Constants.APPENGINE_USERNAME, Constants.APPENGINE_PASSWORD);

        try {
            installer.install(options);
        } catch (IOException ex) {
            Logger.getLogger(GoogleAppEngineServices.class.getName()).log(Level.SEVERE, null, ex);
        }


        // Update the options with reusable credentials so we can skip
        // authentication on subsequent calls.

        options.reuseCredentials(Constants.APPENGINE_USERNAME, installer.serializeCredentials());

        Iterable<RequestLogs> records = LogServiceFactory.getLogService().fetch(query);

        return records;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "createDataModelOntology")
    public boolean createDataModelOntology() {

        getDataTypesInCurrentData();
        GoogleDataOntologyCreator.create();
        return true;
    }

    // get data types from current Google App Engine datastorage
    @WebMethod(operationName = "getDataTypesInCurrentData")
    public List<String> getDataTypesInCurrentData() {

        

        // nađen je filtrirani data container
        File[] files = new File(Constants.GOOGLE_APP_ENGINE_CSV_DIRECTORY).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.getName().contains("FILTERED_D_C");
            }
        });
        
        System.out.println("DEBUG files = " + files);
        System.out.println("DEBUG files length = " + files.length);

        // nije nađeni filtrirani data container, operaciju treba provesti za sve CSV datoteke
        // Get all entities from csv files
        if (files == null || files.length == 0) {
            files = new File(Constants.GOOGLE_APP_ENGINE_CSV_DIRECTORY).listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return file.getName().endsWith(".csv");
                }
            });
        }
        
          System.out.println("DEBUG files za sve = " + files);
        System.out.println("DEBUG files length za sve = " + files.length);

        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {


                String entityKind = "";
                String filename = files[i].getName();
                System.out.println("DEBUG FILENAME:" + filename);
                
                if (filename.contains("FILTERED_D_C")){
                    filename = filename.replaceAll("FILTERED_D_C", "");
                }
                // TODO i renamati tu datoteku opet bez ovog prefixa
                

                entityKind = filename.substring(0, filename.indexOf("."));
               System.out.println("DEBUG ENTITYKIND:" + entityKind);


                // capitalize the first letter of a string


                if (entityKind != null && entityKind.length() > 1) {
                    entityKind = entityKind.substring(0, 1).toUpperCase() + entityKind.substring(1);
                    System.out.println("DEBUG ENTITYKIND:" + entityKind);
                    getEntities(entityKind);
                }
            }
        }

        return GoogleDataOntologyCreator.getDataTypesInCurrentData();
    }

    private void getEntities(String entityKind) {


        // Calling web service that returns list of strings is doing fine...

        /*
         List<String> lista = testReturnOfList("Test");

         System.out.println("Debug veličina liste stringa ==>> " + lista.size());
         
         
         */



        List<String> entities = getEntitiesOfKind(entityKind);
        if (entities != null) {
            for (String e : entities) {
                System.out.println("DEBUG Entity String = " + e);
            }
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
}
