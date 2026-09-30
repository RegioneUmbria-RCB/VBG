package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.Soggetto;

@XmlRootElement(name = "profilo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Profilo", propOrder = { "nome", "domini", "tipiPendenza", "acl", "anagrafica", "identityData", })
public class Profilo {

    @XmlElement(name = "nome")
    private String nome = null;
    @XmlElement(name = "domini")
    private List<Dominio> domini = new ArrayList<>();
    @XmlElement(name = "tipiPendenza")
    private List<TipoPendenza> tipiPendenza = new ArrayList<>();
    @XmlElement(name = "acl")
    private List<Acl> acl = new ArrayList<>();
    @XmlElement(name = "anagrafica")
    private Soggetto anagrafica = null;
    @XmlElement(name = "identityData")
    private Object identityData = null;

    /**
     * Nome o principal dell'utenza
     **/
    public Profilo nome(String nome) {

	this.nome = nome;
	return this;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    /**
     * domini su cui e' abilitato ad operare
     **/
    public Profilo domini(List<Dominio> domini) {

	this.domini = domini;
	return this;
    }

    public List<Dominio> getDomini() {

	return domini;
    }

    public void setDomini(List<Dominio> domini) {

	this.domini = domini;
    }

    /**
     * tipologie di pendenza su cui e' abilitato ad operare
     **/
    public Profilo tipiPendenza(List<TipoPendenza> tipiPendenza) {

	this.tipiPendenza = tipiPendenza;
	return this;
    }

    public List<TipoPendenza> getTipiPendenza() {

	return tipiPendenza;
    }

    public void setTipiPendenza(List<TipoPendenza> tipiPendenza) {

	this.tipiPendenza = tipiPendenza;
    }

    /**
     **/
    public Profilo acl(List<Acl> acl) {

	this.acl = acl;
	return this;
    }

    public List<Acl> getAcl() {

	return acl;
    }

    public void setAcl(List<Acl> acl) {

	this.acl = acl;
    }

    /**
     **/
    public Profilo anagrafica(Soggetto anagrafica) {

	this.anagrafica = anagrafica;
	return this;
    }

    public Soggetto getAnagrafica() {

	return anagrafica;
    }

    public void setAnagrafica(Soggetto anagrafica) {

	this.anagrafica = anagrafica;
    }

    /**
     **/
    public Profilo identityData(Object identityData) {

	this.identityData = identityData;
	return this;
    }

    public Object getIdentityData() {

	return identityData;
    }

    public void setIdentityData(Object identityData) {

	this.identityData = identityData;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	Profilo profilo = (Profilo) o;
	return Objects.equals(nome, profilo.nome) && Objects.equals(domini, profilo.domini) && Objects.equals(tipiPendenza, profilo.tipiPendenza)
		&& Objects.equals(acl, profilo.acl) && Objects.equals(anagrafica, profilo.anagrafica)
		&& Objects.equals(identityData, profilo.identityData);
    }

    @Override
    public int hashCode() {

	return Objects.hash(nome, domini, tipiPendenza, acl, anagrafica, identityData);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class Profilo {\n");
	sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
	sb.append("    domini: ").append(toIndentedString(domini)).append("\n");
	sb.append("    tipiPendenza: ").append(toIndentedString(tipiPendenza)).append("\n");
	sb.append("    acl: ").append(toIndentedString(acl)).append("\n");
	sb.append("    anagrafica: ").append(toIndentedString(anagrafica)).append("\n");
	sb.append("    identityData: ").append(toIndentedString(identityData)).append("\n");
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
