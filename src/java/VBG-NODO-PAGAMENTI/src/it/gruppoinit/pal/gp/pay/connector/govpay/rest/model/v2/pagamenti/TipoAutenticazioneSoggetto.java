package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * modalita' di autenticazione del soggetto versante
 */
@XmlType(name = "TipoAutenticazioneSoggetto")
@XmlEnum
public enum TipoAutenticazioneSoggetto {

    @XmlEnumValue("CNS")
    CNS("CNS"),
    @XmlEnumValue("USR")
    USR("USR"),
    @XmlEnumValue("OTH")
    OTH("OTH"),
    @XmlEnumValue("N/A")
    N_A("N/A");

    private String value;

    TipoAutenticazioneSoggetto(String value) {

	this.value = value;
    }

    @Override
    public String toString() {

	return String.valueOf(value);
    }

    public static TipoAutenticazioneSoggetto fromValue(String text) {

	for (TipoAutenticazioneSoggetto b : TipoAutenticazioneSoggetto.values()) {
	    if (String.valueOf(b.value).equals(text)) {
		return b;
	    }
	}
	return null;
    }
}
