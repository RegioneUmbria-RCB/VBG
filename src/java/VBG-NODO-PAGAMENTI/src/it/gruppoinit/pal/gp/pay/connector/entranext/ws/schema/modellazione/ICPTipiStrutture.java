
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ICP_TipiStrutture.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="ICP_TipiStrutture"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Monofacciale"/&gt;
 *     &lt;enumeration value="Bifacciale"/&gt;
 *     &lt;enumeration value="Polifacciale"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ICP_TipiStrutture")
@XmlEnum
public enum ICPTipiStrutture {

    @XmlEnumValue("Monofacciale")
    MONOFACCIALE("Monofacciale"),
    @XmlEnumValue("Bifacciale")
    BIFACCIALE("Bifacciale"),
    @XmlEnumValue("Polifacciale")
    POLIFACCIALE("Polifacciale");
    private final String value;

    ICPTipiStrutture(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ICPTipiStrutture fromValue(String v) {
        for (ICPTipiStrutture c: ICPTipiStrutture.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
