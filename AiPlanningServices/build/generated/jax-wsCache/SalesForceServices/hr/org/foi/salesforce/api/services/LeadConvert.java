
package hr.org.foi.salesforce.api.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for leadConvert complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="leadConvert">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contactId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="convertedStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="doNotCreateOpportunity" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="leadId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="opportunityName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="overwriteLeadSource" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ownerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sendNotificationEmail" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "leadConvert", propOrder = {
    "accountId",
    "contactId",
    "convertedStatus",
    "doNotCreateOpportunity",
    "leadId",
    "opportunityName",
    "overwriteLeadSource",
    "ownerId",
    "sendNotificationEmail"
})
public class LeadConvert {

    protected String accountId;
    protected String contactId;
    protected String convertedStatus;
    protected boolean doNotCreateOpportunity;
    protected String leadId;
    protected String opportunityName;
    protected boolean overwriteLeadSource;
    protected String ownerId;
    protected boolean sendNotificationEmail;

    /**
     * Gets the value of the accountId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Sets the value of the accountId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountId(String value) {
        this.accountId = value;
    }

    /**
     * Gets the value of the contactId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactId() {
        return contactId;
    }

    /**
     * Sets the value of the contactId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactId(String value) {
        this.contactId = value;
    }

    /**
     * Gets the value of the convertedStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConvertedStatus() {
        return convertedStatus;
    }

    /**
     * Sets the value of the convertedStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConvertedStatus(String value) {
        this.convertedStatus = value;
    }

    /**
     * Gets the value of the doNotCreateOpportunity property.
     * 
     */
    public boolean isDoNotCreateOpportunity() {
        return doNotCreateOpportunity;
    }

    /**
     * Sets the value of the doNotCreateOpportunity property.
     * 
     */
    public void setDoNotCreateOpportunity(boolean value) {
        this.doNotCreateOpportunity = value;
    }

    /**
     * Gets the value of the leadId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeadId() {
        return leadId;
    }

    /**
     * Sets the value of the leadId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeadId(String value) {
        this.leadId = value;
    }

    /**
     * Gets the value of the opportunityName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpportunityName() {
        return opportunityName;
    }

    /**
     * Sets the value of the opportunityName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpportunityName(String value) {
        this.opportunityName = value;
    }

    /**
     * Gets the value of the overwriteLeadSource property.
     * 
     */
    public boolean isOverwriteLeadSource() {
        return overwriteLeadSource;
    }

    /**
     * Sets the value of the overwriteLeadSource property.
     * 
     */
    public void setOverwriteLeadSource(boolean value) {
        this.overwriteLeadSource = value;
    }

    /**
     * Gets the value of the ownerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * Sets the value of the ownerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnerId(String value) {
        this.ownerId = value;
    }

    /**
     * Gets the value of the sendNotificationEmail property.
     * 
     */
    public boolean isSendNotificationEmail() {
        return sendNotificationEmail;
    }

    /**
     * Sets the value of the sendNotificationEmail property.
     * 
     */
    public void setSendNotificationEmail(boolean value) {
        this.sendNotificationEmail = value;
    }

}
