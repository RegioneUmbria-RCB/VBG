
package it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipoVoce.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoVoce"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ImportoTransato"/&gt;
 *     &lt;enumeration value="ImportoAutorizzato"/&gt;
 *     &lt;enumeration value="ImportoCommissioni"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TipoVoce")
@XmlEnum
public enum TipoVoce {

    @XmlEnumValue("ImportoTransato")
    IMPORTO_TRANSATO("ImportoTransato"),
    @XmlEnumValue("ImportoAutorizzato")
    IMPORTO_AUTORIZZATO("ImportoAutorizzato"),
    @XmlEnumValue("ImportoCommissioni")
    IMPORTO_COMMISSIONI("ImportoCommissioni");
    private final String value;

    TipoVoce(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoVoce fromValue(String v) {
        for (TipoVoce c: TipoVoce.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
