
package hr.org.foi.salesforce.api.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for convertLeadRecords complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="convertLeadRecords">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="leadsToConvert" type="{http://services.api.salesforce.foi.org.hr/}leadConvert" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "convertLeadRecords", propOrder = {
    "leadsToConvert"
})
public class ConvertLeadRecords {

    @XmlElement(nillable = true)
    protected List<LeadConvert> leadsToConvert;

    /**
     * Gets the value of the leadsToConvert property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the leadsToConvert property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLeadsToConvert().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LeadConvert }
     * 
     * 
     */
    public List<LeadConvert> getLeadsToConvert() {
        if (leadsToConvert == null) {
            leadsToConvert = new ArrayList<LeadConvert>();
        }
        return this.leadsToConvert;
    }

}
