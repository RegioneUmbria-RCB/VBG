
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per GravitaAnomalia.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="GravitaAnomalia"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Info"/&gt;
 *     &lt;enumeration value="Bassa"/&gt;
 *     &lt;enumeration value="Alta"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "GravitaAnomalia")
@XmlEnum
public enum GravitaAnomalia {

    @XmlEnumValue("Info")
    INFO("Info"),
    @XmlEnumValue("Bassa")
    BASSA("Bassa"),
    @XmlEnumValue("Alta")
    ALTA("Alta");
    private final String value;

    GravitaAnomalia(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GravitaAnomalia fromValue(String v) {
        for (GravitaAnomalia c: GravitaAnomalia.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
