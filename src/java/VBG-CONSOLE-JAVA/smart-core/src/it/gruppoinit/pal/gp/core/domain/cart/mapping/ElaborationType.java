//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.07.28 alle 03:27:39 PM CEST 
//


package it.gruppoinit.pal.gp.core.domain.cart.mapping;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ElaborationType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="ElaborationType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="APPEND"/>
 *     &lt;enumeration value="GET_FIRST"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ElaborationType", namespace = "http://gruppoinit.it/sigepro/schemas/cartmapping")
@XmlEnum
public enum ElaborationType {

    APPEND,
    GET_FIRST;

    public String value() {
        return name();
    }

    public static ElaborationType fromValue(String v) {
        return valueOf(v);
    }

}
