
package it.gruppoinit.sigeprosecurity.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ComunisecurityAttiviType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="ComunisecurityAttiviType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="TUTTI"/&gt;
 *     &lt;enumeration value="ATTIVI"/&gt;
 *     &lt;enumeration value="DISATTIVATI"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ComunisecurityAttiviType")
@XmlEnum
public enum ComunisecurityAttiviType {

    TUTTI,
    ATTIVI,
    DISATTIVATI;

    public String value() {
        return name();
    }

    public static ComunisecurityAttiviType fromValue(String v) {
        return valueOf(v);
    }

}
