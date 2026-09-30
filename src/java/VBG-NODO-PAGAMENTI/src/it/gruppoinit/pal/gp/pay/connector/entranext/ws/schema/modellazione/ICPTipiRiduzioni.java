
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ICP_TipiRiduzioni.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="ICP_TipiRiduzioni"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="CINQUANTA"/&gt;
 *     &lt;enumeration value="CENTO"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ICP_TipiRiduzioni")
@XmlEnum
public enum ICPTipiRiduzioni {

    CINQUANTA,
    CENTO;

    public String value() {
        return name();
    }

    public static ICPTipiRiduzioni fromValue(String v) {
        return valueOf(v);
    }

}
