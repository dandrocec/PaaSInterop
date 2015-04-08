
package hr.org.foi.appengine.api.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for query complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="query">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GQLquery" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "query", propOrder = {
    "gqLquery"
})
public class Query {

    @XmlElement(name = "GQLquery")
    protected String gqLquery;

    /**
     * Gets the value of the gqLquery property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGQLquery() {
        return gqLquery;
    }

    /**
     * Sets the value of the gqLquery property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGQLquery(String value) {
        this.gqLquery = value;
    }

}
