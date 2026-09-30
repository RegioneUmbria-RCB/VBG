
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Divisa.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="Divisa"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="EUR"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "Divisa")
@XmlEnum
public enum Divisa {

    EUR;

    public String value() {
        return name();
    }

    public static Divisa fromValue(String v) {
        return valueOf(v);
    }

}
