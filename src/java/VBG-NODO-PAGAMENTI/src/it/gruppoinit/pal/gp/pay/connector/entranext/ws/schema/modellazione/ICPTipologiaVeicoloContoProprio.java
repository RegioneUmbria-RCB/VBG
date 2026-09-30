
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ICP_TipologiaVeicoloContoProprio.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="ICP_TipologiaVeicoloContoProprio"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="PortataInferiore3000"/&gt;
 *     &lt;enumeration value="PortataSuperiore3000"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ICP_TipologiaVeicoloContoProprio")
@XmlEnum
public enum ICPTipologiaVeicoloContoProprio {

    @XmlEnumValue("PortataInferiore3000")
    PORTATA_INFERIORE_3000("PortataInferiore3000"),
    @XmlEnumValue("PortataSuperiore3000")
    PORTATA_SUPERIORE_3000("PortataSuperiore3000");
    private final String value;

    ICPTipologiaVeicoloContoProprio(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ICPTipologiaVeicoloContoProprio fromValue(String v) {
        for (ICPTipologiaVeicoloContoProprio c: ICPTipologiaVeicoloContoProprio.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
