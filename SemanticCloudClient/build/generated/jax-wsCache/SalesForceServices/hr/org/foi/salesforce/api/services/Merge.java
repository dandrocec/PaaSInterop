
package hr.org.foi.salesforce.api.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for merge complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="merge">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mergeRequests" type="{http://services.api.salesforce.foi.org.hr/}mergeRequest" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "merge", propOrder = {
    "mergeRequests"
})
public class Merge {

    @XmlElement(nillable = true)
    protected List<MergeRequest> mergeRequests;

    /**
     * Gets the value of the mergeRequests property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mergeRequests property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMergeRequests().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MergeRequest }
     * 
     * 
     */
    public List<MergeRequest> getMergeRequests() {
        if (mergeRequests == null) {
            mergeRequests = new ArrayList<MergeRequest>();
        }
        return this.mergeRequests;
    }

}
