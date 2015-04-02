/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.jena;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.XSD;
import com.sforce.soap.metadata.FieldType;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Darko Androcec a class to convert from one data type to another
 */
public class DataTypeConverter {

    public static final String DATA_TYPE_MAPPER_ONTOLOGY_DISKFILE = "D:\\DOKTORAT\\mojeOntologijeZaDoktorat\\DataTypeMapperOntology.owl";

    // convert from Google App Engine/Java dataType to Java object for property value
    public static Object convertIntoGoogleAppEngineTypes(String datatype, String value) {

        try {
            if (datatype.compareTo("java.lang.String") == 0) {
                return value;
            } else if (datatype.compareTo("java.util.Date") == 0) {
                DateFormat format =
                        DateFormat.getDateTimeInstance(
                        DateFormat.MEDIUM, DateFormat.SHORT);
                Date date = null;

                try {
                    date = format.parse(value);
                } catch (ParseException ex) {
                    Logger.getLogger(DataTypeConverter.class.getName()).log(Level.SEVERE, null, ex);
                }
                return date;
            } else if (datatype.compareTo("java.lang.Boolean") == 0) {
                return new Boolean(value);
            } else if (datatype.compareTo("java.lang.Double") == 0) {
                return new Double(value);
            } else if (datatype.compareTo("java.lang.Integer") == 0) {
                return new Integer(value);
            } else if (datatype.compareTo("java.lang.Short") == 0) {
                return new Short(value);
            } else if (datatype.compareTo("java.lang.Long") == 0) {
                return new Long(value);
            } else if (datatype.compareTo("com.google.appengine.api.datastore.Blob") == 0) {
                return new com.google.appengine.api.datastore.Blob(value.getBytes());
            }


        } catch (Exception ex) {
            System.out.println("Exception occured! " + ex.toString());
        }

        return value;
    }
    
      public static Object convertIntoJavaObjectTypes(String datatype, String value) {

        try {
            if (datatype.compareTo("XsdBoolean") == 0) {
                return new Boolean(value);
            } else if (datatype.compareTo("XsdDouble") == 0) {
                return new Double(value);
            } else if (datatype.compareTo("XsdInteger") == 0) {
                return new Integer(value);
            } else if (datatype.compareTo("XsdShort") == 0) {
                return new Short(value);
            } 
            else{
                // return string value
                return value;
            }


        } catch (Exception ex) {
            System.out.println("Exception occured! " + ex.toString());
        }

        return value;
    }

    // convert from Salesforce dataType to SalesforceCustomFieldsTypes used in creation of custom objects and custom fields
    public static FieldType convertIntoSalesforceCustomFieldsTypes(String datatype) {
        FieldType type = com.sforce.soap.metadata.FieldType.Text;

        
        if (datatype.compareTo("String") == 0) {
            return com.sforce.soap.metadata.FieldType.Text;
        } else if (datatype.compareTo("Boolean") == 0) {
            return com.sforce.soap.metadata.FieldType.Checkbox;
        } else if (datatype.compareTo("Double") == 0 || datatype.compareTo("Int") == 0 || datatype.compareTo("Byte") == 0) {
            return com.sforce.soap.metadata.FieldType.Number;
        } else if (datatype.compareTo("DateTime") == 0) {
            return com.sforce.soap.metadata.FieldType.DateTime;
        } else if (datatype.compareTo("Date") == 0) {
            return com.sforce.soap.metadata.FieldType.Date;
        } else if (datatype.compareTo("Base64") == 0) {
            return com.sforce.soap.metadata.FieldType.File;
        }
        

        return type;

    }

    // CONVERT FROM OWL DATA TYPE TO DATA TYPE OF OTHER CLOUD PROVIDER
    public static String convertFromOwlDataType(String to, String datatype) {
        String res = "";

        if (to != null && datatype != null) {
            String baseURI = "http://www.org.foi.hr/ontologies/2012/11/DataTypeMapperOntology.owl#";
            OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
            ontologyModel.read("file:" + DATA_TYPE_MAPPER_ONTOLOGY_DISKFILE, "RDF/XML-ABBREV");

            ExtendedIterator<Individual> individuals = ontologyModel.listIndividuals(ontologyModel.getOntClass(baseURI + "DataTypeMapper"));

            int size = 0;
            String fromType = datatype;
            System.out.println("DEBUG: from type " + fromType);

            while (individuals.hasNext()) {
                Individual individual = individuals.next();

                OntProperty hasSourceProperty = ontologyModel.getOntProperty(baseURI + "hasSource");
                OntProperty hasDestinationProperty = ontologyModel.getOntProperty(baseURI + "hasDestination");

                RDFNode iValue = individual.getPropertyValue(hasSourceProperty);
                String source = iValue.toString();
                source = source.substring(source.indexOf("#") + 1);


                if (fromType.compareTo(source) == 0) {

                    System.out.println("DEBUG: get hasSourceProperty value" + source);
                    RDFNode jValue = individual.getPropertyValue(hasDestinationProperty);
                    String destination = jValue.toString();
                    destination = destination.substring(destination.indexOf("#") + 1);
                    System.out.println("DEBUG: get hasDestinationProperty value" + destination);


                    System.out.println("DEBUG: to = " + to);
                    if (destination.contains(to)) {
                        // remove Cloud name from result
                        String typeValue = destination.replace(to, "");
                        res = typeValue;
                        return res;
                    }

                }

                size++;
            }
            // System.out.println("DEBUG: Data mapper individuals size = " + size);

            ontologyModel.close();


        }

        return res;
    }

    // CONVERT TO OWL DATA TYPE
    public static Resource convertToOwlDataPropertyRange(String from, String datatype) {


        if (from != null && datatype != null) {
            String baseURI = "http://www.org.foi.hr/ontologies/2012/11/DataTypeMapperOntology.owl#";
            OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
            ontologyModel.read("file:" + DATA_TYPE_MAPPER_ONTOLOGY_DISKFILE, "RDF/XML-ABBREV");

            ExtendedIterator<Individual> individuals = ontologyModel.listIndividuals(ontologyModel.getOntClass(baseURI + "DataTypeMapper"));

            int size = 0;

            String fromType = "";

            if (from.compareTo(TypeConstants.GOOGLE_APP_ENGINE) == 0) {
                fromType = from + datatype;
            } else {
                fromType = from + datatype.substring(0, 1).toUpperCase() + datatype.substring(1);
            }


            System.out.println("DEBUG: from type " + fromType);

            while (individuals.hasNext()) {
                Individual individual = individuals.next();

                OntProperty hasSourceProperty = ontologyModel.getOntProperty(baseURI + "hasSource");
                OntProperty hasDestinationProperty = ontologyModel.getOntProperty(baseURI + "hasDestination");

                RDFNode iValue = individual.getPropertyValue(hasSourceProperty);
                String source = iValue.toString();
                source = source.substring(source.indexOf("#") + 1);


                if (fromType.compareTo(source) == 0) {

                    System.out.println("DEBUG: get hasSourceProperty value" + source);
                    RDFNode jValue = individual.getPropertyValue(hasDestinationProperty);
                    String destination = jValue.toString();
                    destination = destination.substring(destination.indexOf("#") + 1);
                    System.out.println("DEBUG: get hasDestinationProperty value" + destination);

                    // return appropriate type
                    if (destination.compareTo("XsdAnyURI") == 0) {
                        return XSD.anyURI;
                    } else if (destination.compareTo("XsdBase64Binary") == 0) {
                        return XSD.base64Binary;
                    } else if (destination.compareTo("XsdBoolean") == 0) {
                        return XSD.xboolean;
                    } else if (destination.compareTo("XsdByte") == 0) {
                        return XSD.xbyte;
                    } else if (destination.compareTo("XsdDate") == 0) {
                        return XSD.date;
                    } else if (destination.compareTo("XsdDatetime") == 0) {
                        return XSD.dateTime;
                    } else if (destination.compareTo("XsdDecimal") == 0) {
                        return XSD.decimal;
                    } else if (destination.compareTo("XsdDouble") == 0) {
                        return XSD.xdouble;
                    } else if (destination.compareTo("XsdFloat") == 0) {
                        return XSD.xfloat;
                    } else if (destination.compareTo("XsdHexBinary") == 0) {
                        return XSD.hexBinary;
                    } else if (destination.compareTo("XsdInt") == 0) {
                        return XSD.xint;
                    } else if (destination.compareTo("XsdInteger") == 0) {
                        return XSD.integer;
                    } else if (destination.compareTo("XsdLanguage") == 0) {
                        return XSD.language;
                    } else if (destination.compareTo("XsdLong") == 0) {
                        return XSD.xlong;
                    } else if (destination.compareTo("XsdName") == 0) {
                        return XSD.Name;
                    } else if (destination.compareTo("XsdNcName") == 0) {
                        return XSD.NCName;
                    } else if (destination.compareTo("XsdNegativeInteger") == 0) {
                        return XSD.negativeInteger;
                    } else if (destination.compareTo("XsdNmToken") == 0) {
                        return XSD.NMTOKEN;
                    } else if (destination.compareTo("XsdNonNegativeInteger") == 0) {
                        return XSD.nonNegativeInteger;
                    } else if (destination.compareTo("XsdNonPositiveInteger") == 0) {
                        return XSD.nonPositiveInteger;
                    } else if (destination.compareTo("XsdNormalizedString") == 0) {
                        return XSD.normalizedString;

                    } else if (destination.compareTo("XsdPositiveInteger") == 0) {
                        return XSD.positiveInteger;
                    } else if (destination.compareTo("XsdShort") == 0) {
                        return XSD.xshort;
                    } else if (destination.compareTo("XsdString") == 0) {
                        return XSD.xstring;
                    } else if (destination.compareTo("XsdTime") == 0) {
                        return XSD.time;
                    } else if (destination.compareTo("XsdToken") == 0) {
                        return XSD.token;
                    } else if (destination.compareTo("XsdUnsignedByte") == 0) {
                        return XSD.unsignedByte;
                    } else if (destination.compareTo("XsdUnsignedInt") == 0) {
                        return XSD.unsignedInt;
                    } else if (destination.compareTo("XsdUnsignedLong") == 0) {
                        return XSD.unsignedLong;
                    } else if (destination.compareTo("XsdUnsignedShort") == 0) {
                        return XSD.unsignedShort;
                    } else if (destination.compareTo("XsdgDay") == 0) {
                        return XSD.gDay;
                    } else if (destination.compareTo("XsdgMonth") == 0) {
                        return XSD.gMonth;
                    } else if (destination.compareTo("XsdgMonthDay") == 0) {
                        return XSD.gMonthDay;
                    } else if (destination.compareTo("XsdgYear") == 0) {
                        return XSD.gYear;
                    } else if (destination.compareTo("XsdgYearMonth") == 0) {
                        return XSD.gYearMonth;
                    }

                }

                size++;
            }
            // System.out.println("DEBUG: Data mapper individuals size = " + size);


            ontologyModel.close();


        }

        // if nothing, then return string on the end
        return XSD.xstring;
    }

    // CONVERT TO OWL DATA TYPE FOR INDIVIDUAL
    public static RDFDatatype convertToOwlDataTypesForIndividual(String from, String datatype) {


        if (from != null && datatype != null) {
            String baseURI = "http://www.org.foi.hr/ontologies/2012/11/DataTypeMapperOntology.owl#";
            OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
            ontologyModel.read("file:" + DATA_TYPE_MAPPER_ONTOLOGY_DISKFILE, "RDF/XML-ABBREV");

            ExtendedIterator<Individual> individuals = ontologyModel.listIndividuals(ontologyModel.getOntClass(baseURI + "DataTypeMapper"));

            int size = 0;

            String fromType = "";

            if (from.compareTo(TypeConstants.GOOGLE_APP_ENGINE) == 0) {
                fromType = from + datatype;
            } else {
                fromType = from + datatype.substring(0, 1).toUpperCase() + datatype.substring(1);
            }
            System.out.println("DEBUG: from type " + fromType);

            while (individuals.hasNext()) {
                Individual individual = individuals.next();

                OntProperty hasSourceProperty = ontologyModel.getOntProperty(baseURI + "hasSource");
                OntProperty hasDestinationProperty = ontologyModel.getOntProperty(baseURI + "hasDestination");

                RDFNode iValue = individual.getPropertyValue(hasSourceProperty);
                String source = iValue.toString();
                source = source.substring(source.indexOf("#") + 1);


                if (fromType.compareTo(source) == 0) {

                    System.out.println("DEBUG: get hasSourceProperty value" + source);
                    RDFNode jValue = individual.getPropertyValue(hasDestinationProperty);
                    String destination = jValue.toString();
                    destination = destination.substring(destination.indexOf("#") + 1);
                    System.out.println("DEBUG: get hasDestinationProperty value" + destination);

                    // return appropriate type
                    if (destination.compareTo("XsdAnyURI") == 0) {
                        return XSDDatatype.XSDanyURI;
                    } else if (destination.compareTo("XsdBase64Binary") == 0) {
                        return XSDDatatype.XSDbase64Binary;
                    } else if (destination.compareTo("XsdBoolean") == 0) {
                        return XSDDatatype.XSDboolean;
                    } else if (destination.compareTo("XsdByte") == 0) {
                        return XSDDatatype.XSDbyte;
                    } else if (destination.compareTo("XsdDate") == 0) {
                        return XSDDatatype.XSDdate;
                    } else if (destination.compareTo("XsdDatetime") == 0) {
                        return XSDDatatype.XSDdateTime;
                    } else if (destination.compareTo("XsdDecimal") == 0) {
                        return XSDDatatype.XSDdecimal;
                    } else if (destination.compareTo("XsdDouble") == 0) {
                        return XSDDatatype.XSDdouble;
                    } else if (destination.compareTo("XsdFloat") == 0) {
                        return XSDDatatype.XSDfloat;
                    } else if (destination.compareTo("XsdHexBinary") == 0) {
                        return XSDDatatype.XSDhexBinary;
                    } else if (destination.compareTo("XsdInt") == 0) {
                        return XSDDatatype.XSDint;
                    } else if (destination.compareTo("XsdInteger") == 0) {
                        return XSDDatatype.XSDinteger;
                    } else if (destination.compareTo("XsdLanguage") == 0) {
                        return XSDDatatype.XSDlanguage;
                    } else if (destination.compareTo("XsdLong") == 0) {
                        return XSDDatatype.XSDlong;
                    } else if (destination.compareTo("XsdName") == 0) {
                        return XSDDatatype.XSDNCName;
                    } else if (destination.compareTo("XsdNcName") == 0) {
                        return XSDDatatype.XSDNCName;
                    } else if (destination.compareTo("XsdNegativeInteger") == 0) {
                        return XSDDatatype.XSDnegativeInteger;
                    } else if (destination.compareTo("XsdNmToken") == 0) {
                        return XSDDatatype.XSDNMTOKEN;
                    } else if (destination.compareTo("XsdNonNegativeInteger") == 0) {
                        return XSDDatatype.XSDnonNegativeInteger;
                    } else if (destination.compareTo("XsdNonPositiveInteger") == 0) {
                        return XSDDatatype.XSDnonPositiveInteger;
                    } else if (destination.compareTo("XsdNormalizedString") == 0) {
                        return XSDDatatype.XSDnormalizedString;

                    } else if (destination.compareTo("XsdPositiveInteger") == 0) {
                        return XSDDatatype.XSDpositiveInteger;
                    } else if (destination.compareTo("XsdShort") == 0) {
                        return XSDDatatype.XSDshort;
                    } else if (destination.compareTo("XsdString") == 0) {
                        return XSDDatatype.XSDstring;
                    } else if (destination.compareTo("XsdTime") == 0) {
                        return XSDDatatype.XSDtime;
                    } else if (destination.compareTo("XsdToken") == 0) {
                        return XSDDatatype.XSDtoken;
                    } else if (destination.compareTo("XsdUnsignedByte") == 0) {
                        return XSDDatatype.XSDunsignedByte;
                    } else if (destination.compareTo("XsdUnsignedInt") == 0) {
                        return XSDDatatype.XSDunsignedInt;
                    } else if (destination.compareTo("XsdUnsignedLong") == 0) {
                        return XSDDatatype.XSDunsignedLong;
                    } else if (destination.compareTo("XsdUnsignedShort") == 0) {
                        return XSDDatatype.XSDunsignedShort;
                    } else if (destination.compareTo("XsdgDay") == 0) {
                        return XSDDatatype.XSDgDay;
                    } else if (destination.compareTo("XsdgMonth") == 0) {
                        return XSDDatatype.XSDgMonth;
                    } else if (destination.compareTo("XsdgMonthDay") == 0) {
                        return XSDDatatype.XSDgMonthDay;
                    } else if (destination.compareTo("XsdgYear") == 0) {
                        return XSDDatatype.XSDgYear;
                    } else if (destination.compareTo("XsdgYearMonth") == 0) {
                        return XSDDatatype.XSDgYearMonth;
                    }

                }

                size++;
            }
            // System.out.println("DEBUG: Data mapper individuals size = " + size);


            ontologyModel.close();


        }

        // if nothing, then return string on the end
        return XSDDatatype.XSDstring;
    }
}
