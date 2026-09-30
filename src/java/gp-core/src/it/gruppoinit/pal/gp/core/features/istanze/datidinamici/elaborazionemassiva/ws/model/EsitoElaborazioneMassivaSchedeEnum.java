
package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.ws.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EsitoElaborazioneMassivaSchedeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EsitoElaborazioneMassivaSchedeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ProntaPerElaborazione"/>
 *     &lt;enumeration value="ElaborazioneInCorso"/>
 *     &lt;enumeration value="ElaborazioneCompletataConSuccesso"/>
 *     &lt;enumeration value="ElaborazioneCompletataConErrori"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EsitoElaborazioneMassivaSchedeEnum", namespace = "http://schemas.datacontract.org/2004/07/Init.SIGePro.Manager.Logic.GestioneElaborazioneMassiva.SchedeIstanza")
@XmlEnum
public enum EsitoElaborazioneMassivaSchedeEnum {

    @XmlEnumValue("ProntaPerElaborazione")
    PRONTA_PER_ELABORAZIONE("ProntaPerElaborazione"),
    @XmlEnumValue("ElaborazioneInCorso")
    ELABORAZIONE_IN_CORSO("ElaborazioneInCorso"),
    @XmlEnumValue("ElaborazioneCompletataConSuccesso")
    ELABORAZIONE_COMPLETATA_CON_SUCCESSO("ElaborazioneCompletataConSuccesso"),
    @XmlEnumValue("ElaborazioneCompletataConErrori")
    ELABORAZIONE_COMPLETATA_CON_ERRORI("ElaborazioneCompletataConErrori");
    private final String value;

    EsitoElaborazioneMassivaSchedeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EsitoElaborazioneMassivaSchedeEnum fromValue(String v) {
        for (EsitoElaborazioneMassivaSchedeEnum c: EsitoElaborazioneMassivaSchedeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
