package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Stato della voce di pagamento
 */
@XmlType(name = "StatoVocePendenza")
@XmlEnum
public enum StatoVocePendenza {

    @XmlEnumValue("Eseguito")
    ESEGUITO("Eseguito"),
    @XmlEnumValue("Non eseguito")
    NON_ESEGUITO("Non eseguito"),
    @XmlEnumValue("Anomalo")
    ANOMALO("Anomalo");

    private String value;

    StatoVocePendenza(String value) {

	this.value = value;
    }

    @Override
    public String toString() {

	return String.valueOf(value);
    }

    public static StatoVocePendenza fromValue(String text) {

	for (StatoVocePendenza b : StatoVocePendenza.values()) {
	    if (String.valueOf(b.value).equals(text)) {
		return b;
	    }
	}
	return null;
    }
}
