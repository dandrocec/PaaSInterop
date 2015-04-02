/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.jena;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import java.sql.*;
import com.microsoft.sqlserver.jdbc.*;
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
import com.sun.rowset.CachedRowSetImpl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Darko Androcec
 */
public class AzureDataOntologyCreator {

    public static final String ONTOLOGY_NAME = "AzureDataModel#";
    public static final String ONTOLOGY_DISKFILE = "D:\\DOKTORAT\\generiraneOntologije\\AzureDataModel.owl";
    // list of retrieved resultsets
    public static ArrayList<CachedRowSet> listOfEntities = new ArrayList<CachedRowSet>();
    public static ArrayList<String> listOfTableName = new ArrayList<String>();

    public static ArrayList<String> getListOfTableName() {
        return listOfTableName;
    }

    public static void setListOfTableName(ArrayList<String> listOfTableName) {
        AzureDataOntologyCreator.listOfTableName = listOfTableName;
    }

    public static ArrayList<CachedRowSet> getListOfEntities() {
        return listOfEntities;
    }

    public static void setListOfEntities(ArrayList<CachedRowSet> listOfEntities) {
        AzureDataOntologyCreator.listOfEntities = listOfEntities;
    }

    public static void create(List<String> foundKeysResult) {
        if (listOfEntities != null && listOfEntities.size() > 0) {
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

            System.out.println("DEBUG found keys results " + foundKeysResult);

            int brojac = 0;

            System.out.println("DEBUG listofEntities size " + listOfEntities.size());

            for (CachedRowSet resultSet : listOfEntities) {
                
                
                try {

                    resultSet.beforeFirst();
                } catch (SQLException ex) {
                    Logger.getLogger(AzureDataOntologyCreator.class.getName()).log(Level.SEVERE, null, ex);
                }
                

                // get table name and create new OWL class

                System.out.println("DEBUG resultSet == " + resultSet);

                String tableName = getListOfTableName().get(brojac);
                System.out.println("DEBUG: Table name == " + tableName);
                // create new OWL class
                ontologyClass = model.createClass(baseURI + tableName);

                try {

                    System.out.println("DEBUG index of identifier value =" + resultSet.findColumn("identifier"));
                } catch (SQLException ex) {
                    Logger.getLogger(AzureDataOntologyCreator.class.getName()).log(Level.SEVERE, null, ex);
                }



                //     System.out.println("DEBUG result set command = " + resultSet.getCommand());

                //     System.out.println("DEBUG result get page size = " + resultSet.getPageSize());


                //      System.out.println("DEBUG result set size = " +   resultSet.size());



                try {
                    // Loop through the results
                    int individul_num = 1;

                    // TODO tu je greška nema ništa u result setu
                    System.out.println("DEBUG isAfterLast " + resultSet.isAfterLast());
                    System.out.println("DEBUG isBeforeFirst " + resultSet.isBeforeFirst());
                    
                    System.out.println("DEBUG isFirst " + resultSet.isFirst());
                    System.out.println("DEBUG isLast " + resultSet.isLast());

                    while (resultSet.next()) {

                        System.out.println("DEBUG ima nešto u result setu");

                        String primaryKeyColumn = "";
                        System.out.println("DEBUG found keys:");
                        for (String foundKey : foundKeysResult) {
                            System.out.println(foundKey);

                            // primary keys
                            if (foundKey.contains("|PrimaryKeyTableName")) {
                                System.out.println("Pronađen je primarni ključ!");

                                String primaryKeyTable = foundKey.substring(foundKey.indexOf("|PrimaryKeyTableName=") + 21, foundKey.indexOf("|PrimaryKeyColumnName"));

                                System.out.println("DEBUG primaryKeyTable " + primaryKeyTable);

                                if (tableName.compareTo(primaryKeyTable) == 0) {
                                    primaryKeyColumn = foundKey.substring(foundKey.indexOf("|PrimaryKeyColumnName") + 22, foundKey.lastIndexOf("|"));
                                    System.out.println("Debug: Primarni ključ je: " + primaryKeyColumn);
                                }


                            }


                        }


                        Individual individual = null;

                        System.out.println("DEBUG individual = " + baseURI + tableName + "_" + resultSet.getString(primaryKeyColumn));

                        if (ontologyClass != null) {
                            individual = model.createIndividual(baseURI + tableName + "_" + resultSet.getString(primaryKeyColumn), ontologyClass);
                        }

                        if (dataKeyProperty != null) {

                            individual.addProperty(dataKeyProperty, resultSet.getString(primaryKeyColumn), XSDDatatype.XSDstring);

                        }

                        ResultSetMetaData metadata = resultSet.getMetaData();

                        int columnNumber = metadata.getColumnCount();

                        System.out.println("DEBUG: Column number = " + columnNumber);

                        if (columnNumber > 0) {



                            for (int i = 1; i <= columnNumber; i++) {

                                String columnName = metadata.getColumnName(i);
                                String columnType = metadata.getColumnTypeName(i);
                                String columnValue = resultSet.getString(i);

                                DatatypeProperty existingDataProperty = model.getDatatypeProperty(baseURI + columnName);

                                if (existingDataProperty == null) {
                                    DatatypeProperty dataProperty = model.createDatatypeProperty(baseURI + columnName);
                                    dataProperty.setDomain(ontologyClass);

                                    Resource resurs = DataTypeConverter.convertToOwlDataPropertyRange(TypeConstants.AZURE, columnType);

                                    dataProperty.setRange(resurs);


                                }

                                System.out.println("COLUMN NAME: "
                                        + columnName);
                                System.out.println("COLUMN TYPE: "
                                        + columnType);
                                System.out.println("VALUE: "
                                        + columnValue);


                                // for all rows, I need to create OWL individuals
                                if (ontologyClass != null && individual != null && columnValue != null) {



                                    DatatypeProperty dataProperty = model.getDatatypeProperty(baseURI + columnName);

                                    if (dataProperty != null) {
                                        RDFDatatype datatype = DataTypeConverter.convertToOwlDataTypesForIndividual(TypeConstants.AZURE, columnType);

                                        individual.addProperty(dataProperty, columnValue, datatype);

                                    }



                                }

                            }
                        }

                        individul_num++;

                    }

                    // add instances for hasLinkObject properties - for foreign key relationship

                    for (String foundKey : foundKeysResult) {

                        if (foundKey.contains("|FK_Colum")) {
                            System.out.println("Pronađen je vanjski ključ!");
                            System.out.println("DEBUG complete foreignkey = " + foundKey);

                            String master = foundKey.substring(foundKey.indexOf("K_Table=") + 8, foundKey.indexOf("|FK_Column"));
                            System.out.println("DEBUG master = " + master);

                            String child = foundKey.substring(foundKey.indexOf("|PK_Table=") + 10, foundKey.indexOf("|PK_Column"));
                            System.out.println("DEBUG child = " + child);

                            String foreignKeyColumn = foundKey.substring(foundKey.indexOf("|FK_Column=") + 11, foundKey.indexOf("|PK_Table="));
                            System.out.println("DEBUG foreignKeyColumn = " + foreignKeyColumn);

                            System.out.println("DEBUG model");

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


                } catch (SQLException ex) {
                    Logger.getLogger(AzureDataOntologyCreator.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    /*
                    if (null != resultSet) {
                        try {
                            resultSet.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(AzureDataOntologyCreator.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    */
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

    // Get all data types from current Azure SQL storage
    public static List<String> getDataTypesInCurrentData() {

        System.out.println("DEBUG: getDataTypesInCurrentData()");

        List<String> result = new ArrayList();

        if (listOfEntities != null && listOfEntities.size() > 0) {

            int brojac = 0;


            for (CachedRowSet resultSet : listOfEntities) {
                
                
                 try {

                    resultSet.beforeFirst();
                } catch (SQLException ex) {
                    Logger.getLogger(AzureDataOntologyCreator.class.getName()).log(Level.SEVERE, null, ex);
                }
                

                // get table name and create new OWL class

                String tableName = getListOfTableName().get(brojac);
                System.out.println("DEBUG: Table name == " + tableName);


                try {
                    // Loop through the results
                    int individul_num = 1;

                    while (resultSet.next()) {

                        ResultSetMetaData metadata = resultSet.getMetaData();

                        int columnNumber = metadata.getColumnCount();

                        System.out.println("DEBUG: Column number = " + columnNumber);

                        if (columnNumber > 0) {

                            for (int i = 1; i <= columnNumber; i++) {

                                String columnName = metadata.getColumnName(i);
                                String columnType = metadata.getColumnTypeName(i);
                                String columnValue = resultSet.getString(i);


                                System.out.println("COLUMN NAME: "
                                        + columnName);
                                System.out.println("COLUMN TYPE: "
                                        + columnType);
                                System.out.println("VALUE: "
                                        + columnValue);

                                if (result != null && !result.contains(columnType)) {
                                    result.add(columnType);
                                }


                            }
                        }

                        individul_num++;

                    }


                } catch (SQLException ex) {
                    Logger.getLogger(AzureDataOntologyCreator.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    /*
                    if (null != resultSet) {
                        try {
                            resultSet.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(AzureDataOntologyCreator.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    */
                }

                brojac++;
            }


        }

        return result;
    }
}
