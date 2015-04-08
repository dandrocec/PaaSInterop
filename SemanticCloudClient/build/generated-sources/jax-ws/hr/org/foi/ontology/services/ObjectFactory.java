
package hr.org.foi.ontology.services;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hr.org.foi.ontology.services package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ReadOntologyResponse_QNAME = new QName("http://services.ontology.foi.org.hr/", "readOntologyResponse");
    private final static QName _ReadOntology_QNAME = new QName("http://services.ontology.foi.org.hr/", "readOntology");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hr.org.foi.ontology.services
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReadOntology }
     * 
     */
    public ReadOntology createReadOntology() {
        return new ReadOntology();
    }

    /**
     * Create an instance of {@link ReadOntologyResponse }
     * 
     */
    public ReadOntologyResponse createReadOntologyResponse() {
        return new ReadOntologyResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadOntologyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.ontology.foi.org.hr/", name = "readOntologyResponse")
    public JAXBElement<ReadOntologyResponse> createReadOntologyResponse(ReadOntologyResponse value) {
        return new JAXBElement<ReadOntologyResponse>(_ReadOntologyResponse_QNAME, ReadOntologyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadOntology }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.ontology.foi.org.hr/", name = "readOntology")
    public JAXBElement<ReadOntology> createReadOntology(ReadOntology value) {
        return new JAXBElement<ReadOntology>(_ReadOntology_QNAME, ReadOntology.class, null, value);
    }

}
