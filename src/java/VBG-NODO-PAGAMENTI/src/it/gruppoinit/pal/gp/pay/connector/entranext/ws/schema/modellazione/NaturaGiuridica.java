
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per NaturaGiuridica.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="NaturaGiuridica"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="NonDefinita"/&gt;
 *     &lt;enumeration value="PersonaFisica"/&gt;
 *     &lt;enumeration value="PersonaGiuridica"/&gt;
 *     &lt;enumeration value="DittaIndividuale"/&gt;
 *     &lt;enumeration value="PubblicaAmministrazione"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "NaturaGiuridica")
@XmlEnum
public enum NaturaGiuridica {

    @XmlEnumValue("NonDefinita")
    NON_DEFINITA("NonDefinita"),
    @XmlEnumValue("PersonaFisica")
    PERSONA_FISICA("PersonaFisica"),
    @XmlEnumValue("PersonaGiuridica")
    PERSONA_GIURIDICA("PersonaGiuridica"),
    @XmlEnumValue("DittaIndividuale")
    DITTA_INDIVIDUALE("DittaIndividuale"),
    @XmlEnumValue("PubblicaAmministrazione")
    PUBBLICA_AMMINISTRAZIONE("PubblicaAmministrazione");
    private final String value;

    NaturaGiuridica(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NaturaGiuridica fromValue(String v) {
        for (NaturaGiuridica c: NaturaGiuridica.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
