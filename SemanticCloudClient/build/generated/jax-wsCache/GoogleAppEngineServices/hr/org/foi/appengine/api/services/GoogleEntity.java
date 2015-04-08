
package hr.org.foi.appengine.api.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for googleEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="googleEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="googleEntityKey" type="{http://services.api.appengine.foi.org.hr/}googleKey" minOccurs="0"/>
 *         &lt;element name="googleEntityKind" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="googleEntityProperties" type="{http://services.api.appengine.foi.org.hr/}googleProperty" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "googleEntity", propOrder = {
    "googleEntityKey",
    "googleEntityKind",
    "googleEntityProperties"
})
public class GoogleEntity {

    protected GoogleKey googleEntityKey;
    protected String googleEntityKind;
    @XmlElement(nillable = true)
    protected List<GoogleProperty> googleEntityProperties;

    /**
     * Gets the value of the googleEntityKey property.
     * 
     * @return
     *     possible object is
     *     {@link GoogleKey }
     *     
     */
    public GoogleKey getGoogleEntityKey() {
        return googleEntityKey;
    }

    /**
     * Sets the value of the googleEntityKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GoogleKey }
     *     
     */
    public void setGoogleEntityKey(GoogleKey value) {
        this.googleEntityKey = value;
    }

    /**
     * Gets the value of the googleEntityKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoogleEntityKind() {
        return googleEntityKind;
    }

    /**
     * Sets the value of the googleEntityKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoogleEntityKind(String value) {
        this.googleEntityKind = value;
    }

    /**
     * Gets the value of the googleEntityProperties property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the googleEntityProperties property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGoogleEntityProperties().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GoogleProperty }
     * 
     * 
     */
    public List<GoogleProperty> getGoogleEntityProperties() {
        if (googleEntityProperties == null) {
            googleEntityProperties = new ArrayList<GoogleProperty>();
        }
        return this.googleEntityProperties;
    }

}
