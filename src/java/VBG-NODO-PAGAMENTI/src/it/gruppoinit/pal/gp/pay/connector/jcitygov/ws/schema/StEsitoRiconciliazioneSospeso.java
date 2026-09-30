//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.13 alle 12:05:21 PM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per stEsitoRiconciliazioneSospeso.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="stEsitoRiconciliazioneSospeso">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Totale"/>
 *     &lt;enumeration value="Parziale"/>
 *     &lt;enumeration value="NonPresente"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "stEsitoRiconciliazioneSospeso")
@XmlEnum
public enum StEsitoRiconciliazioneSospeso {

    @XmlEnumValue("Totale")
    TOTALE("Totale"),
    @XmlEnumValue("Parziale")
    PARZIALE("Parziale"),
    @XmlEnumValue("NonPresente")
    NON_PRESENTE("NonPresente");
    private final String value;

    StEsitoRiconciliazioneSospeso(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StEsitoRiconciliazioneSospeso fromValue(String v) {
        for (StEsitoRiconciliazioneSospeso c: StEsitoRiconciliazioneSospeso.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
