
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipoOperazione.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoOperazione"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Insert"/&gt;
 *     &lt;enumeration value="UpdateStatus"/&gt;
 *     &lt;enumeration value="UpdateMassivo"/&gt;
 *     &lt;enumeration value="Replace"/&gt;
 *     &lt;enumeration value="Delete"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TipoOperazione")
@XmlEnum
public enum TipoOperazione {

    @XmlEnumValue("Insert")
    INSERT("Insert"),
    @XmlEnumValue("UpdateStatus")
    UPDATE_STATUS("UpdateStatus"),
    @XmlEnumValue("UpdateMassivo")
    UPDATE_MASSIVO("UpdateMassivo"),
    @XmlEnumValue("Replace")
    REPLACE("Replace"),
    @XmlEnumValue("Delete")
    DELETE("Delete");
    private final String value;

    TipoOperazione(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoOperazione fromValue(String v) {
        for (TipoOperazione c: TipoOperazione.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
