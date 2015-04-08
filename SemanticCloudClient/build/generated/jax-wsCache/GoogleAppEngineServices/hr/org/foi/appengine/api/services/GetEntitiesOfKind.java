
package hr.org.foi.appengine.api.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getEntitiesOfKind complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getEntitiesOfKind">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="entityKind" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getEntitiesOfKind", propOrder = {
    "entityKind"
})
public class GetEntitiesOfKind {

    protected String entityKind;

    /**
     * Gets the value of the entityKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntityKind() {
        return entityKind;
    }

    /**
     * Sets the value of the entityKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntityKind(String value) {
        this.entityKind = value;
    }

}
