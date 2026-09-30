
package it.gruppoinit.pal.gp.pay.ws.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per StatoDocumentoType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoDocumentoType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="RICHIESTO"/&gt;
 *     &lt;enumeration value="DISPONIBILE"/&gt;
 *     &lt;enumeration value="NON_DISPONIBILE"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "StatoDocumentoType")
@XmlEnum
public enum StatoDocumentoType {

    RICHIESTO,
    DISPONIBILE,
    NON_DISPONIBILE;

    public String value() {
        return name();
    }

    public static StatoDocumentoType fromValue(String v) {
        return valueOf(v);
    }

}
