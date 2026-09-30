
package it.gruppoinit.impresainungiorno.schema.base.v_1_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatoImpresa.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoImpresa">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ATTIVA"/>
 *     &lt;enumeration value="RIATTIVATA"/>
 *     &lt;enumeration value="SOSPESA"/>
 *     &lt;enumeration value="CESSATA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StatoImpresa")
@XmlEnum
public enum StatoImpresa {

    ATTIVA,
    RIATTIVATA,
    SOSPESA,
    CESSATA;

    public String value() {
        return name();
    }

    public static StatoImpresa fromValue(String v) {
        return valueOf(v);
    }

}
