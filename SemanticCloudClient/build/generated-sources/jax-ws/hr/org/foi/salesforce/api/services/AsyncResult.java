
package hr.org.foi.salesforce.api.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for asyncResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="asyncResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="checkOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="done" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numberComponentErrors" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numberComponentsDeployed" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numberComponentsTotal" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numberTestErrors" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numberTestsCompleted" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numberTestsTotal" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="state" type="{http://services.api.salesforce.foi.org.hr/}asyncRequestState" minOccurs="0"/>
 *         &lt;element name="stateDetail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stateDetailLastModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{http://services.api.salesforce.foi.org.hr/}statusCode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "asyncResult", propOrder = {
    "checkOnly",
    "done",
    "id",
    "message",
    "numberComponentErrors",
    "numberComponentsDeployed",
    "numberComponentsTotal",
    "numberTestErrors",
    "numberTestsCompleted",
    "numberTestsTotal",
    "state",
    "stateDetail",
    "stateDetailLastModifiedDate",
    "statusCode"
})
public class AsyncResult {

    protected boolean checkOnly;
    protected boolean done;
    protected String id;
    protected String message;
    protected int numberComponentErrors;
    protected int numberComponentsDeployed;
    protected int numberComponentsTotal;
    protected int numberTestErrors;
    protected int numberTestsCompleted;
    protected int numberTestsTotal;
    protected AsyncRequestState state;
    protected String stateDetail;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar stateDetailLastModifiedDate;
    protected StatusCode statusCode;

    /**
     * Gets the value of the checkOnly property.
     * 
     */
    public boolean isCheckOnly() {
        return checkOnly;
    }

    /**
     * Sets the value of the checkOnly property.
     * 
     */
    public void setCheckOnly(boolean value) {
        this.checkOnly = value;
    }

    /**
     * Gets the value of the done property.
     * 
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Sets the value of the done property.
     * 
     */
    public void setDone(boolean value) {
        this.done = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the numberComponentErrors property.
     * 
     */
    public int getNumberComponentErrors() {
        return numberComponentErrors;
    }

    /**
     * Sets the value of the numberComponentErrors property.
     * 
     */
    public void setNumberComponentErrors(int value) {
        this.numberComponentErrors = value;
    }

    /**
     * Gets the value of the numberComponentsDeployed property.
     * 
     */
    public int getNumberComponentsDeployed() {
        return numberComponentsDeployed;
    }

    /**
     * Sets the value of the numberComponentsDeployed property.
     * 
     */
    public void setNumberComponentsDeployed(int value) {
        this.numberComponentsDeployed = value;
    }

    /**
     * Gets the value of the numberComponentsTotal property.
     * 
     */
    public int getNumberComponentsTotal() {
        return numberComponentsTotal;
    }

    /**
     * Sets the value of the numberComponentsTotal property.
     * 
     */
    public void setNumberComponentsTotal(int value) {
        this.numberComponentsTotal = value;
    }

    /**
     * Gets the value of the numberTestErrors property.
     * 
     */
    public int getNumberTestErrors() {
        return numberTestErrors;
    }

    /**
     * Sets the value of the numberTestErrors property.
     * 
     */
    public void setNumberTestErrors(int value) {
        this.numberTestErrors = value;
    }

    /**
     * Gets the value of the numberTestsCompleted property.
     * 
     */
    public int getNumberTestsCompleted() {
        return numberTestsCompleted;
    }

    /**
     * Sets the value of the numberTestsCompleted property.
     * 
     */
    public void setNumberTestsCompleted(int value) {
        this.numberTestsCompleted = value;
    }

    /**
     * Gets the value of the numberTestsTotal property.
     * 
     */
    public int getNumberTestsTotal() {
        return numberTestsTotal;
    }

    /**
     * Sets the value of the numberTestsTotal property.
     * 
     */
    public void setNumberTestsTotal(int value) {
        this.numberTestsTotal = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link AsyncRequestState }
     *     
     */
    public AsyncRequestState getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link AsyncRequestState }
     *     
     */
    public void setState(AsyncRequestState value) {
        this.state = value;
    }

    /**
     * Gets the value of the stateDetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateDetail() {
        return stateDetail;
    }

    /**
     * Sets the value of the stateDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateDetail(String value) {
        this.stateDetail = value;
    }

    /**
     * Gets the value of the stateDetailLastModifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStateDetailLastModifiedDate() {
        return stateDetailLastModifiedDate;
    }

    /**
     * Sets the value of the stateDetailLastModifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStateDetailLastModifiedDate(XMLGregorianCalendar value) {
        this.stateDetailLastModifiedDate = value;
    }

    /**
     * Gets the value of the statusCode property.
     * 
     * @return
     *     possible object is
     *     {@link StatusCode }
     *     
     */
    public StatusCode getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusCode }
     *     
     */
    public void setStatusCode(StatusCode value) {
        this.statusCode = value;
    }

}
