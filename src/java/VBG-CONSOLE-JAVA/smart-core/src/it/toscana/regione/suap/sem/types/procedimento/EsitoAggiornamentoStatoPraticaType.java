
package it.toscana.regione.suap.sem.types.procedimento;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for esitoAggiornamentoStatoPraticaType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="esitoAggiornamentoStatoPraticaType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="INCOERENTE"/>
 *     &lt;enumeration value="MITTENTE_INESISTENTE"/>
 *     &lt;enumeration value="PRATICA_INESISTENTE"/>
 *     &lt;enumeration value="KO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "esitoAggiornamentoStatoPraticaType")
@XmlEnum
public enum EsitoAggiornamentoStatoPraticaType {

    OK,
    INCOERENTE,
    MITTENTE_INESISTENTE,
    PRATICA_INESISTENTE,
    KO;

    public String value() {
        return name();
    }

    public static EsitoAggiornamentoStatoPraticaType fromValue(String v) {
        return valueOf(v);
    }

}
