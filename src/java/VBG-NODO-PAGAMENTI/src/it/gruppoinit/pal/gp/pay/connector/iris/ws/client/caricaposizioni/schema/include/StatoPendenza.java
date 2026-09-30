
package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per StatoPendenza.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoPendenza"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Aperta"/&gt;
 *     &lt;enumeration value="Chiusa"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "StatoPendenza")
@XmlEnum
public enum StatoPendenza {

    @XmlEnumValue("Aperta")
    APERTA("Aperta"),
    @XmlEnumValue("Chiusa")
    CHIUSA("Chiusa");
    private final String value;

    StatoPendenza(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatoPendenza fromValue(String v) {
        for (StatoPendenza c: StatoPendenza.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
