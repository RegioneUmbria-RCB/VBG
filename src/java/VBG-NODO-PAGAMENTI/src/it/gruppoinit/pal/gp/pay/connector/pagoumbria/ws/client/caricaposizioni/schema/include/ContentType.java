
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ContentType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="ContentType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Documento"/&gt;
 *     &lt;enumeration value="Ricevuta"/&gt;
 *     &lt;enumeration value="Quietanza"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ContentType")
@XmlEnum
public enum ContentType {

    @XmlEnumValue("Documento")
    DOCUMENTO("Documento"),
    @XmlEnumValue("Ricevuta")
    RICEVUTA("Ricevuta"),
    @XmlEnumValue("Quietanza")
    QUIETANZA("Quietanza");
    private final String value;

    ContentType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ContentType fromValue(String v) {
        for (ContentType c: ContentType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
