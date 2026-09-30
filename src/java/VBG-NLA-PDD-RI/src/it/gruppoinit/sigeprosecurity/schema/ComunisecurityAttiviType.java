
package it.gruppoinit.sigeprosecurity.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComunisecurityAttiviType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ComunisecurityAttiviType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="TUTTI"/>
 *     &lt;enumeration value="ATTIVI"/>
 *     &lt;enumeration value="DISATTIVATI"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
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
