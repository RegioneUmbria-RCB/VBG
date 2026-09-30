
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.server.esitopagamentiattesi.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per codiceEsitoComunicazione.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="codiceEsitoComunicazione"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="SUCCESS"/&gt;
 *     &lt;enumeration value="ERROR"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "codiceEsitoComunicazione")
@XmlEnum
public enum CodiceEsitoComunicazione {

    SUCCESS,
    ERROR;

    public String value() {
        return name();
    }

    public static CodiceEsitoComunicazione fromValue(String v) {
        return valueOf(v);
    }

}
