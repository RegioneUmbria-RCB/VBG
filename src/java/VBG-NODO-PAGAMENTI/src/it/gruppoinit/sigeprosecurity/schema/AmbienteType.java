
package it.gruppoinit.sigeprosecurity.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AmbienteType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="AmbienteType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ASP"/&gt;
 *     &lt;enumeration value="DOTNET"/&gt;
 *     &lt;enumeration value="JAVA"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
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
