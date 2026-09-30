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
 * <p>Classe Java per OperatorType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="OperatorType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="EXISTS"/>
 *     &lt;enumeration value="NOT_EXISTS"/>
 *     &lt;enumeration value="EQUALS"/>
 *     &lt;enumeration value="NOT_EQUALS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OperatorType", namespace = "http://gruppoinit.it/sigepro/schemas/cartmapping")
@XmlEnum
public enum OperatorType {


    /**
     * 
     * 		        	l'operatore restituisce true se la proprietà specificata in if-property è valorizzata 
     * 		        	
     * 
     */
    EXISTS,

    /**
     * 
     * 		        	l'operatore restituisce true se la proprietà specificata in if-property non è valorizzata 
     * 		        	
     * 
     */
    NOT_EXISTS,

    /**
     * 
     * 		        	l'operatore restituisce true se la proprietà specificata in if-property è uguale al valore specificato in if-value 
     * 		        	
     * 
     */
    EQUALS,

    /**
     * 
     * 		        	l'operatore restituisce true se la proprietà specificata in if-property è diversa dal valore specificato in if-value 
     * 		        	
     * 
     */
    NOT_EQUALS;

    public String value() {
        return name();
    }

    public static OperatorType fromValue(String v) {
        return valueOf(v);
    }

}
