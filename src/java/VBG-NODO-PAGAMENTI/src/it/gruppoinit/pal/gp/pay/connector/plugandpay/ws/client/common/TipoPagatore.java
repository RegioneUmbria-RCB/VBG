
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipoPagatore.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoPagatore"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="PersonaFisica"/&gt;
 *     &lt;enumeration value="PersonaGiuridica"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TipoPagatore")
@XmlEnum
public enum TipoPagatore {

    @XmlEnumValue("PersonaFisica")
    PERSONA_FISICA("PersonaFisica"),
    @XmlEnumValue("PersonaGiuridica")
    PERSONA_GIURIDICA("PersonaGiuridica");
    private final String value;

    TipoPagatore(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoPagatore fromValue(String v) {
        for (TipoPagatore c: TipoPagatore.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
