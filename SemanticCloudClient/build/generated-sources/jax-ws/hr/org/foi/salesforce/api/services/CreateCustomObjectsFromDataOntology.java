
package hr.org.foi.salesforce.api.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createCustomObjectsFromDataOntology complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createCustomObjectsFromDataOntology">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pathToOntology" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createCustomObjectsFromDataOntology", propOrder = {
    "pathToOntology"
})
public class CreateCustomObjectsFromDataOntology {

    protected String pathToOntology;

    /**
     * Gets the value of the pathToOntology property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPathToOntology() {
        return pathToOntology;
    }

    /**
     * Sets the value of the pathToOntology property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPathToOntology(String value) {
        this.pathToOntology = value;
    }

}
