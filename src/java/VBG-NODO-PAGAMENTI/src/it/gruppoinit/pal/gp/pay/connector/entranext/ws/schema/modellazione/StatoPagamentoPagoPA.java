
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per StatoPagamentoPagoPA.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoPagamentoPagoPA"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="NonRichiesto"/&gt;
 *     &lt;enumeration value="InPagamento"/&gt;
 *     &lt;enumeration value="PagamentoRifiutato"/&gt;
 *     &lt;enumeration value="PagamentoAnnullato"/&gt;
 *     &lt;enumeration value="PagamentoAccettato"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "StatoPagamentoPagoPA")
@XmlEnum
public enum StatoPagamentoPagoPA {

    @XmlEnumValue("NonRichiesto")
    NON_RICHIESTO("NonRichiesto"),
    @XmlEnumValue("InPagamento")
    IN_PAGAMENTO("InPagamento"),
    @XmlEnumValue("PagamentoRifiutato")
    PAGAMENTO_RIFIUTATO("PagamentoRifiutato"),
    @XmlEnumValue("PagamentoAnnullato")
    PAGAMENTO_ANNULLATO("PagamentoAnnullato"),
    @XmlEnumValue("PagamentoAccettato")
    PAGAMENTO_ACCETTATO("PagamentoAccettato");
    private final String value;

    StatoPagamentoPagoPA(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatoPagamentoPagoPA fromValue(String v) {
        for (StatoPagamentoPagoPA c: StatoPagamentoPagoPA.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
