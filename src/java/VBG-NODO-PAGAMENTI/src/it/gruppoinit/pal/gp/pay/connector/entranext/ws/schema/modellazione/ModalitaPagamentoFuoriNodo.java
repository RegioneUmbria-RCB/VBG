
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ModalitaPagamentoFuoriNodo.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="ModalitaPagamentoFuoriNodo"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Nessuna"/&gt;
 *     &lt;enumeration value="CassaContanti"/&gt;
 *     &lt;enumeration value="CassaBancomat"/&gt;
 *     &lt;enumeration value="CassaCartaDiCredito"/&gt;
 *     &lt;enumeration value="Prepagato"/&gt;
 *     &lt;enumeration value="POS"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ModalitaPagamentoFuoriNodo")
@XmlEnum
public enum ModalitaPagamentoFuoriNodo {

    @XmlEnumValue("Nessuna")
    NESSUNA("Nessuna"),
    @XmlEnumValue("CassaContanti")
    CASSA_CONTANTI("CassaContanti"),
    @XmlEnumValue("CassaBancomat")
    CASSA_BANCOMAT("CassaBancomat"),
    @XmlEnumValue("CassaCartaDiCredito")
    CASSA_CARTA_DI_CREDITO("CassaCartaDiCredito"),
    @XmlEnumValue("Prepagato")
    PREPAGATO("Prepagato"),
    POS("POS");
    private final String value;

    ModalitaPagamentoFuoriNodo(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ModalitaPagamentoFuoriNodo fromValue(String v) {
        for (ModalitaPagamentoFuoriNodo c: ModalitaPagamentoFuoriNodo.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
