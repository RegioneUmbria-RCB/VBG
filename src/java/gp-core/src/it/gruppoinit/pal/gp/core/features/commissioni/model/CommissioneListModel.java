package it.gruppoinit.pal.gp.core.features.commissioni.model;

import java.util.Date;

public class CommissioneListModel {

    private Integer id;
    private String numeroprotocollo;
    private String descrizione;
    private boolean aperta;
    private Date data;
    private Integer codicetipologia;
    private String tipologia;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getNumeroprotocollo() {

	return numeroprotocollo;
    }

    public void setNumeroprotocollo(String numeroprotocollo) {

	this.numeroprotocollo = numeroprotocollo;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public boolean isAperta() {

	return aperta;
    }

    public void setAperta(boolean aperta) {

	this.aperta = aperta;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public Integer getCodicetipologia() {

	return codicetipologia;
    }

    public void setCodicetipologia(Integer codicetipologia) {

	this.codicetipologia = codicetipologia;
    }

    public String getTipologia() {

	return tipologia;
    }

    public void setTipologia(String tipologia) {

	this.tipologia = tipologia;
    }
}
