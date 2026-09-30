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
 * <p>Classe Java per stCodiceMotivoEliminazione.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="stCodiceMotivoEliminazione">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SOSTITUZIONE"/>
 *     &lt;enumeration value="PAGAMENTO_ESTERNO"/>
 *     &lt;enumeration value="ELIMINAZIONE_DEBITO"/>
 *     &lt;enumeration value="ALTRO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "stCodiceMotivoEliminazione")
@XmlEnum
public enum StCodiceMotivoEliminazione {

    SOSTITUZIONE,
    PAGAMENTO_ESTERNO,
    ELIMINAZIONE_DEBITO,
    ALTRO;

    public String value() {
        return name();
    }

    public static StCodiceMotivoEliminazione fromValue(String v) {
        return valueOf(v);
    }

}
