
package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipoDestinatario.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoDestinatario"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Cittadino"/&gt;
 *     &lt;enumeration value="Delegato"/&gt;
 *     &lt;enumeration value="Altro"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TipoDestinatario")
@XmlEnum
public enum TipoDestinatario {

    @XmlEnumValue("Cittadino")
    CITTADINO("Cittadino"),
    @XmlEnumValue("Delegato")
    DELEGATO("Delegato"),
    @XmlEnumValue("Altro")
    ALTRO("Altro");
    private final String value;

    TipoDestinatario(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoDestinatario fromValue(String v) {
        for (TipoDestinatario c: TipoDestinatario.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
