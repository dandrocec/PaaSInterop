
package hr.org.foi.salesforce.api.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for upsert complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="upsert">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalIdFieldName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sObjects" type="{http://services.api.salesforce.foi.org.hr/}sObject" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "upsert", propOrder = {
    "externalIdFieldName",
    "sObjects"
})
public class Upsert {

    protected String externalIdFieldName;
    @XmlElement(nillable = true)
    protected List<SObject> sObjects;

    /**
     * Gets the value of the externalIdFieldName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalIdFieldName() {
        return externalIdFieldName;
    }

    /**
     * Sets the value of the externalIdFieldName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalIdFieldName(String value) {
        this.externalIdFieldName = value;
    }

    /**
     * Gets the value of the sObjects property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sObjects property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSObjects().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SObject }
     * 
     * 
     */
    public List<SObject> getSObjects() {
        if (sObjects == null) {
            sObjects = new ArrayList<SObject>();
        }
        return this.sObjects;
    }

}
