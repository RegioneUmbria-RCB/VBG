
package it.gruppoinit.pal.gp.backoffice.schemas.messages.base;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CategorieEventiBaseType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CategorieEventiBaseType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="STC"/>
 *     &lt;enumeration value="AVVERTIMENTI"/>
 *     &lt;enumeration value="FIRMA"/>
 *     &lt;enumeration value="PROTOCOLLO"/>
 *     &lt;enumeration value="MAIL"/>
 *     &lt;enumeration value="STC-INS-ATT"/>
 *     &lt;enumeration value="STC-INS-PRA"/>
 *     &lt;enumeration value="AR-SOTTOS"/>
 *     &lt;enumeration value="AR-INVIO"/>
 *     &lt;enumeration value="AR-ALTRO"/>
 *     &lt;enumeration value="AR-ANNULLAMENTO"/>
 *     &lt;enumeration value="AR-TRASFERIMENTO"/>
 *     &lt;enumeration value="AR-SOGGETTOINVIO"/>
 *     &lt;enumeration value="AR-DATI_NLA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CategorieEventiBaseType", namespace = "http://gruppoinit.it/sigepro/schemas/messages/base")
@XmlEnum
public enum CategorieEventiBaseType {


    /**
     * Comunicazioni tramite STC
     * 
     */
    STC("STC"),

    /**
     * Avvertimenti generati da funzionalit� di business
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
     * STC: Inserimento Attivit�
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
