
package it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema.include;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Divisa.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="Divisa"&gt;
 *   &lt;restriction base="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max3Text"&gt;
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
