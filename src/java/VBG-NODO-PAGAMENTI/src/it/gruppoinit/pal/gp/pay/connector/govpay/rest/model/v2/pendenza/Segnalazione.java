package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Segnalazione", propOrder = { "data", "codice", "descrizione", "dettaglio", })
public class Segnalazione {

    @XmlElement(name = "data")
    private XMLGregorianCalendar data = null;
    @XmlElement(name = "codice")
    private String codice = null;
    @XmlElement(name = "descrizione")
    private String descrizione = null;
    @XmlElement(name = "dettaglio")
    private String dettaglio = null;

    /**
     **/
    public Segnalazione data(XMLGregorianCalendar data) {

	this.data = data;
	return this;
    }

    public XMLGregorianCalendar getData() {

	return data;
    }

    public void setData(XMLGregorianCalendar data) {

	this.data = data;
    }

    /**
     **/
    public Segnalazione codice(String codice) {

	this.codice = codice;
	return this;
    }

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    /**
     **/
    public Segnalazione descrizione(String descrizione) {

	this.descrizione = descrizione;
	return this;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    /**
     **/
    public Segnalazione dettaglio(String dettaglio) {

	this.dettaglio = dettaglio;
	return this;
    }

    public String getDettaglio() {

	return dettaglio;
    }

    public void setDettaglio(String dettaglio) {

	this.dettaglio = dettaglio;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	Segnalazione segnalazione = (Segnalazione) o;
	return Objects.equals(data, segnalazione.data) && Objects.equals(codice, segnalazione.codice)
		&& Objects.equals(descrizione, segnalazione.descrizione) && Objects.equals(dettaglio, segnalazione.dettaglio);
    }

    @Override
    public int hashCode() {

	return Objects.hash(data, codice, descrizione, dettaglio);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class Segnalazione {\n");
	sb.append("    data: ").append(toIndentedString(data)).append("\n");
	sb.append("    codice: ").append(toIndentedString(codice)).append("\n");
	sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
	sb.append("    dettaglio: ").append(toIndentedString(dettaglio)).append("\n");
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
