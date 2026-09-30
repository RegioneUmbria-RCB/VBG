//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.13 alle 12:05:21 PM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per stOperazioneRiconciliazione.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="stOperazioneRiconciliazione">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PER_SOSPESI"/>
 *     &lt;enumeration value="PER_IDENTIFICATIVO_FLUSSO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "stOperazioneRiconciliazione")
@XmlEnum
public enum StOperazioneRiconciliazione {

    PER_SOSPESI,
    PER_IDENTIFICATIVO_FLUSSO;

    public String value() {
        return name();
    }

    public static StOperazioneRiconciliazione fromValue(String v) {
        return valueOf(v);
    }

}
