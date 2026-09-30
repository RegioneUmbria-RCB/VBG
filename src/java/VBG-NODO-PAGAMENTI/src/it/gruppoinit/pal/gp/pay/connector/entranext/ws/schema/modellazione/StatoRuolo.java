
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per StatoRuolo.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoRuolo"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Nessuno"/&gt;
 *     &lt;enumeration value="Tutti"/&gt;
 *     &lt;enumeration value="Creato"/&gt;
 *     &lt;enumeration value="Emesso"/&gt;
 *     &lt;enumeration value="Approvato"/&gt;
 *     &lt;enumeration value="Postalizzato"/&gt;
 *     &lt;enumeration value="Rifiutato"/&gt;
 *     &lt;enumeration value="InEmissione"/&gt;
 *     &lt;enumeration value="Importato"/&gt;
 *     &lt;enumeration value="InApprovazione"/&gt;
 *     &lt;enumeration value="InAcquisizione"/&gt;
 *     &lt;enumeration value="AttesaConfermaEmissione"/&gt;
 *     &lt;enumeration value="EmissioneInterrotta"/&gt;
 *     &lt;enumeration value="InPostalizzazione"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "StatoRuolo")
@XmlEnum
public enum StatoRuolo {

    @XmlEnumValue("Nessuno")
    NESSUNO("Nessuno"),
    @XmlEnumValue("Tutti")
    TUTTI("Tutti"),
    @XmlEnumValue("Creato")
    CREATO("Creato"),
    @XmlEnumValue("Emesso")
    EMESSO("Emesso"),
    @XmlEnumValue("Approvato")
    APPROVATO("Approvato"),
    @XmlEnumValue("Postalizzato")
    POSTALIZZATO("Postalizzato"),
    @XmlEnumValue("Rifiutato")
    RIFIUTATO("Rifiutato"),
    @XmlEnumValue("InEmissione")
    IN_EMISSIONE("InEmissione"),
    @XmlEnumValue("Importato")
    IMPORTATO("Importato"),
    @XmlEnumValue("InApprovazione")
    IN_APPROVAZIONE("InApprovazione"),
    @XmlEnumValue("InAcquisizione")
    IN_ACQUISIZIONE("InAcquisizione"),
    @XmlEnumValue("AttesaConfermaEmissione")
    ATTESA_CONFERMA_EMISSIONE("AttesaConfermaEmissione"),
    @XmlEnumValue("EmissioneInterrotta")
    EMISSIONE_INTERROTTA("EmissioneInterrotta"),
    @XmlEnumValue("InPostalizzazione")
    IN_POSTALIZZAZIONE("InPostalizzazione");
    private final String value;

    StatoRuolo(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatoRuolo fromValue(String v) {
        for (StatoRuolo c: StatoRuolo.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
