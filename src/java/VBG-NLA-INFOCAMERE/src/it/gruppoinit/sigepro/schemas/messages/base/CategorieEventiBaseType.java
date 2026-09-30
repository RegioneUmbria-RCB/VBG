
package it.gruppoinit.sigepro.schemas.messages.base;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per CategorieEventiBaseType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="CategorieEventiBaseType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="STC"/&gt;
 *     &lt;enumeration value="AVVERTIMENTI"/&gt;
 *     &lt;enumeration value="FIRMA"/&gt;
 *     &lt;enumeration value="PROTOCOLLO"/&gt;
 *     &lt;enumeration value="MAIL"/&gt;
 *     &lt;enumeration value="STC-INS-ATT"/&gt;
 *     &lt;enumeration value="STC-INS-PRA"/&gt;
 *     &lt;enumeration value="AR-SOTTOS"/&gt;
 *     &lt;enumeration value="AR-INVIO"/&gt;
 *     &lt;enumeration value="AR-ALTRO"/&gt;
 *     &lt;enumeration value="AR-ANNULLAMENTO"/&gt;
 *     &lt;enumeration value="AR-TRASFERIMENTO"/&gt;
 *     &lt;enumeration value="AR-SOGGETTOINVIO"/&gt;
 *     &lt;enumeration value="AR-DATI_NLA"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CategorieEventiBaseType")
@XmlEnum
public enum CategorieEventiBaseType {


    /**
     * Comunicazioni tramite STC
     * 
     */
    STC("STC"),

    /**
     * Avvertimenti generati da funzionalità di business
     * 
     */
    AVVERTIMENTI("AVVERTIMENTI"),

    /**
     * Firma del documento
     * 
     */
    FIRMA("FIRMA"),

    /**
     * Protocollazione del documento
     * 
     */
    PROTOCOLLO("PROTOCOLLO"),

    /**
     * Mail
     * 
     */
    MAIL("MAIL"),

    /**
     * STC: Inserimento Attività
     * 
     */
    @XmlEnumValue("STC-INS-ATT")
    STC_INS_ATT("STC-INS-ATT"),

    /**
     * STC: Inserimento Pratica
     * 
     */
    @XmlEnumValue("STC-INS-PRA")
    STC_INS_PRA("STC-INS-PRA"),

    /**
     * Sottoscrizione
     * 
     */
    @XmlEnumValue("AR-SOTTOS")
    AR_SOTTOS("AR-SOTTOS"),

    /**
     * Invio
     * 
     */
    @XmlEnumValue("AR-INVIO")
    AR_INVIO("AR-INVIO"),

    /**
     * Altro
     * 
     */
    @XmlEnumValue("AR-ALTRO")
    AR_ALTRO("AR-ALTRO"),

    /**
     * Annullamento
     * 
     */
    @XmlEnumValue("AR-ANNULLAMENTO")
    AR_ANNULLAMENTO("AR-ANNULLAMENTO"),

    /**
     * Trasferimento della domanda ai soggetti sottoscriventi
     * 
     */
    @XmlEnumValue("AR-TRASFERIMENTO")
    AR_TRASFERIMENTO("AR-TRASFERIMENTO"),

    /**
     * Invio dell'istanza da parte di un soggetto
     * 
     */
    @XmlEnumValue("AR-SOGGETTOINVIO")
    AR_SOGGETTOINVIO("AR-SOGGETTOINVIO"),

    /**
     * Ricezione dati da parte di un nodo NLA
     * 
     */
    @XmlEnumValue("AR-DATI_NLA")
    AR_DATI_NLA("AR-DATI_NLA");
    private final String value;

    CategorieEventiBaseType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CategorieEventiBaseType fromValue(String v) {
        for (CategorieEventiBaseType c: CategorieEventiBaseType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
