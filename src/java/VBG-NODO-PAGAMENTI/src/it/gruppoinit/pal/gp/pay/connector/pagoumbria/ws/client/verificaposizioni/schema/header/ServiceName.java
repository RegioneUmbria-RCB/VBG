
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ServiceName.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="ServiceName"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="IdpAllineamentoPendenze"/&gt;
 *     &lt;enumeration value="IdpInformativaPagamento"/&gt;
 *     &lt;enumeration value="IdpRendicontazioneEnti"/&gt;
 *     &lt;enumeration value="IdpConfigurazioneEnte"/&gt;
 *     &lt;enumeration value="IdpAutorizzazioneDiPagamento"/&gt;
 *     &lt;enumeration value="IdpEstrattoContoDebitorio"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ServiceName")
@XmlEnum
public enum ServiceName {

    @XmlEnumValue("IdpAllineamentoPendenze")
    IDP_ALLINEAMENTO_PENDENZE("IdpAllineamentoPendenze"),
    @XmlEnumValue("IdpInformativaPagamento")
    IDP_INFORMATIVA_PAGAMENTO("IdpInformativaPagamento"),
    @XmlEnumValue("IdpRendicontazioneEnti")
    IDP_RENDICONTAZIONE_ENTI("IdpRendicontazioneEnti"),
    @XmlEnumValue("IdpConfigurazioneEnte")
    IDP_CONFIGURAZIONE_ENTE("IdpConfigurazioneEnte"),
    @XmlEnumValue("IdpAutorizzazioneDiPagamento")
    IDP_AUTORIZZAZIONE_DI_PAGAMENTO("IdpAutorizzazioneDiPagamento"),
    @XmlEnumValue("IdpEstrattoContoDebitorio")
    IDP_ESTRATTO_CONTO_DEBITORIO("IdpEstrattoContoDebitorio");
    private final String value;

    ServiceName(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ServiceName fromValue(String v) {
        for (ServiceName c: ServiceName.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
