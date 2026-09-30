
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema.include;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipoMittente.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoMittente"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Cittadino"/&gt;
 *     &lt;enumeration value="Altro"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TipoMittente")
@XmlEnum
public enum TipoMittente {

    @XmlEnumValue("Cittadino")
    CITTADINO("Cittadino"),
    @XmlEnumValue("Altro")
    ALTRO("Altro");
    private final String value;

    TipoMittente(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoMittente fromValue(String v) {
        for (TipoMittente c: TipoMittente.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
