
package hr.org.foi.salesforce.api.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mergeRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mergeRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="masterRecord" type="{http://services.api.salesforce.foi.org.hr/}sObject" minOccurs="0"/>
 *         &lt;element name="recordToMergeIds" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mergeRequest", propOrder = {
    "masterRecord",
    "recordToMergeIds"
})
public class MergeRequest {

    protected SObject masterRecord;
    @XmlElement(nillable = true)
    protected List<String> recordToMergeIds;

    /**
     * Gets the value of the masterRecord property.
     * 
     * @return
     *     possible object is
     *     {@link SObject }
     *     
     */
    public SObject getMasterRecord() {
        return masterRecord;
    }

    /**
     * Sets the value of the masterRecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link SObject }
     *     
     */
    public void setMasterRecord(SObject value) {
        this.masterRecord = value;
    }

    /**
     * Gets the value of the recordToMergeIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the recordToMergeIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRecordToMergeIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRecordToMergeIds() {
        if (recordToMergeIds == null) {
            recordToMergeIds = new ArrayList<String>();
        }
        return this.recordToMergeIds;
    }

}
