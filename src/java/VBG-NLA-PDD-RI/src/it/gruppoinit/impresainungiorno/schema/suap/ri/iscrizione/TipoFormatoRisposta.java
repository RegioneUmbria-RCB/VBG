
package it.gruppoinit.impresainungiorno.schema.suap.ri.iscrizione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoFormatoRisposta.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoFormatoRisposta">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="XML"/>
 *     &lt;enumeration value="PDF"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoFormatoRisposta")
@XmlEnum
public enum TipoFormatoRisposta {

    XML,
    PDF;

    public String value() {
        return name();
    }

    public static TipoFormatoRisposta fromValue(String v) {
        return valueOf(v);
    }

}
