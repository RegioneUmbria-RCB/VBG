
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per EsitoPagamento.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="EsitoPagamento"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="PagamentoEseguito"/&gt;
 *     &lt;enumeration value="PagamentoRevocato"/&gt;
 *     &lt;enumeration value="PagamentoInAassenzaRPT"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "EsitoPagamento", namespace = "http://schemas.datacontract.org/2004/07/PlugAndPay.DigitBusNodoPA.Erogazione.QueryStack.Model")
@XmlEnum
public enum EsitoPagamento {

    @XmlEnumValue("PagamentoEseguito")
    PAGAMENTO_ESEGUITO("PagamentoEseguito"),
    @XmlEnumValue("PagamentoRevocato")
    PAGAMENTO_REVOCATO("PagamentoRevocato"),
    @XmlEnumValue("PagamentoInAassenzaRPT")
    PAGAMENTO_IN_AASSENZA_RPT("PagamentoInAassenzaRPT");
    private final String value;

    EsitoPagamento(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EsitoPagamento fromValue(String v) {
        for (EsitoPagamento c: EsitoPagamento.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
