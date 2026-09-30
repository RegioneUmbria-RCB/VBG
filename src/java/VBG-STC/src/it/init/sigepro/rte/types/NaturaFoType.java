
package it.init.sigepro.rte.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NaturaFoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NaturaFoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="comunicazione"/>
 *     &lt;enumeration value="ordinario"/>
 *     &lt;enumeration value="scia"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "NaturaFoType")
@XmlEnum
public enum NaturaFoType {

    @XmlEnumValue("comunicazione")
    COMUNICAZIONE("comunicazione"),
    @XmlEnumValue("ordinario")
    ORDINARIO("ordinario"),
    @XmlEnumValue("scia")
    SCIA("scia");
    private final String value;

    NaturaFoType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NaturaFoType fromValue(String v) {
        for (NaturaFoType c: NaturaFoType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
