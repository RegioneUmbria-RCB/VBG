
package it.init.sigepro.rte.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatoIterType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoIterType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Sospesa"/>
 *     &lt;enumeration value="Interrotta"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StatoIterType")
@XmlEnum
public enum StatoIterType {

    @XmlEnumValue("Sospesa")
    SOSPESA("Sospesa"),
    @XmlEnumValue("Interrotta")
    INTERROTTA("Interrotta");
    private final String value;

    StatoIterType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatoIterType fromValue(String v) {
        for (StatoIterType c: StatoIterType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
