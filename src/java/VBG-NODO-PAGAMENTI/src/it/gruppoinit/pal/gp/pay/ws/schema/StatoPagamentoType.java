
package it.gruppoinit.pal.gp.pay.ws.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per StatoPagamentoType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoPagamentoType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="NON_ACQUISITO"/&gt;
 *     &lt;enumeration value="ACQUISITO"/&gt;
 *     &lt;enumeration value="TRASMESSO_A_PSP"/&gt;
 *     &lt;enumeration value="ATTIVATO_IN_PSP"/&gt;
 *     &lt;enumeration value="NOTIFICATO_DA_PSP"/&gt;
 *     &lt;enumeration value="RENDICONTATO_DA_IC"/&gt;
 *     &lt;enumeration value="ANNULLAMENTO_RICHIESTO"/&gt;
 *     &lt;enumeration value="ANNULLATO"/&gt;
 *     &lt;enumeration value="CON_ERRORE"/&gt;
 *     &lt;enumeration value="PAGATO_OFFLINE_DA_ANNULLARE"/&gt;
 *     &lt;enumeration value="PAGATO_OFFLINE_ANNULLATO"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "StatoPagamentoType")
@XmlEnum
public enum StatoPagamentoType {

    NON_ACQUISITO,
    ACQUISITO,
    TRASMESSO_A_PSP,
    ATTIVATO_IN_PSP,
    NOTIFICATO_DA_PSP,
    RENDICONTATO_DA_IC,
    ANNULLAMENTO_RICHIESTO,
    ANNULLATO,
    CON_ERRORE,
    PAGATO_OFFLINE_DA_ANNULLARE,
    PAGATO_OFFLINE_ANNULLATO;

    public String value() {
        return name();
    }

    public static StatoPagamentoType fromValue(String v) {
        return valueOf(v);
    }

}
