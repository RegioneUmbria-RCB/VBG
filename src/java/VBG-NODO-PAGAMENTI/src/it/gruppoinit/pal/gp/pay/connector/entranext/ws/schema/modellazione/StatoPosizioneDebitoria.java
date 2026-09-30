
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per StatoPosizioneDebitoria.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoPosizioneDebitoria"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Emesso"/&gt;
 *     &lt;enumeration value="Pagato"/&gt;
 *     &lt;enumeration value="Insoluto"/&gt;
 *     &lt;enumeration value="PagatoDifetto"/&gt;
 *     &lt;enumeration value="RatealeInCorso"/&gt;
 *     &lt;enumeration value="RatealeNonOttemperato"/&gt;
 *     &lt;enumeration value="SuperatoDaAltraPratica"/&gt;
 *     &lt;enumeration value="InviatoRuolo"/&gt;
 *     &lt;enumeration value="ChiusoDaNotaDiCredito"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "StatoPosizioneDebitoria")
@XmlEnum
public enum StatoPosizioneDebitoria {

    @XmlEnumValue("Emesso")
    EMESSO("Emesso"),
    @XmlEnumValue("Pagato")
    PAGATO("Pagato"),
    @XmlEnumValue("Insoluto")
    INSOLUTO("Insoluto"),
    @XmlEnumValue("PagatoDifetto")
    PAGATO_DIFETTO("PagatoDifetto"),
    @XmlEnumValue("RatealeInCorso")
    RATEALE_IN_CORSO("RatealeInCorso"),
    @XmlEnumValue("RatealeNonOttemperato")
    RATEALE_NON_OTTEMPERATO("RatealeNonOttemperato"),
    @XmlEnumValue("SuperatoDaAltraPratica")
    SUPERATO_DA_ALTRA_PRATICA("SuperatoDaAltraPratica"),
    @XmlEnumValue("InviatoRuolo")
    INVIATO_RUOLO("InviatoRuolo"),
    @XmlEnumValue("ChiusoDaNotaDiCredito")
    CHIUSO_DA_NOTA_DI_CREDITO("ChiusoDaNotaDiCredito");
    private final String value;

    StatoPosizioneDebitoria(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatoPosizioneDebitoria fromValue(String v) {
        for (StatoPosizioneDebitoria c: StatoPosizioneDebitoria.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
