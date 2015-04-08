
package hr.org.foi.salesforce.api.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataCategoryGroupSobjectTypePair complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataCategoryGroupSobjectTypePair">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataCategoryGroupName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sobject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataCategoryGroupSobjectTypePair", propOrder = {
    "dataCategoryGroupName",
    "sobject"
})
public class DataCategoryGroupSobjectTypePair {

    protected String dataCategoryGroupName;
    protected String sobject;

    /**
     * Gets the value of the dataCategoryGroupName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataCategoryGroupName() {
        return dataCategoryGroupName;
    }

    /**
     * Sets the value of the dataCategoryGroupName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataCategoryGroupName(String value) {
        this.dataCategoryGroupName = value;
    }

    /**
     * Gets the value of the sobject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSobject() {
        return sobject;
    }

    /**
     * Sets the value of the sobject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSobject(String value) {
        this.sobject = value;
    }

}
