
package it.init.sigepro.rte.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoCampoDinamicoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoCampoDinamicoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Checkbox"/>
 *     &lt;enumeration value="Data"/>
 *     &lt;enumeration value="Lista"/>
 *     &lt;enumeration value="Testo"/>
 *     &lt;enumeration value="NumericoIntero"/>
 *     &lt;enumeration value="NumericoDouble"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoCampoDinamicoType")
@XmlEnum
public enum TipoCampoDinamicoType {

    @XmlEnumValue("Checkbox")
    CHECKBOX("Checkbox"),
    @XmlEnumValue("Data")
    DATA("Data"),
    @XmlEnumValue("Lista")
    LISTA("Lista"),
    @XmlEnumValue("Testo")
    TESTO("Testo"),
    @XmlEnumValue("NumericoIntero")
    NUMERICO_INTERO("NumericoIntero"),
    @XmlEnumValue("NumericoDouble")
    NUMERICO_DOUBLE("NumericoDouble");
    private final String value;

    TipoCampoDinamicoType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoCampoDinamicoType fromValue(String v) {
        for (TipoCampoDinamicoType c: TipoCampoDinamicoType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
