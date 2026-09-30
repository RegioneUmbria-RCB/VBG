
package it.toscana.regione.suap.sem.types.procedimento;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attoreReteSuap.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="attoreReteSuap">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FACCT"/>
 *     &lt;enumeration value="COMACCT"/>
 *     &lt;enumeration value="SUAP"/>
 *     &lt;enumeration value="ASL"/>
 *     &lt;enumeration value="GC"/>
 *     &lt;enumeration value="VVFF"/>
 *     &lt;enumeration value="REG"/>
 *     &lt;enumeration value="PROV"/>
 *     &lt;enumeration value="ED"/>
 *     &lt;enumeration value="COMM"/>
 *     &lt;enumeration value="AMB"/>
 *     &lt;enumeration value="VVUU"/>
 *     &lt;enumeration value="SBBAA"/>
 *     &lt;enumeration value="ANAS"/>
 *     &lt;enumeration value="ARPAT"/>
 *     &lt;enumeration value="ENPA"/>
 *     &lt;enumeration value="CPORTO"/>
 *     &lt;enumeration value="QUEST"/>
 *     &lt;enumeration value="MISE"/>
 *     &lt;enumeration value="MINT"/>
 *     &lt;enumeration value="PREF"/>
 *     &lt;enumeration value="AIT"/>
 *     &lt;enumeration value="EGF"/>
 *     &lt;enumeration value="CIRC"/>
 *     &lt;enumeration value="INAIL"/>
 *     &lt;enumeration value="ADM"/>
 *     &lt;enumeration value="ATO"/>
 *     &lt;enumeration value="APORTO"/>
 *     &lt;enumeration value="URB"/>
 *     &lt;enumeration value="DEM"/>
 *     &lt;enumeration value="PAMM"/>
 *     &lt;enumeration value="SOC"/>
 *     &lt;enumeration value="AMBRT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "attoreReteSuap")
@XmlEnum
public enum AttoreReteSuap {

    FACCT,
    COMACCT,
    SUAP,
    ASL,
    GC,
    VVFF,
    REG,
    PROV,
    ED,
    COMM,
    AMB,
    VVUU,
    SBBAA,
    ANAS,
    ARPAT,
    ENPA,
    CPORTO,
    QUEST,
    MISE,
    MINT,
    PREF,
    AIT,
    EGF,
    CIRC,
    INAIL,
    ADM,
    ATO,
    APORTO,
    URB,
    DEM,
    PAMM,
    SOC,
    AMBRT;

    public String value() {
        return name();
    }

    public static AttoreReteSuap fromValue(String v) {
        return valueOf(v);
    }

}
