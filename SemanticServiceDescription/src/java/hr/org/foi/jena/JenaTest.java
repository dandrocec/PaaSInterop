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
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.vocabulary.XSD;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Darko Androcec
 */
public class JenaTest {

    public static void main(String[] args) {
        //ModelMaker modelMaker = ModelFactory.createFileModelMaker("testOntology.owl");
        String baseURI = "http://hr.org.foi.ontology/FirstOntology#";
        //Create ontology model
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
//Create a class
        OntClass customer = model.createClass(baseURI + "Customer");
        
// Create data properties
        
        DatatypeProperty customerId = model.createDatatypeProperty(baseURI+"customerId"); 
        customerId.setDomain(customer);
        customerId.setRange(XSD.xint);
        
        DatatypeProperty customerName = model.createDatatypeProperty(baseURI+"customerName"); 
        customerName.setDomain(customer);
        customerName.setRange(XSD.xstring);
        
        DatatypeProperty address = model.createDatatypeProperty(baseURI+"address"); 
        address.setDomain(customer);
        address.setRange(XSD.xstring);
        
        DatatypeProperty phoneNumber = model.createDatatypeProperty(baseURI+"phoneNumber"); 
        phoneNumber.setDomain(customer);
        phoneNumber.setRange(XSD.xstring);

//Create an individual for class
        Individual customer1 = model.createIndividual(baseURI + "customer1", customer);
        customer1.addProperty(customerId, "1", XSDDatatype.XSDint);
        customer1.addProperty(customerName, "Pero Peric", XSDDatatype.XSDstring);
        customer1.addProperty(address, "Zagrebačka 23, 42000 Varaždin", XSDDatatype.XSDstring);
        customer1.addProperty(phoneNumber, "042/323-456", XSDDatatype.XSDstring);

        try {

            File file = new File("D:\\DOKTORAT\\generiraneOntologije\\firstOntology.owl");
            model.write(new FileOutputStream(file));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
