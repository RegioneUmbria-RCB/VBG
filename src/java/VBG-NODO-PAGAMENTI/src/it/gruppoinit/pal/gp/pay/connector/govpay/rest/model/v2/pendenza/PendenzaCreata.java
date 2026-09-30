package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "PendenzaCreata", propOrder = { "idDominio", "numeroAvviso", "pdf", })
public class PendenzaCreata {

    @XmlElement(name = "idDominio")
    private String idDominio = null;
    @XmlElement(name = "numeroAvviso")
    private String numeroAvviso = null;
    @XmlElement(name = "pdf")
    private String pdf = null;

    /**
     * Identificativo del creditore dell'avviso
     **/
    public PendenzaCreata idDominio(String idDominio) {

	this.idDominio = idDominio;
	return this;
    }

    public String getIdDominio() {

	return idDominio;
    }

    public void setIdDominio(String idDominio) {

	this.idDominio = idDominio;
    }

    /**
     * Numero identificativo dell'avviso di pagamento
     **/
    public PendenzaCreata numeroAvviso(String numeroAvviso) {

	this.numeroAvviso = numeroAvviso;
	return this;
    }

    public String getNumeroAvviso() {

	return numeroAvviso;
    }

    public void setNumeroAvviso(String numeroAvviso) {

	this.numeroAvviso = numeroAvviso;
    }

    /**
     * Stampa pdf dell'avviso
     **/
    public PendenzaCreata pdf(String pdf) {

	this.pdf = pdf;
	return this;
    }

    public String getPdf() {

	return pdf;
    }

    public void setPdf(String pdf) {

	this.pdf = pdf;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	PendenzaCreata pendenzaCreata = (PendenzaCreata) o;
	return Objects.equals(idDominio, pendenzaCreata.idDominio) && Objects.equals(numeroAvviso, pendenzaCreata.numeroAvviso)
		&& Objects.equals(pdf, pendenzaCreata.pdf);
    }

    @Override
    public int hashCode() {

	return Objects.hash(idDominio, numeroAvviso, pdf);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class PendenzaCreata {\n");
	sb.append("    idDominio: ").append(toIndentedString(idDominio)).append("\n");
	sb.append("    numeroAvviso: ").append(toIndentedString(numeroAvviso)).append("\n");
	sb.append("    pdf: ").append(toIndentedString(pdf)).append("\n");
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
