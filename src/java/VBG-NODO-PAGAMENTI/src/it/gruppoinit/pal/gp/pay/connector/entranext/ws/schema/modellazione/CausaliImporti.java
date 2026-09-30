
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per CausaliImporti.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="CausaliImporti"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Servizi"/&gt;
 *     &lt;enumeration value="Sanzioni"/&gt;
 *     &lt;enumeration value="Spese"/&gt;
 *     &lt;enumeration value="Bollo"/&gt;
 *     &lt;enumeration value="Interessi"/&gt;
 *     &lt;enumeration value="Arrotondamento"/&gt;
 *     &lt;enumeration value="DepositiCauzionali"/&gt;
 *     &lt;enumeration value="RimborsoDepositiCauzionali"/&gt;
 *     &lt;enumeration value="RimborsoServizi"/&gt;
 *     &lt;enumeration value="SpeseTenutaConto"/&gt;
 *     &lt;enumeration value="ImpostaRegistro"/&gt;
 *     &lt;enumeration value="Commissioni"/&gt;
 *     &lt;enumeration value="InteressiPassiviCCP"/&gt;
 *     &lt;enumeration value="SpeseDomiciliazione"/&gt;
 *     &lt;enumeration value="CommissioniBolloSpeseTenutaConto"/&gt;
 *     &lt;enumeration value="CommissioniSpeseTenutaConto"/&gt;
 *     &lt;enumeration value="Urgenza"/&gt;
 *     &lt;enumeration value="SanzioniInfedele"/&gt;
 *     &lt;enumeration value="SanzioniOmessa"/&gt;
 *     &lt;enumeration value="SanzioniLiquidazione"/&gt;
 *     &lt;enumeration value="Addizionali"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CausaliImporti")
@XmlEnum
public enum CausaliImporti {

    @XmlEnumValue("Servizi")
    SERVIZI("Servizi"),
    @XmlEnumValue("Sanzioni")
    SANZIONI("Sanzioni"),
    @XmlEnumValue("Spese")
    SPESE("Spese"),
    @XmlEnumValue("Bollo")
    BOLLO("Bollo"),
    @XmlEnumValue("Interessi")
    INTERESSI("Interessi"),
    @XmlEnumValue("Arrotondamento")
    ARROTONDAMENTO("Arrotondamento"),
    @XmlEnumValue("DepositiCauzionali")
    DEPOSITI_CAUZIONALI("DepositiCauzionali"),
    @XmlEnumValue("RimborsoDepositiCauzionali")
    RIMBORSO_DEPOSITI_CAUZIONALI("RimborsoDepositiCauzionali"),
    @XmlEnumValue("RimborsoServizi")
    RIMBORSO_SERVIZI("RimborsoServizi"),
    @XmlEnumValue("SpeseTenutaConto")
    SPESE_TENUTA_CONTO("SpeseTenutaConto"),
    @XmlEnumValue("ImpostaRegistro")
    IMPOSTA_REGISTRO("ImpostaRegistro"),
    @XmlEnumValue("Commissioni")
    COMMISSIONI("Commissioni"),
    @XmlEnumValue("InteressiPassiviCCP")
    INTERESSI_PASSIVI_CCP("InteressiPassiviCCP"),
    @XmlEnumValue("SpeseDomiciliazione")
    SPESE_DOMICILIAZIONE("SpeseDomiciliazione"),
    @XmlEnumValue("CommissioniBolloSpeseTenutaConto")
    COMMISSIONI_BOLLO_SPESE_TENUTA_CONTO("CommissioniBolloSpeseTenutaConto"),
    @XmlEnumValue("CommissioniSpeseTenutaConto")
    COMMISSIONI_SPESE_TENUTA_CONTO("CommissioniSpeseTenutaConto"),
    @XmlEnumValue("Urgenza")
    URGENZA("Urgenza"),
    @XmlEnumValue("SanzioniInfedele")
    SANZIONI_INFEDELE("SanzioniInfedele"),
    @XmlEnumValue("SanzioniOmessa")
    SANZIONI_OMESSA("SanzioniOmessa"),
    @XmlEnumValue("SanzioniLiquidazione")
    SANZIONI_LIQUIDAZIONE("SanzioniLiquidazione"),
    @XmlEnumValue("Addizionali")
    ADDIZIONALI("Addizionali");
    private final String value;

    CausaliImporti(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CausaliImporti fromValue(String v) {
        for (CausaliImporti c: CausaliImporti.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
