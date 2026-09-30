
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipoNotifica.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoNotifica"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ESEGUITO"/&gt;
 *     &lt;enumeration value="REGOLATO"/&gt;
 *     &lt;enumeration value="INCASSO"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TipoNotifica")
@XmlEnum
public enum TipoNotifica {

    ESEGUITO,
    REGOLATO,
    INCASSO;

    public String value() {
        return name();
    }

    public static TipoNotifica fromValue(String v) {
        return valueOf(v);
    }

}
