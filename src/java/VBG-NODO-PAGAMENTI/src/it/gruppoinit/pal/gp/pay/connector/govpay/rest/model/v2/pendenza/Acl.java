package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Acl", propOrder = { "ruolo", "principal", "servizio", "autorizzazioni", })
public class Acl {

    @XmlElement(name = "ruolo")
    private String ruolo = null;
    @XmlElement(name = "principal")
    private String principal = null;
    @XmlElement(name = "servizio")
    private TipoServizio servizio = null;
    @XmlElement(name = "autorizzazioni")
    private List<String> autorizzazioni = null;

    /**
     * ruolo a cui si applica l'acl
     **/
    public Acl ruolo(String ruolo) {

	this.ruolo = ruolo;
	return this;
    }

    public String getRuolo() {

	return ruolo;
    }

    public void setRuolo(String ruolo) {

	this.ruolo = ruolo;
    }

    /**
     * principal a cui si applica l'acl
     **/
    public Acl principal(String principal) {

	this.principal = principal;
	return this;
    }

    public String getPrincipal() {

	return principal;
    }

    public void setPrincipal(String principal) {

	this.principal = principal;
    }

    /**
     **/
    public Acl servizi(TipoServizio servizio) {

	this.servizio = servizio;
	return this;
    }

    public TipoServizio getServizio() {

	return servizio;
    }

    public void setServizio(TipoServizio servizio) {

	this.servizio = servizio;
    }

    /**
     **/
    public Acl autorizzazioni(List<String> autorizzazioni) {

	this.autorizzazioni = autorizzazioni;
	return this;
    }

    public List<String> getAutorizzazioni() {

	return autorizzazioni;
    }

    public void setAutorizzazioni(List<String> autorizzazioni) {

	this.autorizzazioni = autorizzazioni;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	Acl acl = (Acl) o;
	return Objects.equals(ruolo, acl.ruolo) && Objects.equals(principal, acl.principal) && Objects.equals(servizio, acl.servizio)
		&& Objects.equals(autorizzazioni, acl.autorizzazioni);
    }

    @Override
    public int hashCode() {

	return Objects.hash(ruolo, principal, servizio, autorizzazioni);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class Acl {\n");
	sb.append("    ruolo: ").append(toIndentedString(ruolo)).append("\n");
	sb.append("    principal: ").append(toIndentedString(principal)).append("\n");
	sb.append("    servizi: ").append(toIndentedString(servizio)).append("\n");
	sb.append("    autorizzazioni: ").append(toIndentedString(autorizzazioni)).append("\n");
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
