
package it.gruppoinit.pal.gp.pay.ws.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipoDocumentoType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoDocumentoType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="FATTURA"/&gt;
 *     &lt;enumeration value="RICEVUTA"/&gt;
 *     &lt;enumeration value="AVVISO"/&gt;
 *     &lt;enumeration value="RICEVUTA_XML"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TipoDocumentoType")
@XmlEnum
public enum TipoDocumentoType {

    FATTURA,
    RICEVUTA,
    AVVISO,
    RICEVUTA_XML;

    public String value() {
        return name();
    }

    public static TipoDocumentoType fromValue(String v) {
        return valueOf(v);
    }

}
