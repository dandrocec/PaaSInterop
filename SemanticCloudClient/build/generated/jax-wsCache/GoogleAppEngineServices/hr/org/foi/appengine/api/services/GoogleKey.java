
package hr.org.foi.appengine.api.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for googleKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="googleKey">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="googleKeyId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="googleKeyKind" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="googleKeyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="googleKeyNamespace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "googleKey", propOrder = {
    "googleKeyId",
    "googleKeyKind",
    "googleKeyName",
    "googleKeyNamespace"
})
public class GoogleKey {

    protected long googleKeyId;
    protected String googleKeyKind;
    protected String googleKeyName;
    protected String googleKeyNamespace;

    /**
     * Gets the value of the googleKeyId property.
     * 
     */
    public long getGoogleKeyId() {
        return googleKeyId;
    }

    /**
     * Sets the value of the googleKeyId property.
     * 
     */
    public void setGoogleKeyId(long value) {
        this.googleKeyId = value;
    }

    /**
     * Gets the value of the googleKeyKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoogleKeyKind() {
        return googleKeyKind;
    }

    /**
     * Sets the value of the googleKeyKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoogleKeyKind(String value) {
        this.googleKeyKind = value;
    }

    /**
     * Gets the value of the googleKeyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoogleKeyName() {
        return googleKeyName;
    }

    /**
     * Sets the value of the googleKeyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoogleKeyName(String value) {
        this.googleKeyName = value;
    }

    /**
     * Gets the value of the googleKeyNamespace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoogleKeyNamespace() {
        return googleKeyNamespace;
    }

    /**
     * Sets the value of the googleKeyNamespace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoogleKeyNamespace(String value) {
        this.googleKeyNamespace = value;
    }

}
