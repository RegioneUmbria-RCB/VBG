
package it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipoRichiestaType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoRichiestaType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="INSERISCI_LISTA_DI_CARICO"/&gt;
 *     &lt;enumeration value="AGGIORNA_POSIZIONI_DEBITORIE"/&gt;
 *     &lt;enumeration value="TRASMETTI_NOTIFICHE_PAGAMENTO"/&gt;
 *     &lt;enumeration value="TRASMETTI_AVVISI_SCADUTI"/&gt;
 *     &lt;enumeration value="TRASMETTI_FLUSSO_RENDICONTAZIONE"/&gt;
 *     &lt;enumeration value="TRASMETTI_FLUSSO_RENDICONTAZIONE_ESTESO"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TipoRichiestaType")
@XmlEnum
public enum TipoRichiestaType {

    INSERISCI_LISTA_DI_CARICO,
    AGGIORNA_POSIZIONI_DEBITORIE,
    TRASMETTI_NOTIFICHE_PAGAMENTO,
    TRASMETTI_AVVISI_SCADUTI,
    TRASMETTI_FLUSSO_RENDICONTAZIONE,
    TRASMETTI_FLUSSO_RENDICONTAZIONE_ESTESO;

    public String value() {
        return name();
    }

    public static TipoRichiestaType fromValue(String v) {
        return valueOf(v);
    }

}
