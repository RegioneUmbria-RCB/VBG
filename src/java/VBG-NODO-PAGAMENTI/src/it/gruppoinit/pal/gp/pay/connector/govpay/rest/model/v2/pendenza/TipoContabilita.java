package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Tipologia di codifica del capitolo di bilancio
 */
@XmlType(name = "TipoContabilita")
@XmlEnum
public enum TipoContabilita {

    @XmlEnumValue("CAPITOLO")
    CAPITOLO("CAPITOLO"),
    @XmlEnumValue("SPECIALE")
    SPECIALE("SPECIALE"),
    @XmlEnumValue("SIOPE")
    SIOPE("SIOPE"),
    @XmlEnumValue("ALTRO")
    ALTRO("ALTRO");

    private String value;

    TipoContabilita(String value) {

	this.value = value;
    }

    @Override
    public String toString() {

	return String.valueOf(this.value);
    }

    public static TipoContabilita fromValue(String text) {

	for (TipoContabilita b : TipoContabilita.values()) {
	    if (String.valueOf(b.value).equals(text)) {
		return b;
	    }
	}
	return null;
    }
}
