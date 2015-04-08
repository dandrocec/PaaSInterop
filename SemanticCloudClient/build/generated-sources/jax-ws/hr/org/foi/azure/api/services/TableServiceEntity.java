
package hr.org.foi.azure.api.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for tableServiceEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tableServiceEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="etag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partitionKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rowKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tableServiceEntity", propOrder = {
    "etag",
    "partitionKey",
    "rowKey",
    "timestamp"
})
public class TableServiceEntity {

    protected String etag;
    protected String partitionKey;
    protected String rowKey;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;

    /**
     * Gets the value of the etag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEtag() {
        return etag;
    }

    /**
     * Sets the value of the etag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEtag(String value) {
        this.etag = value;
    }

    /**
     * Gets the value of the partitionKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartitionKey() {
        return partitionKey;
    }

    /**
     * Sets the value of the partitionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartitionKey(String value) {
        this.partitionKey = value;
    }

    /**
     * Gets the value of the rowKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRowKey() {
        return rowKey;
    }

    /**
     * Sets the value of the rowKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRowKey(String value) {
        this.rowKey = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

}
