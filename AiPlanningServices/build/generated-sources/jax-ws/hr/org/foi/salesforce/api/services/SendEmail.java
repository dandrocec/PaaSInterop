
package hr.org.foi.salesforce.api.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sendEmail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sendEmail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="message" type="{http://services.api.salesforce.foi.org.hr/}singleEmailMessage" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sendEmail", propOrder = {
    "message"
})
public class SendEmail {

    protected SingleEmailMessage message;

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link SingleEmailMessage }
     *     
     */
    public SingleEmailMessage getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link SingleEmailMessage }
     *     
     */
    public void setMessage(SingleEmailMessage value) {
        this.message = value;
    }

}
