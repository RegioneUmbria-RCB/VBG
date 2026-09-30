package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dominio", propOrder = { "idDominio", "ragioneSociale", })
public class Dominio {

    @XmlElement(name = "idDominio")
    private String idDominio = null;
    @XmlElement(name = "ragioneSociale")
    private String ragioneSociale = null;

    /**
     * Codice fiscale
     **/
    public Dominio idDominio(String idDominio) {

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
     * Ragione sociale
     **/
    public Dominio ragioneSociale(String ragioneSociale) {

	this.ragioneSociale = ragioneSociale;
	return this;
    }

    public String getRagioneSociale() {

	return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {

	this.ragioneSociale = ragioneSociale;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	Dominio dominio = (Dominio) o;
	return Objects.equals(idDominio, dominio.idDominio) && Objects.equals(ragioneSociale, dominio.ragioneSociale);
    }

    @Override
    public int hashCode() {

	return Objects.hash(idDominio, ragioneSociale);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class Dominio {\n");
	sb.append("    idDominio: ").append(toIndentedString(idDominio)).append("\n");
	sb.append("    ragioneSociale: ").append(toIndentedString(ragioneSociale)).append("\n");
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
