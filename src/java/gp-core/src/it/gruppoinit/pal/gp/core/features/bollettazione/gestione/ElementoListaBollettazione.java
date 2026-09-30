package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.Date;

public class ElementoListaBollettazione {

    private Integer id;
    private String tipo;
    private String periodo;
    private String descrizione;
    private Date dallaData;
    private Date allaData;
    private Date dataCreazione;
    private String stato;

    public ElementoListaBollettazione() {

    }

    public Integer getId() {

	return id;
    }

    public String getTipo() {

	return tipo;
    }

    public String getPeriodo() {

	return periodo;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public Date getDallaData() {

	return dallaData;
    }

    public Date getAllaData() {

	return allaData;
    }

    public Date getDataCreazione() {

	return dataCreazione;
    }

    public String getStato() {

	return stato;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public void setTipo(String tipo) {

	this.tipo = tipo;
    }

    public void setPeriodo(String periodo) {

	this.periodo = periodo;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public void setDallaData(Date dallaData) {

	this.dallaData = dallaData;
    }

    public void setAllaData(Date allaData) {

	this.allaData = allaData;
    }

    public void setDataCreazione(Date dataCreazione) {

	this.dataCreazione = dataCreazione;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }
}
