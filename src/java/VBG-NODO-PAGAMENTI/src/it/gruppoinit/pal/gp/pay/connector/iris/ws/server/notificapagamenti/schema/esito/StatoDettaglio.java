
package it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema.esito;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per StatoDettaglio.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoDettaglio"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Scartato"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "StatoDettaglio")
@XmlEnum
public enum StatoDettaglio {

    @XmlEnumValue("Scartato")
    SCARTATO("Scartato");
    private final String value;

    StatoDettaglio(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatoDettaglio fromValue(String v) {
        for (StatoDettaglio c: StatoDettaglio.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
