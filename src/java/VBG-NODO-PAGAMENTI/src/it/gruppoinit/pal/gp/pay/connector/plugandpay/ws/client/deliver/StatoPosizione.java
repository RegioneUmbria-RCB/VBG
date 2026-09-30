
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per StatoPosizione.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoPosizione"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="NonPagata"/&gt;
 *     &lt;enumeration value="Pagata"/&gt;
 *     &lt;enumeration value="PagataParzialmente"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "StatoPosizione")
@XmlEnum
public enum StatoPosizione {

    @XmlEnumValue("NonPagata")
    NON_PAGATA("NonPagata"),
    @XmlEnumValue("Pagata")
    PAGATA("Pagata"),
    @XmlEnumValue("PagataParzialmente")
    PAGATA_PARZIALMENTE("PagataParzialmente");
    private final String value;

    StatoPosizione(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatoPosizione fromValue(String v) {
        for (StatoPosizione c: StatoPosizione.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
