
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EnumAnnullatoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EnumAnnullatoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="si"/>
 *     &lt;enumeration value="no"/>
 *     &lt;enumeration value="nondefinito"/>
 *     &lt;enumeration value="warning"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EnumAnnullatoType")
@XmlEnum
public enum EnumAnnullatoType {

    @XmlEnumValue("si")
    SI("si"),
    @XmlEnumValue("no")
    NO("no"),
    @XmlEnumValue("nondefinito")
    NONDEFINITO("nondefinito"),
    @XmlEnumValue("warning")
    WARNING("warning");
    private final String value;

    EnumAnnullatoType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumAnnullatoType fromValue(String v) {
        for (EnumAnnullatoType c: EnumAnnullatoType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
