
package it.init.sigepro.rte.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoCampoStaticoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoCampoStaticoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Titolo"/>
 *     &lt;enumeration value="TestoEsteso"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoCampoStaticoType")
@XmlEnum
public enum TipoCampoStaticoType {

    @XmlEnumValue("Titolo")
    TITOLO("Titolo"),
    @XmlEnumValue("TestoEsteso")
    TESTO_ESTESO("TestoEsteso");
    private final String value;

    TipoCampoStaticoType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoCampoStaticoType fromValue(String v) {
        for (TipoCampoStaticoType c: TipoCampoStaticoType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
