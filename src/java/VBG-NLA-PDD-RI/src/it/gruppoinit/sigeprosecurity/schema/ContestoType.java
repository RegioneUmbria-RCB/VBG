
package it.gruppoinit.sigeprosecurity.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContestoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ContestoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AMM"/>
 *     &lt;enumeration value="APP"/>
 *     &lt;enumeration value="OPE"/>
 *     &lt;enumeration value="UTE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ContestoType")
@XmlEnum
public enum ContestoType {

    AMM,
    APP,
    OPE,
    UTE;

    public String value() {
        return name();
    }

    public static ContestoType fromValue(String v) {
        return valueOf(v);
    }

}
