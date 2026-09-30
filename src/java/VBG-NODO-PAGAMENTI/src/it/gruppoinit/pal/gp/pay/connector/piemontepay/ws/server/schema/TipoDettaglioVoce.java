
package it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.server.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipoDettaglioVoce.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoDettaglioVoce"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="IMPORTO_TRANSATO"/&gt;
 *     &lt;enumeration value="IMPORTO_AUTORIZZATO"/&gt;
 *     &lt;enumeration value="IMPORTO_COMMISSIONI"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TipoDettaglioVoce")
@XmlEnum
public enum TipoDettaglioVoce {

    IMPORTO_TRANSATO,
    IMPORTO_AUTORIZZATO,
    IMPORTO_COMMISSIONI;

    public String value() {
        return name();
    }

    public static TipoDettaglioVoce fromValue(String v) {
        return valueOf(v);
    }

}
