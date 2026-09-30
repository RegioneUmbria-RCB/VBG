
package it.gruppoinit.sigeprosecurity.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AmbienteType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AmbienteType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ASP"/>
 *     &lt;enumeration value="DOTNET"/>
 *     &lt;enumeration value="JAVA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AmbienteType")
@XmlEnum
public enum AmbienteType {

    ASP,
    DOTNET,
    JAVA;

    public String value() {
        return name();
    }

    public static AmbienteType fromValue(String v) {
        return valueOf(v);
    }

}
