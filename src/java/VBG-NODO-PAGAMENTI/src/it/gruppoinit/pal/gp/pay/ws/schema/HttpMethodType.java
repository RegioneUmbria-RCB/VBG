
package it.gruppoinit.pal.gp.pay.ws.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per HttpMethodType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="HttpMethodType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="POST"/&gt;
 *     &lt;enumeration value="GET"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "HttpMethodType")
@XmlEnum
public enum HttpMethodType {

    POST,
    GET;

    public String value() {
        return name();
    }

    public static HttpMethodType fromValue(String v) {
        return valueOf(v);
    }

}
