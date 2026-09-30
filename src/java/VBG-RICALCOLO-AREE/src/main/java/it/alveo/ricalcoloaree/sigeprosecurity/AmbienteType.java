
package it.alveo.ricalcoloaree.sigeprosecurity;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * <p>Classe Java per AmbienteType.</p>
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.</p>
 * <pre>{@code
 * <simpleType name="AmbienteType">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="ASP"/>
 *     <enumeration value="DOTNET"/>
 *     <enumeration value="JAVA"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
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
