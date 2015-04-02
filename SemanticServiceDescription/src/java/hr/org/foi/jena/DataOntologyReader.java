/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.jena;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Darko Androcec
 */
public class DataOntologyReader {

    public static List<TableUtil> tables = new ArrayList();

    public static List<TableUtil> getTables() {
        return tables;
    }

    public static void setTables(List<TableUtil> tables) {
        DataOntologyReader.tables = tables;
    }

    public static void readOntology(String pathToOntology) {
        if (pathToOntology != null && pathToOntology.length() > 0) {

            OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
            ontologyModel.read("file:" + pathToOntology, "RDF/XML-ABBREV");

            ExtendedIterator<OntClass> classes = ontologyModel.listClasses();

            String baseURI = "http://hr.org.foi.ontology/" + pathToOntology.substring(pathToOntology.lastIndexOf("\\") + 1, pathToOntology.lastIndexOf(".owl")) + "#";

            System.out.println("DEBUG baseURI " + baseURI);
            
            List<TableUtil> tablesWithReferences = new ArrayList();

            tables.clear();

            int numberOfClasses = 0;

            while (classes.hasNext()) {

                numberOfClasses++;
                OntClass klasa = classes.next();
                System.out.println("Found class " + klasa.toString());


                if (klasa != null) {

                    String className = klasa.getLocalName();

                    if (className != null) {
                        TableUtil table = new TableUtil();

                        table.setName(className);

                        // LIST AND STORE ALL INDIVIDUALS INTO CHOSEN CLOUD DATASTORE
                        ExtendedIterator instances = klasa.listInstances();

                        while (instances.hasNext()) {

                            ArrayList<String> rowColumns = new ArrayList();

                            Individual thisInstance = (Individual) instances.next();

                            System.out.println("DEBUG IMPORTANT INDIVIDUAL = " + thisInstance.toString());

                            ExtendedIterator<OntProperty> propertiesForIndividual = klasa.listDeclaredProperties();
                 
                            while (propertiesForIndividual.hasNext()) {

                                OntProperty property = propertiesForIndividual.next();
                                
                                System.out.println("DEBUG property = " + property.getLocalName());
                                RDFNode iValue = thisInstance.getPropertyValue(property);
                                
                                System.out.println("DEBUG iValue = " + iValue);

                                String propertyName = property.getLocalName();

                                if (!propertyName.contains(TypeConstants.ONTOLOGY_IDENTIFIER_PROPERTY_NAME)
                                        && !propertyName.contains(TypeConstants.ONTOLOGY_REFERENCE_LINK_PROPERTY_NAME)) {

                                    if (iValue != null) {
                                        // Get type in data type mapper ontology format
                                        String fullProperty = iValue.toString();
                                        System.out.println("DEBUG IMPORTANT fullProperty: " + fullProperty);

                                        if (fullProperty.contains("^^")) {
                                            String propertyValue = fullProperty.substring(0, fullProperty.indexOf("^^"));
                                            System.out.println("DEBUG IMPORTANT property value: " + propertyValue);

                                            rowColumns.add(propertyValue);
                                        }
                                        else{
                                            // ako ne sadržava ^^ znači da nema tip uz sebe
                                            rowColumns.add(fullProperty);
                                        }
                                    }
                                    else{
                                         rowColumns.add(null);
                                    }
                                } 
                                
                                //else if (propertyName.contains(TypeConstants.ONTOLOGY_IDENTIFIER_PROPERTY_NAME)) {
                                
                               // promjena kod Vosao migracije
                                else if (propertyName.compareTo(TypeConstants.ONTOLOGY_IDENTIFIER_PROPERTY_NAME)==0){
                                    if (iValue != null) {
                                        // Get type in data type mapper ontology format
                                        String identifierValue = iValue.toString();

                                        if (identifierValue.contains("^^")) {
                                            identifierValue = identifierValue.substring(0, identifierValue.indexOf("^^"));
                                            System.out.println("DEBUG identifier property values: " + identifierValue);
                                            table.getIdentifiersValues().add(identifierValue);
                                        }


                                    }
                                }
                            }

                            table.getRows().add(rowColumns);
                        }


                        // System.out.println("DEBUG: Class name == " + className);

                        ExtendedIterator<OntProperty> properties = klasa.listDeclaredProperties();

                        while (properties.hasNext()) {
                            OntProperty property = properties.next();
                            String propertyName = property.getLocalName();
                            
                            System.out.println("Debug property name before putting it into properties = " + propertyName);

                            // identifiers
                            if (propertyName.contains(TypeConstants.ONTOLOGY_IDENTIFIER_PROPERTY_NAME)) {
                                System.out.println("DEBUG identifier is found");

                                table.setIdentifier(propertyName);


                            } // reference links to other data objects
                            else if (propertyName.contains(TypeConstants.ONTOLOGY_REFERENCE_LINK_PROPERTY_NAME)) {
                                System.out.println("DEBUG reference link to other object has been found");

                                ExtendedIterator<? extends OntResource> it = klasa.listInstances(true);
                                //    for (ExtendedIterator<? extends OntResource> it = klasa.listInstances(true); it.hasNext();) {

                                // I put this information (property data type) only for first individual of the class



                                if (it.hasNext()) {

                                    Individual ins = (Individual) it.next();


                                    RDFNode iValue = ins.getPropertyValue(property);

                                    // Get type in data type mapper ontology format
                                    if (iValue != null) {
                                        String linkToObjectString = iValue.toString();


                                        System.out.println("DEBUG child individual URL = " + linkToObjectString);
                                        Individual childIndividual = ontologyModel.getIndividual(linkToObjectString);
                                        System.out.println("DEBUG childIndividual " + childIndividual);
                                        Property dataKeyProperty = ontologyModel.getProperty("identifier");
                                        System.out.println("DEBUG dataKeyProperty " + dataKeyProperty);

                                        RDFNode identifierValue = null;
                                        if (childIndividual != null && dataKeyProperty != null) {
                                            if (childIndividual.getPropertyValue(dataKeyProperty) != null) {
                                                identifierValue = childIndividual.getPropertyValue(dataKeyProperty);


                                            }
                                        }


                                        table.getListOfLinksBetweenDataObjects().add(className + "(" + linkToObjectString + ")" + "||" + identifierValue);

                                    }
                                }




                            } // data property
                            else {
                                table.getProperties().add(propertyName);

                                ExtendedIterator<? extends OntResource> it = klasa.listInstances(true);
                                //    for (ExtendedIterator<? extends OntResource> it = klasa.listInstances(true); it.hasNext();) {

                                // I put this information (property data type) only for first individual of the class



                                if (it.hasNext()) {

                                    Individual ins = (Individual) it.next();


                                    RDFNode iValue = ins.getPropertyValue(property);

                                    // Get type in data type mapper ontology format
                                    if (iValue != null) {
                                        String fullProperty = iValue.toString();
                                        // System.out.println("DEBUG IMPORTANT: " + fullProperty);

                                        String propertyType = fullProperty.substring(fullProperty.indexOf("#") + 1);
                                        propertyType = "Xsd" + propertyType.substring(0, 1).toUpperCase() + propertyType.substring(1);

                                        table.getPropertyTypes().add(propertyType);
                                    }
                                    else{
                                        table.getPropertyTypes().add("XsdString");
                                    }
                                }
                                //   }
                            }
                            //    System.out.println("DEBUG: Property name == " + propertyName);





                        }


                        System.out.println("DEBUG Table ==> " + table.toString());
                        
                        // if table contains links to other table, they will be added later to a main list
                        // TODO - the problem can occur if refence is to the table that also has references
                        if (table.getListOfLinksBetweenDataObjects().size()>0){
                            tablesWithReferences.add(table);
                        }
                        else{
                            tables.add(table);
                        }
                        
                        
                        



                    }



                }
            }
            
            // on the end add tables with references
            tables.addAll(tablesWithReferences);

            System.out.println("DEBUG classes in model size ==> " + numberOfClasses);


        }
    }
}
