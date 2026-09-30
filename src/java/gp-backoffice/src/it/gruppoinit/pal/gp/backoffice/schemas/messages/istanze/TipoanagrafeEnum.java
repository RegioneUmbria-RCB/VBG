
package it.gruppoinit.pal.gp.backoffice.schemas.messages.istanze;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoanagrafeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoanagrafeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="G"/>
 *     &lt;enumeration value="F"/>
 *     &lt;enumeration value="NON_SPECIFICATO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoanagrafeEnum", namespace = "http://gruppoinit.it/sigepro/schemas/messages/istanze")
@XmlEnum
public enum TipoanagrafeEnum {

    G,
    F,
    NON_SPECIFICATO;

    public String value() {
        return name();
    }

    public static TipoanagrafeEnum fromValue(String v) {
        return valueOf(v);
    }

}
