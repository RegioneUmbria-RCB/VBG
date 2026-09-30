
package it.gruppoinit.sigeprosecurity.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ContestoType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="ContestoType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="AMM"/&gt;
 *     &lt;enumeration value="APP"/&gt;
 *     &lt;enumeration value="OPE"/&gt;
 *     &lt;enumeration value="UTE"/&gt;
 *     &lt;enumeration value="UTEG"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ContestoType")
@XmlEnum
public enum ContestoType {

    AMM,
    APP,
    OPE,
    UTE,
    UTEG;

    public String value() {
        return name();
    }

    public static ContestoType fromValue(String v) {
        return valueOf(v);
    }

}
