
package hr.org.foi.aiplanning.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for removeUnrelatedCsvFiles complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="removeUnrelatedCsvFiles">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="directory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="chosenDataContainer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeUnrelatedCsvFiles", propOrder = {
    "directory",
    "chosenDataContainer"
})
public class RemoveUnrelatedCsvFiles {

    protected String directory;
    protected String chosenDataContainer;

    /**
     * Gets the value of the directory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * Sets the value of the directory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirectory(String value) {
        this.directory = value;
    }

    /**
     * Gets the value of the chosenDataContainer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChosenDataContainer() {
        return chosenDataContainer;
    }

    /**
     * Sets the value of the chosenDataContainer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChosenDataContainer(String value) {
        this.chosenDataContainer = value;
    }

}
