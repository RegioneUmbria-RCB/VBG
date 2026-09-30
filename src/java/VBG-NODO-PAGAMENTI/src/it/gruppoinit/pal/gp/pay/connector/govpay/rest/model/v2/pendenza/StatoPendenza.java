package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Stato della pendenza: * ESEGUITA: Pagata * NON_ESEGUITA: Da pagare * ESEGUITA_PARZIALE: Pagata parzialmente *
 * ANNULLATA: Annullata * SCADUTA: Scaduta
 */
@XmlType(name = "StatoPendenza")
@XmlEnum
public enum StatoPendenza {

    @XmlEnumValue("ESEGUITA")
    ESEGUITA("ESEGUITA"),
    @XmlEnumValue("NON_ESEGUITA")
    NON_ESEGUITA("NON_ESEGUITA"),
    @XmlEnumValue("ESEGUITA_PARZIALE")
    ESEGUITA_PARZIALE("ESEGUITA_PARZIALE"),
    @XmlEnumValue("ANNULLATA")
    ANNULLATA("ANNULLATA"),
    @XmlEnumValue("SCADUTA")
    SCADUTA("SCADUTA"),
    @XmlEnumValue("ANOMALA")
    ANOMALA("ANOMALA");

    private String value;

    StatoPendenza(String value) {

	this.value = value;
    }

    @Override
    public String toString() {

	return String.valueOf(this.value);
    }

    public static StatoPendenza fromValue(String text) {

	for (StatoPendenza b : StatoPendenza.values()) {
	    if (String.valueOf(b.value).equals(text)) {
		return b;
	    }
	}
	return null;
    }
}
