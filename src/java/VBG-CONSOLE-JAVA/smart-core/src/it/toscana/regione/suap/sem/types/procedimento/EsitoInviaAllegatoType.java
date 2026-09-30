
package it.toscana.regione.suap.sem.types.procedimento;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for esitoInviaAllegatoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="esitoInviaAllegatoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="MITTENTE_INESISTENTE"/>
 *     &lt;enumeration value="IDMSG_INESISTENTE"/>
 *     &lt;enumeration value="INCOERENTE"/>
 *     &lt;enumeration value="CONTENT_ID_INCOERENTE"/>
 *     &lt;enumeration value="HASH_ERRATO"/>
 *     &lt;enumeration value="KO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "esitoInviaAllegatoType")
@XmlEnum
public enum EsitoInviaAllegatoType {

    OK,
    MITTENTE_INESISTENTE,
    IDMSG_INESISTENTE,
    INCOERENTE,
    CONTENT_ID_INCOERENTE,
    HASH_ERRATO,
    KO;

    public String value() {
        return name();
    }

    public static EsitoInviaAllegatoType fromValue(String v) {
        return valueOf(v);
    }

}
