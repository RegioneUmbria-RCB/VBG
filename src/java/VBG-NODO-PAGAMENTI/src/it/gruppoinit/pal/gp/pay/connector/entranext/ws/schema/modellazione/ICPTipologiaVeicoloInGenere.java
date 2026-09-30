
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ICP_TipologiaVeicoloInGenere.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="ICP_TipologiaVeicoloInGenere"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Interno"/&gt;
 *     &lt;enumeration value="Esterno"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ICP_TipologiaVeicoloInGenere")
@XmlEnum
public enum ICPTipologiaVeicoloInGenere {

    @XmlEnumValue("Interno")
    INTERNO("Interno"),
    @XmlEnumValue("Esterno")
    ESTERNO("Esterno");
    private final String value;

    ICPTipologiaVeicoloInGenere(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ICPTipologiaVeicoloInGenere fromValue(String v) {
        for (ICPTipologiaVeicoloInGenere c: ICPTipologiaVeicoloInGenere.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
