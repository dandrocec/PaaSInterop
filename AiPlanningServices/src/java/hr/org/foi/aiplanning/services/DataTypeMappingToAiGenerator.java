/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.aiplanning.services;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 *
 * @author Darko Androcec
 */
public class DataTypeMappingToAiGenerator {

    private static String DATA_TYPE_MAPPER_ONTOLOGY_DISKFILE = "D:\\DOKTORAT\\PaaSOntologijav4\\PaaSOntologyv4.owl";

    public static String getAllDataTypeMappings() {

        String result = "";

        String baseURI = "http://localhost:8080/PaaSOntologyv4.owl#";
        OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        ontologyModel.read("file:" + DATA_TYPE_MAPPER_ONTOLOGY_DISKFILE, "RDF/XML-ABBREV");

        ExtendedIterator<Individual> individuals = ontologyModel.listIndividuals(ontologyModel.getOntClass(baseURI + "DataTypeMapper"));


        while (individuals.hasNext()) {
            Individual individual = individuals.next();

            OntProperty hasSourceProperty = ontologyModel.getOntProperty(baseURI + "hasSource");
            OntProperty hasDestinationProperty = ontologyModel.getOntProperty(baseURI + "hasDestination");

            RDFNode iValue = individual.getPropertyValue(hasSourceProperty);
            String source = iValue.toString();
            source = source.substring(source.indexOf("#") + 1);
            source = source.toLowerCase();
            
           
            String aiItem = "(dataTypeMappingExists ";

           // System.out.println("DEBUG: get hasSourceProperty value" + source);
            
            aiItem = aiItem + source + " ";
            RDFNode jValue = individual.getPropertyValue(hasDestinationProperty);
            String destination = jValue.toString();
            destination = destination.substring(destination.indexOf("#") + 1);
            destination = destination.toLowerCase();
            aiItem = aiItem + destination + ")\n";
           // System.out.println("DEBUG: get hasDestinationProperty value" + destination);
            
            System.out.println("DEBUG AI item = " + aiItem);
            
            result = result + aiItem;
            

        }

        ontologyModel.close();
        return result;

    }
}
