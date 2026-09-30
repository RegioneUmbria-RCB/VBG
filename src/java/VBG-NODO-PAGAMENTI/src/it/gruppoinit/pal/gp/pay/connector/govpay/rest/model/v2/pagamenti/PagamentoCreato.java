package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PagamentoCreato", propOrder = { "id", "location", "redirect", "idSession", })
public class PagamentoCreato {

    @XmlElement(name = "id")
    private String id = null;
    @XmlElement(name = "location")
    private String location = null;
    @XmlElement(name = "redirect")
    private String redirect = null;
    @XmlElement(name = "idSession")
    private String idSession = null;

    /**
     * identificativo del pagamento
     **/
    public PagamentoCreato id(String id) {

	this.id = id;
	return this;
    }

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    /**
     * Url del dettaglio del pagamento
     **/
    public PagamentoCreato location(String location) {

	this.location = location;
	return this;
    }

    public String getLocation() {

	return location;
    }

    public void setLocation(String location) {

	this.location = location;
    }

    /**
     * url a cui redirigere il navigatore per proseguire nel pagamento.
     **/
    public PagamentoCreato redirect(String redirect) {

	this.redirect = redirect;
	return this;
    }

    public String getRedirect() {

	return redirect;
    }

    public void setRedirect(String redirect) {

	this.redirect = redirect;
    }

    /**
     * identificativo della sessione di pagamento per la riconciliazione in al ritorno dal psp.
     **/
    public PagamentoCreato idSession(String idSession) {

	this.idSession = idSession;
	return this;
    }

    public String getIdSession() {

	return idSession;
    }

    public void setIdSession(String idSession) {

	this.idSession = idSession;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	PagamentoCreato pagamentoCreato = (PagamentoCreato) o;
	return Objects.equals(id, pagamentoCreato.id) && Objects.equals(location, pagamentoCreato.location)
		&& Objects.equals(redirect, pagamentoCreato.redirect) && Objects.equals(idSession, pagamentoCreato.idSession);
    }

    @Override
    public int hashCode() {

	return Objects.hash(id, location, redirect, idSession);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class PagamentoCreato {\n");
	sb.append("    id: ").append(toIndentedString(id)).append("\n");
	sb.append("    location: ").append(toIndentedString(location)).append("\n");
	sb.append("    redirect: ").append(toIndentedString(redirect)).append("\n");
	sb.append("    idSession: ").append(toIndentedString(idSession)).append("\n");
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
