
package it.gruppoinit.wssit;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FiltroRicercaListaVie.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FiltroRicercaListaVie">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Tutte"/>
 *     &lt;enumeration value="Cessata"/>
 *     &lt;enumeration value="Attiva"/>
 *     &lt;enumeration value="Modificata"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FiltroRicercaListaVie")
@XmlEnum
public enum FiltroRicercaListaVie {

    @XmlEnumValue("Tutte")
    TUTTE("Tutte"),
    @XmlEnumValue("Cessata")
    CESSATA("Cessata"),
    @XmlEnumValue("Attiva")
    ATTIVA("Attiva"),
    @XmlEnumValue("Modificata")
    MODIFICATA("Modificata");
    private final String value;

    FiltroRicercaListaVie(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FiltroRicercaListaVie fromValue(String v) {
        for (FiltroRicercaListaVie c: FiltroRicercaListaVie.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
