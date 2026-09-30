
package it.gov.impresainungiorno.schema.base;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per StatoImpresa.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="StatoImpresa"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ATTIVA"/&gt;
 *     &lt;enumeration value="RIATTIVATA"/&gt;
 *     &lt;enumeration value="SOSPESA"/&gt;
 *     &lt;enumeration value="CESSATA"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "StatoImpresa")
@XmlEnum
public enum StatoImpresa {

    ATTIVA,
    RIATTIVATA,
    SOSPESA,
    CESSATA;

    public String value() {
        return name();
    }

    public static StatoImpresa fromValue(String v) {
        return valueOf(v);
    }

}
