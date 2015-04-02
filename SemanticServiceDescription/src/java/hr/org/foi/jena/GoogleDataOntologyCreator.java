/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.jena;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.XSD;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Darko Androcec
 */
public class GoogleDataOntologyCreator {

    public static final String ONTOLOGY_NAME = "GoogleAppEngineDataModel#";
    public static final String ONTOLOGY_DISKFILE = "D:\\DOKTORAT\\generiraneOntologije\\GoogleAppEngineDataModel.owl";
    public static ArrayList<List<Entity>> listOfEntityList = new ArrayList<List<Entity>>();

    // get data types from current Google App Engine datastorage
    public static List<String> getDataTypesInCurrentData() {

        List<String> result = new ArrayList();

        System.out.println("DEBUG: getDataTypesInCurrentData()");


        if (listOfEntityList != null && listOfEntityList.size() > 0) {
            System.out.println("DEBUG: List of entities list size = " + listOfEntityList.size());



            for (List<Entity> entityList : listOfEntityList) {
                int i = 0;
                for (Entity e : entityList) {

                    if (i == 0) {

                        //get all properties of a entity
                        Map<String, Object> entityProperties = e.getProperties();

                        System.out.println("Debug entityProperties.keySet() = " + entityProperties.keySet().toString());

                        String[] propertyNames = entityProperties.keySet().toArray(new String[entityProperties.size()]);

                        for (final String propertyName : propertyNames) {

                            System.out.println("PROPERTY NAME IS = " + propertyName);
                            // propertyNames string contains
                            // "com.google.appengine.api.datastore.PostalAddress" if it is a Postal Address
                        }

                        for (Map.Entry<String, Object> entry : entityProperties.entrySet()) {
                            String key = entry.getKey();



                            System.out.println("DEBUG: Map key " + key);
                            // create properties of the class using key values

                            if (entry.getValue() != null && entry.getValue().getClass() != null) {
                                String dataType = entry.getValue().getClass().getCanonicalName();
                                System.out.println("DEBUG dataType == " + dataType);
                                if (dataType == null){
                                    System.out.println("DEBUG dataType is NULL == " + dataType + " in ");
                                }
                                
                                // java.util.ArrayList is not permitted as Google DataStore data type - it is here modified to string
                                if (dataType.contains("java.util.ArrayList")){
                                    dataType = "java.lang.String";
                                }

                                if ( result != null && !result.contains(dataType)) {
                                    result.add(dataType);
                                }
                               
                            }
                        }
                    }
                }
            }
        }

        
        System.out.println("DEBUG DATATYPES RESULT " + result);



        return result;

    }

    public static void create() {
        
         System.out.println("DEBUG: List of entities" + listOfEntityList);
        
        if (listOfEntityList != null && listOfEntityList.size() > 0) {
            System.out.println("DEBUG: List of entities list size = " + listOfEntityList.size());

            // create ontology using Jena
            String baseURI = "http://hr.org.foi.ontology/" + ONTOLOGY_NAME;
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

            // for first element of a list, I need to create OWL class
            OntClass ontologyClass = null;

            // create object property for object relationship (similar to foreign keys)
            ObjectProperty objectRelationshipProperty = model.createObjectProperty(baseURI + "hasLinkToObject");
            objectRelationshipProperty.setDomain(OWL.Thing);
            objectRelationshipProperty.setRange(OWL.Thing);

            // add key to identifier data ptoperty of the ontology
            DatatypeProperty dataKeyProperty = model.createDatatypeProperty(baseURI + "identifier");
            dataKeyProperty.setDomain(OWL.Thing);
            dataKeyProperty.setRange(XSD.xstring);



            List<Key> relatedKeys = new ArrayList();

            for (List<Entity> entityList : listOfEntityList) {
                int i = 0;
                for (Entity e : entityList) {
                    
                    String shortEntityName = e.getKind();
                    
                    if (shortEntityName.contains("Entity")){
                        shortEntityName = shortEntityName.substring(0, shortEntityName.indexOf("Entity"));
                    }
                    
                    shortEntityName = shortEntityName.toLowerCase();

                    String keyString = "";
                    if (e.getKey().getName() != null) {
                        keyString = e.getKey().getName();
                    } else {
                        keyString = new Long(e.getKey().getId()).toString();
                    }

                    System.out.println("DEBUG e.getKey() ==> " + e.getKey());
                    String keyColumnString = e.getKey().toString().substring(0, e.getKey().toString().indexOf("("));


                    if (i == 0) {
                        // create new OWL class
                        ontologyClass = model.createClass(baseURI + e.getKind());

                        System.out.println("DEBUG KEY STRING " + keyString);

                        //get all properties of a entity
                        Map<String, Object> entityProperties = e.getProperties();

                        System.out.println("Debug entityProperties.keySet() = " + entityProperties.keySet().toString());

                        String[] propertyNames = entityProperties.keySet().toArray(new String[entityProperties.size()]);

                        for (final String propertyName : propertyNames) {

                            System.out.println("PROPERTY NAME IS = " + propertyName);
                            // propertyNames string contains
                            // "com.google.appengine.api.datastore.PostalAddress" if it is a Postal Address
                        }

                        for (Map.Entry<String, Object> entry : entityProperties.entrySet()) {
                            String key = entry.getKey();



                            System.out.println("DEBUG: Map key " + key);
                            // create properties of the class using key values
                            DatatypeProperty dataProperty = model.createDatatypeProperty(baseURI + shortEntityName + "_" + key);
                            dataProperty.setDomain(ontologyClass);

                            if (entry.getValue() != null && entry.getValue().getClass() != null) {
                                String dataType = entry.getValue().getClass().getCanonicalName();
                                if (dataType != null) {
                                    System.out.println("DEBUG dataType == " + dataType);
                                    Resource resurs = DataTypeConverter.convertToOwlDataPropertyRange(TypeConstants.GOOGLE_APP_ENGINE, dataType);


                                    dataProperty.setRange(resurs);
                                } else {
                                    dataProperty.setRange(XSD.xstring);
                                }
                            } else {
                                dataProperty.setRange(XSD.xstring);
                            }
                        }
                    }

                    // for all elements, I need to create OWL individuals
                    if (ontologyClass != null) {

                        int num = i + 1;
                        Individual individual = model.createIndividual(baseURI + e.getKind() + "_" + keyString, ontologyClass);

                        // add instance to identifier property

                        if (dataKeyProperty != null) {

                            individual.addProperty(dataKeyProperty, keyString, XSDDatatype.XSDstring);

                        }


                        // store related key into list
                        if (e.getKey().getParent() != null) {
                            relatedKeys.add(e.getKey());



                        }


                        Map<String, Object> properties = e.getProperties();
                        for (Map.Entry<String, Object> entry : properties.entrySet()) {
                            String key = entry.getKey();

                            if (entry.getValue() != null) {
                                String value = entry.getValue().toString();

                                String dataType = entry.getValue().getClass().getCanonicalName();
                                System.out.println("DEBUG dataType == " + dataType);

                                DatatypeProperty dataProperty = model.getDatatypeProperty(baseURI + shortEntityName + "_" + key);


                                if (dataProperty != null) {
                                    RDFDatatype datatype = DataTypeConverter.convertToOwlDataTypesForIndividual(TypeConstants.GOOGLE_APP_ENGINE, dataType);

                                    individual.addProperty(dataProperty, value, datatype);

                                }
                            }

                        }

                    }


                    System.out.println("DEBUG Entity in GoogleDataOntologyCreator =>" + e.toString());
                    i++;
                }

            }

            // add instances for hasLinkObject properties - for foreign key relationship
            for (Key relatedKey : relatedKeys) {
                System.out.println("DEBUG key " + relatedKey.toString());
                System.out.println("DEBUG key parent " + relatedKey.getParent().toString());

                String master = relatedKey.toString();
                master = master.replace("\"", "");
                master = master.substring(master.indexOf("/") + 1, master.lastIndexOf(")"));
                master = master.replace("(", "_");
                System.out.println("DEBUG master = " + master);

                String child = relatedKey.getParent().toString();
                child = child.replace("\"", "");
                child = child.substring(0, child.indexOf(")"));
                child = child.replace("(", "_");
                System.out.println("DEBUG child = " + child);

                Individual masterIndividual = model.getIndividual(baseURI + master);
                Individual childIndividual = model.getIndividual(baseURI + child);

                masterIndividual.addProperty(objectRelationshipProperty, childIndividual);


            }


            // on the end, write ontology to a specified file
            try {

                File file = new File(ONTOLOGY_DISKFILE);
                model.write(new FileOutputStream(file));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<List<Entity>> getListOfEntityList() {
        return listOfEntityList;
    }

    public static void setListOfEntityList(ArrayList<List<Entity>> listOfEntityList) {
        GoogleDataOntologyCreator.listOfEntityList = listOfEntityList;
    }

 
}
