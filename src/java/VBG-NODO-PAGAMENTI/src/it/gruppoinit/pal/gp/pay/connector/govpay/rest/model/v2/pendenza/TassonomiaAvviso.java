package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Macro categoria della pendenza Agid
 */
@XmlType(name = "TassonomiaAvviso")
@XmlEnum
public enum TassonomiaAvviso {

    @XmlEnumValue("Cartelle esattoriali")
    CARTELLE_ESATTORIALI("Cartelle esattoriali"),
    @XmlEnumValue("Diritti e concessioni")
    DIRITTI_E_CONCESSIONI("Diritti e concessioni"),
    @XmlEnumValue("Imposte e tasse")
    IMPOSTE_E_TASSE("Imposte e tasse"),
    @XmlEnumValue("IMU, TASI e altre tasse comunali")
    IMU_TASI_E_ALTRE_TASSE_COMUNALI("IMU, TASI e altre tasse comunali"),
    @XmlEnumValue("Ingressi a mostre e musei")
    INGRESSI_A_MOSTRE_E_MUSEI("Ingressi a mostre e musei"),
    @XmlEnumValue("Multe e sanzioni amministrative")
    MULTE_E_SANZIONI_AMMINISTRATIVE("Multe e sanzioni amministrative"),
    @XmlEnumValue("Previdenza e infortuni")
    PREVIDENZA_E_INFORTUNI("Previdenza e infortuni"),
    @XmlEnumValue("Servizi erogati dal comune")
    SERVIZI_EROGATI_DAL_COMUNE("Servizi erogati dal comune"),
    @XmlEnumValue("Servizi erogati da altri enti")
    SERVIZI_EROGATI_DA_ALTRI_ENTI("Servizi erogati da altri enti"),
    @XmlEnumValue("Servizi scolastici")
    SERVIZI_SCOLASTICI("Servizi scolastici"),
    @XmlEnumValue("Tassa automobilistica")
    TASSA_AUTOMOBILISTICA("Tassa automobilistica"),
    @XmlEnumValue("Ticket e prestazioni sanitarie")
    TICKET_E_PRESTAZIONI_SANITARIE("Ticket e prestazioni sanitarie"),
    @XmlEnumValue("Trasporti, mobilità e parcheggi")
    TRASPORTI_MOBILIT_E_PARCHEGGI("Trasporti, mobilità e parcheggi");

    private String value;

    TassonomiaAvviso(String value) {

	this.value = value;
    }

    @Override
    public String toString() {

	return String.valueOf(this.value);
    }

    public static TassonomiaAvviso fromValue(String text) {

	for (TassonomiaAvviso b : TassonomiaAvviso.values()) {
	    if (String.valueOf(b.value).equals(text)) {
		return b;
	    }
	}
	return null;
    }
}
