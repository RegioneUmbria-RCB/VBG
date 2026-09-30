
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipiDocumentiSDI.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipiDocumentiSDI"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Avviso"/&gt;
 *     &lt;enumeration value="Fattura"/&gt;
 *     &lt;enumeration value="PromemoriaDiMancatoPagamento"/&gt;
 *     &lt;enumeration value="SollecitoNonNotificato"/&gt;
 *     &lt;enumeration value="SollecitoNotificato"/&gt;
 *     &lt;enumeration value="IngiunzioneFiscale"/&gt;
 *     &lt;enumeration value="Accertamento_Liquidazione"/&gt;
 *     &lt;enumeration value="Accertamento_InfedeleDenuncia"/&gt;
 *     &lt;enumeration value="Accertamento_OmessaDenuncia"/&gt;
 *     &lt;enumeration value="Rateizzazione"/&gt;
 *     &lt;enumeration value="AccertamentoEsecutivo"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TipiDocumentiSDI")
@XmlEnum
public enum TipiDocumentiSDI {

    @XmlEnumValue("Avviso")
    AVVISO("Avviso"),
    @XmlEnumValue("Fattura")
    FATTURA("Fattura"),
    @XmlEnumValue("PromemoriaDiMancatoPagamento")
    PROMEMORIA_DI_MANCATO_PAGAMENTO("PromemoriaDiMancatoPagamento"),
    @XmlEnumValue("SollecitoNonNotificato")
    SOLLECITO_NON_NOTIFICATO("SollecitoNonNotificato"),
    @XmlEnumValue("SollecitoNotificato")
    SOLLECITO_NOTIFICATO("SollecitoNotificato"),
    @XmlEnumValue("IngiunzioneFiscale")
    INGIUNZIONE_FISCALE("IngiunzioneFiscale"),
    @XmlEnumValue("Accertamento_Liquidazione")
    ACCERTAMENTO_LIQUIDAZIONE("Accertamento_Liquidazione"),
    @XmlEnumValue("Accertamento_InfedeleDenuncia")
    ACCERTAMENTO_INFEDELE_DENUNCIA("Accertamento_InfedeleDenuncia"),
    @XmlEnumValue("Accertamento_OmessaDenuncia")
    ACCERTAMENTO_OMESSA_DENUNCIA("Accertamento_OmessaDenuncia"),
    @XmlEnumValue("Rateizzazione")
    RATEIZZAZIONE("Rateizzazione"),
    @XmlEnumValue("AccertamentoEsecutivo")
    ACCERTAMENTO_ESECUTIVO("AccertamentoEsecutivo");
    private final String value;

    TipiDocumentiSDI(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipiDocumentiSDI fromValue(String v) {
        for (TipiDocumentiSDI c: TipiDocumentiSDI.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
