package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Dati necessari alla realizzazione dei pagamenti per Addebito Diretto, se previsto dal profilo del versante.
 **/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Conto", propOrder = { "iban", "bic", })
public class Conto {

    @XmlElement(name = "iban")
    private String iban = null;
    @XmlElement(name = "bic")
    private String bic = null;

    /**
     **/
    public Conto iban(String iban) {

	this.iban = iban;
	return this;
    }

    public String getIban() {

	return iban;
    }

    public void setIban(String iban) {

	this.iban = iban;
    }

    /**
     **/
    public Conto bic(String bic) {

	this.bic = bic;
	return this;
    }

    public String getBic() {

	return bic;
    }

    public void setBic(String bic) {

	this.bic = bic;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	Conto conto = (Conto) o;
	return Objects.equals(iban, conto.iban) && Objects.equals(bic, conto.bic);
    }

    @Override
    public int hashCode() {

	return Objects.hash(iban, bic);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class Conto {\n");
	sb.append("    iban: ").append(toIndentedString(iban)).append("\n");
	sb.append("    bic: ").append(toIndentedString(bic)).append("\n");
	sb.append("}");
	return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {

	if (o == null) {
	    return "null";
	}
	return o.toString().replace("\n", "\n    ");
    }
}
