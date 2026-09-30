
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per IVA.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="IVA"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="IVA_22"/&gt;
 *     &lt;enumeration value="IVA_10"/&gt;
 *     &lt;enumeration value="IVA_4"/&gt;
 *     &lt;enumeration value="E01"/&gt;
 *     &lt;enumeration value="E02"/&gt;
 *     &lt;enumeration value="E03"/&gt;
 *     &lt;enumeration value="E04"/&gt;
 *     &lt;enumeration value="E05"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "IVA")
@XmlEnum
public enum IVA {

    IVA_22("IVA_22"),
    IVA_10("IVA_10"),
    IVA_4("IVA_4"),
    @XmlEnumValue("E01")
    E_01("E01"),
    @XmlEnumValue("E02")
    E_02("E02"),
    @XmlEnumValue("E03")
    E_03("E03"),
    @XmlEnumValue("E04")
    E_04("E04"),
    @XmlEnumValue("E05")
    E_05("E05");
    private final String value;

    IVA(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static IVA fromValue(String v) {
        for (IVA c: IVA.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
