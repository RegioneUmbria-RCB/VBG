package it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2;

import org.apache.commons.lang.StringUtils;

public class IstanzaLista {

    private String alias;
    private String sportello;
    private String id;
    private String numero;
    private String data;
    private String numeroProtocollo;
    private String dataProtocollo;
    private String intervento;
    private LocalizzazioneIstanza indirizzo;
    private String richiedente;
    private String azienda;
    private String ruolo;
    private String descrizioneRichiedenteRuoloAzienda;

    public String getAlias() {

	return alias;
    }

    public void setAlias(String alias) {

	this.alias = alias;
    }

    public String getSportello() {

	return sportello;
    }

    public void setSportello(String sportello) {

	this.sportello = sportello;
    }

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public String getNumero() {

	return numero;
    }

    public void setNumero(String numero) {

	this.numero = numero;
    }

    public String getData() {

	return data;
    }

    public void setData(String data) {

	this.data = data;
    }

    public String getNumeroProtocollo() {

	return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {

	this.numeroProtocollo = numeroProtocollo;
    }

    public String getDataProtocollo() {

	return dataProtocollo;
    }

    public void setDataProtocollo(String dataProtocollo) {

	this.dataProtocollo = dataProtocollo;
    }

    public String getIntervento() {

	return intervento;
    }

    public void setIntervento(String intervento) {

	this.intervento = intervento;
    }

    public LocalizzazioneIstanza getIndirizzo() {

	return indirizzo;
    }

    public void setIndirizzo(LocalizzazioneIstanza indirizzo) {

	this.indirizzo = indirizzo;
    }

    public String getRichiedente() {

	return richiedente;
    }

    public void setRichiedente(String richiedente) {

	this.richiedente = richiedente;
    }

    public String getAzienda() {

	return azienda;
    }

    public void setAzienda(String azienda) {

	this.azienda = azienda;
    }

    public String getRuolo() {

	return ruolo;
    }

    public void setRuolo(String ruolo) {

	this.ruolo = ruolo;
    }

    public String getDescrizioneRichiedenteRuoloAzienda() {

	this.descrizioneRichiedenteRuoloAzienda = "";
	if (StringUtils.isNotBlank(getRichiedente())) {
	    this.descrizioneRichiedenteRuoloAzienda = getRichiedente();
	}
	if (StringUtils.isNotBlank(getRuolo())) {
	    this.descrizioneRichiedenteRuoloAzienda += " " + getRuolo();
	}
	if (StringUtils.isNotBlank(getAzienda())) {
	    if (StringUtils.isNotBlank(getRuolo())) {
		this.descrizioneRichiedenteRuoloAzienda += " di";
	    }
	    this.descrizioneRichiedenteRuoloAzienda += " " + getAzienda();
	}
	return this.descrizioneRichiedenteRuoloAzienda;
    }

    public void setDescrizioneRichiedenteRuoloAzienda(String descrizioneRichiedenteRuoloAzienda) {

	this.descrizioneRichiedenteRuoloAzienda = descrizioneRichiedenteRuoloAzienda;
    }
}
