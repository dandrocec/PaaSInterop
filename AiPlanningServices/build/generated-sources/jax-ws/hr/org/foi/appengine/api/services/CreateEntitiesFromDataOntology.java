
package hr.org.foi.appengine.api.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createEntitiesFromDataOntology complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createEntitiesFromDataOntology">
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
@XmlType(name = "createEntitiesFromDataOntology", propOrder = {
    "pathToOntology"
})
public class CreateEntitiesFromDataOntology {

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
