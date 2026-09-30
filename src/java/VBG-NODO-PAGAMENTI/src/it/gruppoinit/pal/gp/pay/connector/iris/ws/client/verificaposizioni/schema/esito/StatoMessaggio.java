
package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.verificaposizioni.schema.esito;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per StatoMessaggio.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoMessaggio"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Elaborato Correttamente"/&gt;
 *     &lt;enumeration value="Elaborato con Errori"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "StatoMessaggio")
@XmlEnum
public enum StatoMessaggio {

    @XmlEnumValue("Elaborato Correttamente")
    ELABORATO_CORRETTAMENTE("Elaborato Correttamente"),
    @XmlEnumValue("Elaborato con Errori")
    ELABORATO_CON_ERRORI("Elaborato con Errori");
    private final String value;

    StatoMessaggio(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatoMessaggio fromValue(String v) {
        for (StatoMessaggio c: StatoMessaggio.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
