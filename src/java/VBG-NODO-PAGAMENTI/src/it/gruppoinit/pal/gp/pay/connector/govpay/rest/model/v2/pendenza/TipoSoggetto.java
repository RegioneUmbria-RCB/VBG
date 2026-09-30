package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * tipologia di soggetto, se persona fisica (F) o giuridica (G)
 */
@XmlType(name = "TipoSoggetto")
@XmlEnum
public enum TipoSoggetto {

    @XmlEnumValue("G")
    G("G"),
    @XmlEnumValue("F")
    F("F");

    private String value;

    TipoSoggetto(String value) {

	this.value = value;
    }

    @Override
    public String toString() {

	return String.valueOf(value);
    }

    public static TipoSoggetto fromValue(String text) {

	for (TipoSoggetto b : TipoSoggetto.values()) {
	    if (String.valueOf(b.value).equals(text)) {
		return b;
	    }
	}
	return null;
    }
}
