/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.ontology.services;

import hr.org.foi.jena.DataOntologyReader;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Darko Androcec
 */
@WebService(serviceName = "OntologyServices")
public class OntologyServices {

   
    @WebMethod(operationName = "readOntology")
    public String readOntology(@WebParam(name = "pathToOntology") String pathToOntology) {
        DataOntologyReader.readOntology(pathToOntology);
        return "Reading ontology " + pathToOntology;
    }
}
