
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema.include;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipoPagamento.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoPagamento"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Pagamento a Rate"/&gt;
 *     &lt;enumeration value="Pagamento Unico"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TipoPagamento")
@XmlEnum
public enum TipoPagamento {

    @XmlEnumValue("Pagamento a Rate")
    PAGAMENTO_A_RATE("Pagamento a Rate"),
    @XmlEnumValue("Pagamento Unico")
    PAGAMENTO_UNICO("Pagamento Unico");
    private final String value;

    TipoPagamento(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoPagamento fromValue(String v) {
        for (TipoPagamento c: TipoPagamento.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
