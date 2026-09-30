package it.gruppoinit.pal.gp.pay.connector.openweb.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "tipoPersona", //
	"nome", //
	"cognome", //
	"cf", //
	"via", //
	"civico", //
	"comune", //
	"cap", //
	"prov", //
	"nazione", //
	"email" //
})
public class SoggettoPagatore {

    @XmlElement(name = "tipo_persona")
    private String tipoPersona; // enum  F G
    @XmlElement(name = "nome")
    private String nome;
    @XmlElement(name = "cognome")
    private String cognome;
    @XmlElement(name = "cf")
    private String cf;
    @XmlElement(name = "via")
    private String via;
    @XmlElement(name = "civico")
    private String civico;
    @XmlElement(name = "comune")
    private String comune;
    @XmlElement(name = "cap")
    private String cap;
    @XmlElement(name = "prov")
    private String prov;
    @XmlElement(name = "nazione")
    private String nazione;
    @XmlElement(name = "email")
    private String email;

    public String getTipoPersona() {

	return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {

	this.tipoPersona = tipoPersona;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getCognome() {

	return cognome;
    }

    public void setCognome(String cognome) {

	this.cognome = cognome;
    }

    public String getCf() {

	return cf;
    }

    public void setCf(String cf) {

	this.cf = cf;
    }

    public String getVia() {

	return via;
    }

    public void setVia(String via) {

	this.via = via;
    }

    public String getCivico() {

	return civico;
    }

    public void setCivico(String civico) {

	this.civico = civico;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public String getCap() {

	return cap;
    }

    public void setCap(String cap) {

	this.cap = cap;
    }

    public String getProv() {

	return prov;
    }

    public void setProv(String prov) {

	this.prov = prov;
    }

    public String getNazione() {

	return nazione;
    }

    public void setNazione(String nazione) {

	this.nazione = nazione;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
