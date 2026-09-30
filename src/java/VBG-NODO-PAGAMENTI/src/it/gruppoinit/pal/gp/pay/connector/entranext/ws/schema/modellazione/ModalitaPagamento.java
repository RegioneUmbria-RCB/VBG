
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ModalitaPagamento.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="ModalitaPagamento"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="CSCT"/&gt;
 *     &lt;enumeration value="CSBA"/&gt;
 *     &lt;enumeration value="CSCC"/&gt;
 *     &lt;enumeration value="PREP"/&gt;
 *     &lt;enumeration value="ONCC"/&gt;
 *     &lt;enumeration value="PT123"/&gt;
 *     &lt;enumeration value="PT451"/&gt;
 *     &lt;enumeration value="PT674"/&gt;
 *     &lt;enumeration value="PTBD"/&gt;
 *     &lt;enumeration value="PTBT"/&gt;
 *     &lt;enumeration value="PTBI"/&gt;
 *     &lt;enumeration value="PTBE"/&gt;
 *     &lt;enumeration value="BABO"/&gt;
 *     &lt;enumeration value="BAMA"/&gt;
 *     &lt;enumeration value="BARI"/&gt;
 *     &lt;enumeration value="LOTT"/&gt;
 *     &lt;enumeration value="COOP"/&gt;
 *     &lt;enumeration value="ESNT"/&gt;
 *     &lt;enumeration value="PTRI"/&gt;
 *     &lt;enumeration value="TESO"/&gt;
 *     &lt;enumeration value="SISL"/&gt;
 *     &lt;enumeration value="PAYPAL"/&gt;
 *     &lt;enumeration value="BAFR"/&gt;
 *     &lt;enumeration value="BAPO"/&gt;
 *     &lt;enumeration value="F24"/&gt;
 *     &lt;enumeration value="PPA1"/&gt;
 *     &lt;enumeration value="PPA3"/&gt;
 *     &lt;enumeration value="PPA1B"/&gt;
 *     &lt;enumeration value="PPA3B"/&gt;
 *     &lt;enumeration value="PRVI"/&gt;
 *     &lt;enumeration value="CVIS"/&gt;
 *     &lt;enumeration value="CMSC"/&gt;
 *     &lt;enumeration value="CMST"/&gt;
 *     &lt;enumeration value="PosPagoPA"/&gt;
 *     &lt;enumeration value="MNTM"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ModalitaPagamento")
@XmlEnum
public enum ModalitaPagamento {

    CSCT("CSCT"),
    CSBA("CSBA"),
    CSCC("CSCC"),
    PREP("PREP"),
    ONCC("ONCC"),
    @XmlEnumValue("PT123")
    PT_123("PT123"),
    @XmlEnumValue("PT451")
    PT_451("PT451"),
    @XmlEnumValue("PT674")
    PT_674("PT674"),
    PTBD("PTBD"),
    PTBT("PTBT"),
    PTBI("PTBI"),
    PTBE("PTBE"),
    BABO("BABO"),
    BAMA("BAMA"),
    BARI("BARI"),
    LOTT("LOTT"),
    COOP("COOP"),
    ESNT("ESNT"),
    PTRI("PTRI"),
    TESO("TESO"),
    SISL("SISL"),
    PAYPAL("PAYPAL"),
    BAFR("BAFR"),
    BAPO("BAPO"),
    @XmlEnumValue("F24")
    F_24("F24"),
    @XmlEnumValue("PPA1")
    PPA_1("PPA1"),
    @XmlEnumValue("PPA3")
    PPA_3("PPA3"),
    @XmlEnumValue("PPA1B")
    PPA_1_B("PPA1B"),
    @XmlEnumValue("PPA3B")
    PPA_3_B("PPA3B"),
    PRVI("PRVI"),
    CVIS("CVIS"),
    CMSC("CMSC"),
    CMST("CMST"),
    @XmlEnumValue("PosPagoPA")
    POS_PAGO_PA("PosPagoPA"),
    MNTM("MNTM");
    private final String value;

    ModalitaPagamento(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ModalitaPagamento fromValue(String v) {
        for (ModalitaPagamento c: ModalitaPagamento.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
