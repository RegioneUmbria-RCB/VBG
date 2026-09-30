
package it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipoAggiornamentoType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoAggiornamentoType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="MODIFICA"/&gt;
 *     &lt;enumeration value="ANNULLAMENTO"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TipoAggiornamentoType")
@XmlEnum
public enum TipoAggiornamentoType {

    MODIFICA,
    ANNULLAMENTO;

    public String value() {
        return name();
    }

    public static TipoAggiornamentoType fromValue(String v) {
        return valueOf(v);
    }

}
