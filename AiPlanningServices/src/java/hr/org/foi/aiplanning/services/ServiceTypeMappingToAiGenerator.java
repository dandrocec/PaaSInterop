/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.aiplanning.services;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.util.Iterator;

/**
 *
 * @author Darko Androcec
 */
public class ServiceTypeMappingToAiGenerator {

    private static String DATA_TYPE_MAPPER_ONTOLOGY_DISKFILE = "D:\\DOKTORAT\\PaaSOntologijav4\\PaaSOntologyv4.owl";

    public static String getAllServiceTypeMappings() {

        String result = "";

        String baseURI = "http://localhost:8080/PaaSOntologyv4.owl#";
        OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF, null);
        ontologyModel.read("file:" + DATA_TYPE_MAPPER_ONTOLOGY_DISKFILE, "RDF/XML-ABBREV");


        // for simple mappings

        ExtendedIterator<Individual> individuals = ontologyModel.listIndividuals(ontologyModel.getOntClass(baseURI + "ServiceDataTypeMapper"));


        while (individuals.hasNext()) {
            Individual individual = individuals.next();

            OntProperty fromOntElement = ontologyModel.getOntProperty(baseURI + "fromOntElement");
            OntProperty toOntElement = ontologyModel.getOntProperty(baseURI + "toOntElement");
            OntProperty ontElementName = ontologyModel.getOntProperty(baseURI + "ontElementName");

            RDFNode iValue = individual.getPropertyValue(fromOntElement);



            String source = iValue.toString();
            source = source.substring(source.indexOf("#") + 1);
            // source = source.toLowerCase();

            Individual fromIndividual = ontologyModel.getIndividual(baseURI + source);
            //  System.out.println("DEBUG fromIndividual = " +fromIndividual );
            RDFNode fromElementNameNode = fromIndividual.getPropertyValue(ontElementName);
            //    System.out.println("DEBUG fromElementNameNode = " +fromElementNameNode );
            String fromString = fromElementNameNode.toString();
            fromString = fromString.substring(0, fromString.indexOf("^"));
            // fromString = fromString.toLowerCase();

            // System.out.println("DEBUG fromString " + fromString);


            String aiItem = "(hasServiceIOMapping ";
            aiItem = aiItem + fromString + " ";

            RDFNode jValue = individual.getPropertyValue(toOntElement);
            String destination = jValue.toString();
            destination = destination.substring(destination.indexOf("#") + 1);

            Individual toIndividual = ontologyModel.getIndividual(baseURI + destination);
            // System.out.println("DEBUG toIndividual = " +toIndividual );
            RDFNode toElementNameNode = toIndividual.getPropertyValue(ontElementName);
            //    System.out.println("DEBUG fromElementNameNode = " +fromElementNameNode );
            String toString = toElementNameNode.toString();
            toString = toString.substring(0, toString.indexOf("^"));
            //   toString = toString.toLowerCase();

            //     System.out.println("DEBUG toString " + toString);



            aiItem = aiItem + toString + ")\n";

            // mapiranje ide u oba smjera!! - zato ovo:

            aiItem = aiItem + "(hasServiceIOMapping ";
            aiItem = aiItem + toString + " ";
            aiItem = aiItem + fromString + ")\n";

            System.out.println("DEBUG AI item = " + aiItem);
            result = result + aiItem;


        }

        // for composite mappings
        // ne treba, jer su i composite mapping subclass of simple mapping pa sve dobijem u prošlom koraku
/*
         ExtendedIterator<Individual> compositeMappingsIndividuals = ontologyModel.listIndividuals(ontologyModel.getOntClass(baseURI + "CompositeServiceDataMapper"));


         while (compositeMappingsIndividuals.hasNext()) {
         Individual individual = compositeMappingsIndividuals.next();

         OntProperty fromOntElement = ontologyModel.getOntProperty(baseURI + "fromOntElement");
         OntProperty toOntElement = ontologyModel.getOntProperty(baseURI + "toOntElement");
         OntProperty ontElementName = ontologyModel.getOntProperty(baseURI + "ontElementName");

         RDFNode iValue = individual.getPropertyValue(fromOntElement);



         String source = iValue.toString();
         source = source.substring(source.indexOf("#") + 1);
         // source = source.toLowerCase();

         Individual fromIndividual = ontologyModel.getIndividual(baseURI + source);
         //  System.out.println("DEBUG fromIndividual = " +fromIndividual );
         RDFNode fromElementNameNode = fromIndividual.getPropertyValue(ontElementName);
         //    System.out.println("DEBUG fromElementNameNode = " +fromElementNameNode );
         String fromString = fromElementNameNode.toString();
         fromString = fromString.substring(0, fromString.indexOf("^"));
         // fromString = fromString.toLowerCase();

         // System.out.println("DEBUG fromString " + fromString);


         String aiItem = "(hasServiceIOMapping ";
         aiItem = aiItem + fromString + " ";

         RDFNode jValue = individual.getPropertyValue(toOntElement);
         String destination = jValue.toString();
         destination = destination.substring(destination.indexOf("#") + 1);

         Individual toIndividual = ontologyModel.getIndividual(baseURI + destination);
         // System.out.println("DEBUG toIndividual = " +toIndividual );
         RDFNode toElementNameNode = toIndividual.getPropertyValue(ontElementName);
         //    System.out.println("DEBUG fromElementNameNode = " +fromElementNameNode );
         String toString = toElementNameNode.toString();
         toString = toString.substring(0, toString.indexOf("^"));
         // toString = toString.toLowerCase();

         Individual toMapedIndividual = ontologyModel.getIndividual(baseURI + toString);
         System.out.println("DEBUG toString " + toMapedIndividual);
         String classNameOfTheToIndividual = toMapedIndividual.getOntClass().getLocalName();
         System.out.println("DEBUG class name of the toIndividual = " + classNameOfTheToIndividual);



         aiItem = aiItem + toString + ")\n";


         System.out.println("DEBUG AI item = " + aiItem);
         result = result + aiItem;


         }
         */

        // isOfType

        // subclasses of sObject OWL class
        OntClass sObjectOntClass = ontologyModel.getOntClass(baseURI + "SalesforceSObject");
        
        if (sObjectOntClass != null) {
            if (sObjectOntClass.listSubClasses() != null) {

                for (Iterator i = sObjectOntClass.listSubClasses(); i.hasNext();) {
                    OntClass c = (OntClass) i.next();
                    // System.out.print(" DEBUG SubClasses   " + c.getLocalName() + " " + "\n");

                    String aiItem = "(isOfType ";

                    aiItem = aiItem + c.getLocalName() + " SObject)\n";

                    System.out.println("DEBUG AI item = " + aiItem);
                    result = result + aiItem;


                }
            }
        }

        // individuals og Google Entity class
        OntClass googleEntityOntClass = ontologyModel.getOntClass(baseURI + "GoogleEntity");
        for (Iterator i = googleEntityOntClass.listInstances(); i.hasNext();) {
            Individual inv = (Individual) i.next();
            //   System.out.print(" DEBUG Individuals   " + inv.getLocalName() + " " + "\n");

            String aiItem = "(isOfType ";

            aiItem = aiItem + inv.getLocalName() + " GoogleEntity)\n";

            System.out.println("DEBUG AI item = " + aiItem);
            result = result + aiItem;


        }


        ontologyModel.close();
        return result;

    }
}
