
package it.init.sigepro.rte.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for segnoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="segnoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ENTRATA"/>
 *     &lt;enumeration value="USCITA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "segnoType")
@XmlEnum
public enum SegnoType {

    ENTRATA,
    USCITA;

    public String value() {
        return name();
    }

    public static SegnoType fromValue(String v) {
        return valueOf(v);
    }

}
