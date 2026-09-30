
package it.init.sigepro.rte.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for XsdTypesVersion.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="XsdTypesVersion">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="V_1_0"/>
 *     &lt;enumeration value="V_1_1"/>
 *     &lt;enumeration value="V_1_2"/>
 *     &lt;enumeration value="V_1_3"/>
 *     &lt;enumeration value="V_1_4"/>
 *     &lt;enumeration value="V_1_5"/>
 *     &lt;enumeration value="V_1_6"/>
 *     &lt;enumeration value="V_1_7"/>
 *     &lt;enumeration value="V_1_8"/>
 *     &lt;enumeration value="V_1_9"/>
 *     &lt;enumeration value="V_1_10"/>
 *     &lt;enumeration value="V_1_11"/>
 *     &lt;enumeration value="V_1_12"/>
 *     &lt;enumeration value="V_1_13"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "XsdTypesVersion")
@XmlEnum
public enum XsdTypesVersion {

    V_1_0,
    V_1_1,
    V_1_2,
    V_1_3,
    V_1_4,
    V_1_5,
    V_1_6,
    V_1_7,
    V_1_8,
    V_1_9,
    V_1_10,
    V_1_11,
    V_1_12,
    V_1_13;

    public String value() {
        return name();
    }

    public static XsdTypesVersion fromValue(String v) {
        return valueOf(v);
    }

}
