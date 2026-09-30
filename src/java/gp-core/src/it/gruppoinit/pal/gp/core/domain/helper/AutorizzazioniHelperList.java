package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Date;

public class AutorizzazioniHelperList {

    private Integer id;
    private String idComune;
    private String numero;
    private Date dataRilascio;
    private Date dataValidita;
    private String comune;
    private String registro;
    private String richiedente;
    private Date dataScadenza;
    private String autorizzataDa;
    private String stato;
    private String numeroistanza;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getIdComune() {

	return idComune;
    }

    public void setIdComune(String idComune) {

	this.idComune = idComune;
    }

    public String getNumero() {

	return numero;
    }

    public void setNumero(String numero) {

	this.numero = numero;
    }

    public Date getDataRilascio() {

	return dataRilascio;
    }

    public void setDataRilascio(Date dataRilascio) {

	this.dataRilascio = dataRilascio;
    }

    public Date getDataValidita() {

	return dataValidita;
    }

    public void setDataValidita(Date dataValidita) {

	this.dataValidita = dataValidita;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public String getRegistro() {

	return registro;
    }

    public void setRegistro(String registro) {

	this.registro = registro;
    }

    public String getRichiedente() {

	return richiedente;
    }

    public void setRichiedente(String richiedente) {

	this.richiedente = richiedente;
    }

    public Date getDataScadenza() {

	return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {

	this.dataScadenza = dataScadenza;
    }

    public String getAutorizzataDa() {

	return autorizzataDa;
    }

    public void setAutorizzataDa(String autorizzataDa) {

	this.autorizzataDa = autorizzataDa;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public String getNumeroistanza() {

	return numeroistanza;
    }

    public void setNumeroistanza(String numeroistanza) {

	this.numeroistanza = numeroistanza;
    }
}
