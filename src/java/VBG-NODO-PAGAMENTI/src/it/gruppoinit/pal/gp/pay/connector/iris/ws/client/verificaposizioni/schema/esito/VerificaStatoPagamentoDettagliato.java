
package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.esito;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per VerificaStatoPagamentoDettagliato.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="VerificaStatoPagamentoDettagliato"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="POSIZIONE_NON_PRESENTE"/&gt;
 *     &lt;enumeration value="POSIZIONE_NON_PAGATA"/&gt;
 *     &lt;enumeration value="POSIZIONE_NON_PAGABILE"/&gt;
 *     &lt;enumeration value="POSIZIONE_PAGATA"/&gt;
 *     &lt;enumeration value="POSIZIONE_PAGATA_SBF"/&gt;
 *     &lt;enumeration value="POSIZIONE_CON_PAG_IN_CORSO"/&gt;
 *     &lt;enumeration value="POSIZIONE_CON_DOC_EMESSO"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "VerificaStatoPagamentoDettagliato")
@XmlEnum
public enum VerificaStatoPagamentoDettagliato {

    POSIZIONE_NON_PRESENTE,
    POSIZIONE_NON_PAGATA,
    POSIZIONE_NON_PAGABILE,
    POSIZIONE_PAGATA,
    POSIZIONE_PAGATA_SBF,
    POSIZIONE_CON_PAG_IN_CORSO,
    POSIZIONE_CON_DOC_EMESSO;

    public String value() {
        return name();
    }

    public static VerificaStatoPagamentoDettagliato fromValue(String v) {
        return valueOf(v);
    }

}
