
package hr.org.foi.salesforce.api.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for describeMetadata complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="describeMetadata">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="apiVersion" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "describeMetadata", propOrder = {
    "apiVersion"
})
public class DescribeMetadata {

    protected double apiVersion;

    /**
     * Gets the value of the apiVersion property.
     * 
     */
    public double getApiVersion() {
        return apiVersion;
    }

    /**
     * Sets the value of the apiVersion property.
     * 
     */
    public void setApiVersion(double value) {
        this.apiVersion = value;
    }

}
