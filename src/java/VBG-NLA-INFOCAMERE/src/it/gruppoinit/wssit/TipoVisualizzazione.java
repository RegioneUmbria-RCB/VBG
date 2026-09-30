
package it.gruppoinit.wssit;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoVisualizzazione.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoVisualizzazione">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PuntoDaIndirizzo"/>
 *     &lt;enumeration value="PuntoDaMappale"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoVisualizzazione")
@XmlEnum
public enum TipoVisualizzazione {

    @XmlEnumValue("PuntoDaIndirizzo")
    PUNTO_DA_INDIRIZZO("PuntoDaIndirizzo"),
    @XmlEnumValue("PuntoDaMappale")
    PUNTO_DA_MAPPALE("PuntoDaMappale");
    private final String value;

    TipoVisualizzazione(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoVisualizzazione fromValue(String v) {
        for (TipoVisualizzazione c: TipoVisualizzazione.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
