
package hr.org.foi.salesforce.api.services;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for asyncRequestState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="asyncRequestState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Queued"/>
 *     &lt;enumeration value="InProgress"/>
 *     &lt;enumeration value="Completed"/>
 *     &lt;enumeration value="Error"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "asyncRequestState")
@XmlEnum
public enum AsyncRequestState {

    @XmlEnumValue("Queued")
    QUEUED("Queued"),
    @XmlEnumValue("InProgress")
    IN_PROGRESS("InProgress"),
    @XmlEnumValue("Completed")
    COMPLETED("Completed"),
    @XmlEnumValue("Error")
    ERROR("Error");
    private final String value;

    AsyncRequestState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AsyncRequestState fromValue(String v) {
        for (AsyncRequestState c: AsyncRequestState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
