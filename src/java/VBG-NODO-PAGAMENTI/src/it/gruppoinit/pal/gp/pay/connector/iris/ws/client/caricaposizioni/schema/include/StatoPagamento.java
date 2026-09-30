
package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per StatoPagamento.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoPagamento"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Non Pagato"/&gt;
 *     &lt;enumeration value="Pagato"/&gt;
 *     &lt;enumeration value="Non Pagabile"/&gt;
 *     &lt;enumeration value="Pagamento Irregolare"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "StatoPagamento")
@XmlEnum
public enum StatoPagamento {

    @XmlEnumValue("Non Pagato")
    NON_PAGATO("Non Pagato"),
    @XmlEnumValue("Pagato")
    PAGATO("Pagato"),
    @XmlEnumValue("Non Pagabile")
    NON_PAGABILE("Non Pagabile"),
    @XmlEnumValue("Pagamento Irregolare")
    PAGAMENTO_IRREGOLARE("Pagamento Irregolare"),
    @XmlEnumValue("Pagamento Rimborsato")
    PAGAMENTO_RIMBORSATO("Pagamento Rimborsato");
    private final String value;

    StatoPagamento(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatoPagamento fromValue(String v) {
        for (StatoPagamento c: StatoPagamento.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
