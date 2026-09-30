
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipiPagamenti.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipiPagamenti"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Versamento"/&gt;
 *     &lt;enumeration value="PartitaDiGiroFruizioneDaBorsellinoElettronico"/&gt;
 *     &lt;enumeration value="RicaricaDaContribuente"/&gt;
 *     &lt;enumeration value="RicaricaDaEccedenza"/&gt;
 *     &lt;enumeration value="RicaricaDaSgravio"/&gt;
 *     &lt;enumeration value="PartitaDiGiroFruizioneDaCompensazione"/&gt;
 *     &lt;enumeration value="PartitaDiGiroRestituzioneDepositiCauzionali"/&gt;
 *     &lt;enumeration value="PartitaDiGiroFruizioneFittiziaBuoniPastiPregressi"/&gt;
 *     &lt;enumeration value="Spese"/&gt;
 *     &lt;enumeration value="GiaRendicontatoDaAltriSoftware"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TipiPagamenti")
@XmlEnum
public enum TipiPagamenti {

    @XmlEnumValue("Versamento")
    VERSAMENTO("Versamento"),
    @XmlEnumValue("PartitaDiGiroFruizioneDaBorsellinoElettronico")
    PARTITA_DI_GIRO_FRUIZIONE_DA_BORSELLINO_ELETTRONICO("PartitaDiGiroFruizioneDaBorsellinoElettronico"),
    @XmlEnumValue("RicaricaDaContribuente")
    RICARICA_DA_CONTRIBUENTE("RicaricaDaContribuente"),
    @XmlEnumValue("RicaricaDaEccedenza")
    RICARICA_DA_ECCEDENZA("RicaricaDaEccedenza"),
    @XmlEnumValue("RicaricaDaSgravio")
    RICARICA_DA_SGRAVIO("RicaricaDaSgravio"),
    @XmlEnumValue("PartitaDiGiroFruizioneDaCompensazione")
    PARTITA_DI_GIRO_FRUIZIONE_DA_COMPENSAZIONE("PartitaDiGiroFruizioneDaCompensazione"),
    @XmlEnumValue("PartitaDiGiroRestituzioneDepositiCauzionali")
    PARTITA_DI_GIRO_RESTITUZIONE_DEPOSITI_CAUZIONALI("PartitaDiGiroRestituzioneDepositiCauzionali"),
    @XmlEnumValue("PartitaDiGiroFruizioneFittiziaBuoniPastiPregressi")
    PARTITA_DI_GIRO_FRUIZIONE_FITTIZIA_BUONI_PASTI_PREGRESSI("PartitaDiGiroFruizioneFittiziaBuoniPastiPregressi"),
    @XmlEnumValue("Spese")
    SPESE("Spese"),
    @XmlEnumValue("GiaRendicontatoDaAltriSoftware")
    GIA_RENDICONTATO_DA_ALTRI_SOFTWARE("GiaRendicontatoDaAltriSoftware");
    private final String value;

    TipiPagamenti(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipiPagamenti fromValue(String v) {
        for (TipiPagamenti c: TipiPagamenti.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
