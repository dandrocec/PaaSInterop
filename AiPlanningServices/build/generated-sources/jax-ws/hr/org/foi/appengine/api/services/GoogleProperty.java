
package hr.org.foi.appengine.api.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for googleProperty complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="googleProperty">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="googleProperyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="googleProperyValue" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "googleProperty", propOrder = {
    "googleProperyName",
    "googleProperyValue"
})
public class GoogleProperty {

    protected String googleProperyName;
    protected Object googleProperyValue;

    /**
     * Gets the value of the googleProperyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoogleProperyName() {
        return googleProperyName;
    }

    /**
     * Sets the value of the googleProperyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoogleProperyName(String value) {
        this.googleProperyName = value;
    }

    /**
     * Gets the value of the googleProperyValue property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getGoogleProperyValue() {
        return googleProperyValue;
    }

    /**
     * Sets the value of the googleProperyValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setGoogleProperyValue(Object value) {
        this.googleProperyValue = value;
    }

}
