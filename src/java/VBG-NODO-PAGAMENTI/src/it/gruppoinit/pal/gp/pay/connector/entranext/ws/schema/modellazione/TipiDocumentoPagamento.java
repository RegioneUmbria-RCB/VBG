
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TipiDocumentoPagamento.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="TipiDocumentoPagamento"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Nessuno"/&gt;
 *     &lt;enumeration value="BollettinoPostale"/&gt;
 *     &lt;enumeration value="MAVSenzaStampa"/&gt;
 *     &lt;enumeration value="MAVConStampa"/&gt;
 *     &lt;enumeration value="RID"/&gt;
 *     &lt;enumeration value="Quietanza"/&gt;
 *     &lt;enumeration value="F24"/&gt;
 *     &lt;enumeration value="BollettinoPostalePA"/&gt;
 *     &lt;enumeration value="AvvisoAnalogicoPagoPA"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TipiDocumentoPagamento")
@XmlEnum
public enum TipiDocumentoPagamento {

    @XmlEnumValue("Nessuno")
    NESSUNO("Nessuno"),
    @XmlEnumValue("BollettinoPostale")
    BOLLETTINO_POSTALE("BollettinoPostale"),
    @XmlEnumValue("MAVSenzaStampa")
    MAV_SENZA_STAMPA("MAVSenzaStampa"),
    @XmlEnumValue("MAVConStampa")
    MAV_CON_STAMPA("MAVConStampa"),
    RID("RID"),
    @XmlEnumValue("Quietanza")
    QUIETANZA("Quietanza"),
    @XmlEnumValue("F24")
    F_24("F24"),
    @XmlEnumValue("BollettinoPostalePA")
    BOLLETTINO_POSTALE_PA("BollettinoPostalePA"),
    @XmlEnumValue("AvvisoAnalogicoPagoPA")
    AVVISO_ANALOGICO_PAGO_PA("AvvisoAnalogicoPagoPA");
    private final String value;

    TipiDocumentoPagamento(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipiDocumentoPagamento fromValue(String v) {
        for (TipiDocumentoPagamento c: TipiDocumentoPagamento.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
