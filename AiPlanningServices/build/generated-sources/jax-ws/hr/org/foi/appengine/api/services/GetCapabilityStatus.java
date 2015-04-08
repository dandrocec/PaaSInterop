
package hr.org.foi.appengine.api.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getCapabilityStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getCapabilityStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="capability" type="{http://services.api.appengine.foi.org.hr/}capability" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCapabilityStatus", propOrder = {
    "capability"
})
public class GetCapabilityStatus {

    protected Capability capability;

    /**
     * Gets the value of the capability property.
     * 
     * @return
     *     possible object is
     *     {@link Capability }
     *     
     */
    public Capability getCapability() {
        return capability;
    }

    /**
     * Sets the value of the capability property.
     * 
     * @param value
     *     allowed object is
     *     {@link Capability }
     *     
     */
    public void setCapability(Capability value) {
        this.capability = value;
    }

}
