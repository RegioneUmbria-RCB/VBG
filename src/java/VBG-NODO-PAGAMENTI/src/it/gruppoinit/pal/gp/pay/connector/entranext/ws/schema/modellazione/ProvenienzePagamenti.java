
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ProvenienzePagamenti.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="ProvenienzePagamenti"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="RiscossioneDiretta"/&gt;
 *     &lt;enumeration value="Posta"/&gt;
 *     &lt;enumeration value="Banca"/&gt;
 *     &lt;enumeration value="Web"/&gt;
 *     &lt;enumeration value="Prepagato"/&gt;
 *     &lt;enumeration value="AltriCircuiti"/&gt;
 *     &lt;enumeration value="Esenzione"/&gt;
 *     &lt;enumeration value="F24"/&gt;
 *     &lt;enumeration value="Tesoreria"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProvenienzePagamenti")
@XmlEnum
public enum ProvenienzePagamenti {

    @XmlEnumValue("RiscossioneDiretta")
    RISCOSSIONE_DIRETTA("RiscossioneDiretta"),
    @XmlEnumValue("Posta")
    POSTA("Posta"),
    @XmlEnumValue("Banca")
    BANCA("Banca"),
    @XmlEnumValue("Web")
    WEB("Web"),
    @XmlEnumValue("Prepagato")
    PREPAGATO("Prepagato"),
    @XmlEnumValue("AltriCircuiti")
    ALTRI_CIRCUITI("AltriCircuiti"),
    @XmlEnumValue("Esenzione")
    ESENZIONE("Esenzione"),
    @XmlEnumValue("F24")
    F_24("F24"),
    @XmlEnumValue("Tesoreria")
    TESORERIA("Tesoreria");
    private final String value;

    ProvenienzePagamenti(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProvenienzePagamenti fromValue(String v) {
        for (ProvenienzePagamenti c: ProvenienzePagamenti.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
