
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ICP_TipiRilevazione.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="ICP_TipiRilevazione"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Contribuente"/&gt;
 *     &lt;enumeration value="Censimento"/&gt;
 *     &lt;enumeration value="Ispettore"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ICP_TipiRilevazione")
@XmlEnum
public enum ICPTipiRilevazione {

    @XmlEnumValue("Contribuente")
    CONTRIBUENTE("Contribuente"),
    @XmlEnumValue("Censimento")
    CENSIMENTO("Censimento"),
    @XmlEnumValue("Ispettore")
    ISPETTORE("Ispettore");
    private final String value;

    ICPTipiRilevazione(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ICPTipiRilevazione fromValue(String v) {
        for (ICPTipiRilevazione c: ICPTipiRilevazione.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
