
package it.toscana.regione.suap.sem.types.procedimento;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for erroreAllegatoRichiestoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="erroreAllegatoRichiestoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MITTENTE_INESISTENTE"/>
 *     &lt;enumeration value="IDMSG_INESISTENTE"/>
 *     &lt;enumeration value="CONTENT_ID_INESISTENTE"/>
 *     &lt;enumeration value="INCOERENTE"/>
 *     &lt;enumeration value="KO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "erroreAllegatoRichiestoType")
@XmlEnum
public enum ErroreAllegatoRichiestoType {

    MITTENTE_INESISTENTE,
    IDMSG_INESISTENTE,
    CONTENT_ID_INESISTENTE,
    INCOERENTE,
    KO;

    public String value() {
        return name();
    }

    public static ErroreAllegatoRichiestoType fromValue(String v) {
        return valueOf(v);
    }

}
