/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.jena;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.XSD;
import com.sforce.soap.enterprise.DescribeSObjectResult;
import java.util.ArrayList;
import com.sforce.soap.enterprise.sobject.SObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.sforce.soap.enterprise.Field;
import com.sforce.soap.enterprise.FieldType;
import hr.org.foi.salesforce.api.services.Constants;
import java.util.List;

/**
 *
 * @author Darko Androcec
 */
public class SalesforceDataOntologyCreator {

    public static final String ONTOLOGY_NAME = "SalesforceDataModel#";
    public static final String ONTOLOGY_DISKFILE = "D:\\DOKTORAT\\generiraneOntologije\\SalesforceDataModel.owl";
    // list of retrieved data entities
    public static ArrayList<SObject[]> listOfEntityList = new ArrayList<SObject[]>();
    // list of object metadata
    public static ArrayList<DescribeSObjectResult> describeList = new ArrayList<DescribeSObjectResult>();

    public static ArrayList<DescribeSObjectResult> getDescribeList() {
        return describeList;
    }

    public static void setDescribeList(ArrayList<DescribeSObjectResult> describeList) {
        SalesforceDataOntologyCreator.describeList = describeList;
    }

    public static ArrayList<SObject[]> getListOfEntityList() {
        return listOfEntityList;
    }

    public static void setListOfEntityList(ArrayList<SObject[]> listOfEntityList) {
        SalesforceDataOntologyCreator.listOfEntityList = listOfEntityList;
    }

    // get data types 
    public static List<String> getDataTypesInCurrentData() {

        List<String> result = new ArrayList();

        if (listOfEntityList != null && listOfEntityList.size() > 0 && describeList != null && describeList.size() > 0) {
             System.out.println("DEBUG: List of entities list size = " + listOfEntityList.size());

            // create ontology using Jena

            int brojac = 0;
            for (SObject[] objectArray : listOfEntityList) {



                for (int i = 0; i < objectArray.length; i++) {
                    SObject objekt = objectArray[i];


                    // System.out.println("DEBUG: Naziv klase objekta " + objekt.getClass());

                    DescribeSObjectResult describe = describeList.get(brojac);

                    String nazivKlase = objekt.getClass().toString();
                    String className = nazivKlase.substring(nazivKlase.lastIndexOf(".") + 1, nazivKlase.indexOf("__c"));

                    if (i == 0) {

                        // Get the fields
                        Field[] fields = describe.getFields();

                        // Iterate through each field and gets its properties
                        for (int j = 0; j < fields.length; j++) {
                            Field field = fields[j];
                            //  System.out.println("Field name: " + field.getName());
                            String fieldType = field.getType().toString();
                            System.out.println("Field type: " + fieldType);
                            
                            if (fieldType.contains("_")){
                                fieldType = fieldType.replace("_", "");
                            }

                            // create properties of the class using key values

                            String fieldname = field.getName();
                            // uzimaju se samo custom fields i Id (primary key)
                            if (fieldname.contains("__c") || fieldname.compareTo("Id") == 0) {
                                if (result != null && !result.contains(fieldType)) {
                                    result.add(fieldType);
                                }
                            }



                        }
                    }
                }
            }
        }

        return result;

    }

    /*
     * Field types in SOAP API Developer's Guide - stranica 29
     ID - Primary key field for the object
     reference - Cross-references to a different object. Analogous to a foreign key field in SQL.
     */
    public static void create() {
        if (listOfEntityList != null && listOfEntityList.size() > 0 && describeList != null && describeList.size() > 0) {
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


            int brojac = 0;
            for (SObject[] objectArray : listOfEntityList) {



                for (int i = 0; i < objectArray.length; i++) {
                    SObject objekt = objectArray[i];


                    System.out.println("DEBUG: Naziv klase objekta " + objekt.getClass());

                    DescribeSObjectResult describe = describeList.get(brojac);

                    String nazivKlase = objekt.getClass().toString();
                    String className = nazivKlase.substring(nazivKlase.lastIndexOf(".") + 1, nazivKlase.indexOf("__c"));
                    
                    System.out.println("DEBUG: className" + className);

                    if (i == 0) {
                        // create new OWL class

                        // System.out.println("DEBUG: Naziv klase ==> " + className);
                        ontologyClass = model.createClass(baseURI + className);



                        // Get the fields
                        Field[] fields = describe.getFields();
                        System.out.println("Has " + fields.length + " fields");
                        // Iterate through each field and gets its properties
                        for (int j = 0; j < fields.length; j++) {
                            Field field = fields[j];
                            //  System.out.println("Field name: " + field.getName());
                            System.out.println("Field type: " + field.getType().toString());

                            // create properties of the class using key values

                            String fieldname = field.getName();
                            if (fieldname.contains("__c")) {
                                fieldname = fieldname.substring(0, fieldname.indexOf("__c"));

                                DatatypeProperty dataProperty = model.createDatatypeProperty(baseURI + fieldname);
                                dataProperty.setDomain(ontologyClass);

                                Resource resurs = DataTypeConverter.convertToOwlDataPropertyRange(TypeConstants.SALESFORCE, field.getType().toString());

                                dataProperty.setRange(resurs);
                            }



                        }
                    }

                    // for all elements, I need to create OWL individuals
                    // I need to parse object string representations and find property values.

                    // for all elements, I need to create OWL individuals
                    if (ontologyClass != null) {

                        int num = i + 1;
                        // I need to parse object string representation

                        String objectString = objekt.toString();
                        System.out.println("DEBUG: Object to string " + objectString);

                        // Get the fields
                        Field[] fields = describe.getFields();
                        //System.out.println("Has " + fields.length + " fields");
                        // Iterate through each field and gets its properties
                        Individual individual = null;
                        for (int j = 0; j < fields.length; j++) {
                            Field field = fields[j];

                            String fieldname = field.getName();
                            String fieldType = field.getType().toString();
                            String fieldValue = objectString.substring(objectString.indexOf(fieldname));
                            
                            System.out.println("DEBUG fieldname " + fieldname);

                            // add identifier to the ontology
                            if (fieldname.compareTo("Id") == 0) {

                               
                                System.out.println("DEBUG identifier full field value = " + fieldValue);
                                fieldValue = fieldValue.substring(fieldValue.indexOf(fieldname + "=\'"), fieldValue.indexOf("\'\n"));
                                fieldValue = fieldValue.substring(fieldValue.indexOf("\'") + 1);
                               System.out.println("DEBUG identifierValue = " + fieldValue);

                                individual = model.createIndividual(baseURI + className + "_" + fieldValue, ontologyClass);

                                if (dataKeyProperty != null ) {

                                    individual.addProperty(dataKeyProperty, fieldValue, XSDDatatype.XSDstring);

                                }

                            }
                            
                         
                           if (fieldname.contains("__c")) {


                                fieldValue = fieldValue.substring(fieldValue.indexOf(fieldname + "=\'"), fieldValue.indexOf("\'\n"));
                                fieldValue = fieldValue.substring(fieldValue.indexOf("\'") + 1);
                                System.out.println("DEBUG fieldValue = " + fieldValue);


                                fieldname = fieldname.substring(0, fieldname.indexOf("__c"));
                                System.out.println("DEBUG: fieldname == " + fieldname);


                                DatatypeProperty dataProperty = model.getDatatypeProperty(baseURI + fieldname);
                                // TODO: implement mapper for data type


                                RDFDatatype datatype = DataTypeConverter.convertToOwlDataTypesForIndividual(TypeConstants.SALESFORCE, field.getType().toString());

                                individual.addProperty(dataProperty, fieldValue, datatype);

                            }



                        }

                    }

                }

                brojac++;




            }

            // add instances for hasLinkObject properties - for foreign key relationship
            brojac = 0;
            for (SObject[] objectArray : listOfEntityList) {



                for (int i = 0; i < objectArray.length; i++) {
                    SObject objekt = objectArray[i];
                    DescribeSObjectResult describe = describeList.get(brojac);

                    String objectString = objekt.toString();

                    Field[] fields = describe.getFields();
                    System.out.println("Has " + fields.length + " fields");
                    // Iterate through each field and gets its properties
                    for (int j = 0; j < fields.length; j++) {
                        Field field = fields[j];

                        String fieldName = field.getName();
                        String fieldType = field.getType().toString();

                        if (fieldName.contains("__c") && fieldType.compareTo(Constants.SALESFORCE_REFERENCE_DATATYPE) == 0) {
                            System.out.println("DEBUG: Reference is found in " + fieldName);

                            String master = objectString.substring(objectString.indexOf("[")+1, objectString.indexOf("__c"));
                            System.out.println("DEBUG master = " + master);

                            String child = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.indexOf("Id"));
                            System.out.println("DEBUG child = " + child);

                            String foreignKeyColumn = fieldName.substring(0, fieldName.indexOf("__c"));
                            System.out.println("DEBUG foreignKeyColumn = " + foreignKeyColumn);



                            if (model.getOntClass(baseURI + master) != null) {

                                ExtendedIterator<Individual> individuals = model.listIndividuals(model.getOntClass(baseURI + master));

                                if (individuals != null) {

                                    while (individuals.hasNext()) {
                                        Individual individualMaster = individuals.next();

                                        OntProperty foreignKeyProperty = model.getOntProperty(baseURI + foreignKeyColumn);

                                        if (foreignKeyProperty != null) {
                                            RDFNode iValue = individualMaster.getPropertyValue(foreignKeyProperty);

                                            if (iValue != null) {
                                                String foreignId = iValue.toString();

                                                if (foreignId != null) {
                                                    String childIndividualString = child + "_" + foreignId;
                                                    System.out.println("DEBUG childIndividualString = " + childIndividualString);
                                                    childIndividualString = childIndividualString.substring(0, childIndividualString.indexOf("^^"));

                                                    Individual childIndividual = model.getIndividual(baseURI + childIndividualString);
                                                    System.out.println("DEBUG childIndividual = " + childIndividual);

                                                    if (childIndividual != null) {
                                                        individualMaster.addProperty(objectRelationshipProperty, childIndividual);
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                            }

                        }


                    }


                }
                brojac++;

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
}
