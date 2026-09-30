package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Gets or Sets TipoPendenzaTipologia
 */
@XmlType(name = "TipoPendenzaTipologia")
@XmlEnum
public enum TipoPendenzaTipologia {

    @XmlEnumValue("spontaneo")
    SPONTANEO("spontaneo"),
    @XmlEnumValue("dovuto")
    DOVUTO("dovuto");

    private String value;

    TipoPendenzaTipologia(String value) {

	this.value = value;
    }

    @Override
    public String toString() {

	return String.valueOf(value);
    }

    public static TipoPendenzaTipologia fromValue(String text) {

	for (TipoPendenzaTipologia b : TipoPendenzaTipologia.values()) {
	    if (String.valueOf(b.value).equals(text)) {
		return b;
	    }
	}
	return null;
    }
}
