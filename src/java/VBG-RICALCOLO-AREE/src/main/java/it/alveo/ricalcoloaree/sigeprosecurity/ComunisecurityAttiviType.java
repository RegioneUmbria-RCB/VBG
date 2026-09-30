
package it.alveo.ricalcoloaree.sigeprosecurity;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * <p>Classe Java per ComunisecurityAttiviType.</p>
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.</p>
 * <pre>{@code
 * <simpleType name="ComunisecurityAttiviType">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="TUTTI"/>
 *     <enumeration value="ATTIVI"/>
 *     <enumeration value="DISATTIVATI"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
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
