package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Gets or Sets TipoServizio
 */
@XmlType(name = "TipoServizio")
@XmlEnum
public enum TipoServizio {

    @XmlEnumValue("Anagrafica PagoPA")
    ANAGRAFICA_PAGOPA("Anagrafica PagoPA"),
    @XmlEnumValue("Anagrafica Creditore")
    ANAGRAFICA_CREDITORE("Anagrafica Creditore"),
    @XmlEnumValue("Anagrafica Applicazion")
    ANAGRAFICA_APPLICAZIONI("Anagrafica Applicazioni"),
    @XmlEnumValue("Anagrafica Ruoli")
    ANAGRAFICA_RUOLI("Anagrafica Ruoli"),
    @XmlEnumValue("Pagamenti")
    PAGAMENTI("Pagamenti"),
    @XmlEnumValue("Pendenze")
    PENDENZE("Pendenze"),
    @XmlEnumValue("Rendicontazioni e Incassi")
    RENDICONTAZIONI_E_INCASSI("Rendicontazioni e Incassi"),
    @XmlEnumValue("Giornale degli Eventi")
    GIORNALE_DEGLI_EVENTI("Giornale degli Eventi"),
    @XmlEnumValue("Configurazione e manutenzione")
    CONFIGURAZIONE_E_MANUTENZIONE("Configurazione e manutenzione");

    private String value;

    TipoServizio(String value) {

	this.value = value;
    }

    @Override
    public String toString() {

	return String.valueOf(value);
    }

    public static TipoServizio fromValue(String text) {

	for (TipoServizio b : TipoServizio.values()) {
	    if (String.valueOf(b.value).equals(text)) {
		return b;
	    }
	}
	return null;
    }
}
