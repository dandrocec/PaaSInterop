
package hr.org.foi.aiplanning.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getInteroperabilityProblems complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getInteroperabilityProblems">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="aiGoal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInteroperabilityProblems", propOrder = {
    "aiGoal"
})
public class GetInteroperabilityProblems {

    protected String aiGoal;

    /**
     * Gets the value of the aiGoal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAiGoal() {
        return aiGoal;
    }

    /**
     * Sets the value of the aiGoal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAiGoal(String value) {
        this.aiGoal = value;
    }

}
