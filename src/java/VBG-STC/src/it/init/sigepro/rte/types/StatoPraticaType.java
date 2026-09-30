
package it.init.sigepro.rte.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatoPraticaType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoPraticaType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Attiva"/>
 *     &lt;enumeration value="ChiusaPositivamente"/>
 *     &lt;enumeration value="ChiusaNegativamente"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StatoPraticaType")
@XmlEnum
public enum StatoPraticaType {

    @XmlEnumValue("Attiva")
    ATTIVA("Attiva"),
    @XmlEnumValue("ChiusaPositivamente")
    CHIUSA_POSITIVAMENTE("ChiusaPositivamente"),
    @XmlEnumValue("ChiusaNegativamente")
    CHIUSA_NEGATIVAMENTE("ChiusaNegativamente");
    private final String value;

    StatoPraticaType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatoPraticaType fromValue(String v) {
        for (StatoPraticaType c: StatoPraticaType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
