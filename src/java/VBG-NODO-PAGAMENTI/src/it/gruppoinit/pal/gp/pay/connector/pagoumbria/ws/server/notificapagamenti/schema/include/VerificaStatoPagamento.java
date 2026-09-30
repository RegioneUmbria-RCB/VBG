
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema.include;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per VerificaStatoPagamento.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="VerificaStatoPagamento"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Posizione non presente"/&gt;
 *     &lt;enumeration value="Posizione non pagabile"/&gt;
 *     &lt;enumeration value="Pagamento non eseguito"/&gt;
 *     &lt;enumeration value="Pagamento eseguito"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "VerificaStatoPagamento")
@XmlEnum
public enum VerificaStatoPagamento {


    /**
     * L'identificativo fornito non riferisce una posizione debitoria registrata su IRIS
     * 
     */
    @XmlEnumValue("Posizione non presente")
    POSIZIONE_NON_PRESENTE("Posizione non presente"),

    /**
     * L'identificativo fornito riferisce una posizione debitoria non pagabile su IRIS
     * 
     */
    @XmlEnumValue("Posizione non pagabile")
    POSIZIONE_NON_PAGABILE("Posizione non pagabile"),
    @XmlEnumValue("Pagamento non eseguito")
    PAGAMENTO_NON_ESEGUITO("Pagamento non eseguito"),
    @XmlEnumValue("Pagamento eseguito")
    PAGAMENTO_ESEGUITO("Pagamento eseguito");
    private final String value;

    VerificaStatoPagamento(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VerificaStatoPagamento fromValue(String v) {
        for (VerificaStatoPagamento c: VerificaStatoPagamento.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
