package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Indica se il pagamento deve avvenire entro o oltre la soglia di giorni indicata
 */
@XmlType(name = "TipoSogliaVincoloPagamento")
@XmlEnum
public enum TipoSogliaVincoloPagamento {

    @XmlEnumValue("ENTRO")
    ENTRO("ENTRO"),
    @XmlEnumValue("OLTRE")
    OLTRE("OLTRE");

    private String value;

    TipoSogliaVincoloPagamento(String value) {

	this.value = value;
    }

    @Override
    public String toString() {

	return String.valueOf(value);
    }

    public static TipoSogliaVincoloPagamento fromValue(String text) {

	for (TipoSogliaVincoloPagamento b : TipoSogliaVincoloPagamento.values()) {
	    if (String.valueOf(b.value).equals(text)) {
		return b;
	    }
	}
	return null;
    }
}
