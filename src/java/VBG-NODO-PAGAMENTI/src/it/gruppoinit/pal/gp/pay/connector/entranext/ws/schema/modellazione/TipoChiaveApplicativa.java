
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipoChiaveApplicativa.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoChiaveApplicativa"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="IUV"/&gt;
 *     &lt;enumeration value="Gestionale"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TipoChiaveApplicativa")
@XmlEnum
public enum TipoChiaveApplicativa {

    IUV("IUV"),
    @XmlEnumValue("Gestionale")
    GESTIONALE("Gestionale");
    private final String value;

    TipoChiaveApplicativa(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoChiaveApplicativa fromValue(String v) {
        for (TipoChiaveApplicativa c: TipoChiaveApplicativa.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
