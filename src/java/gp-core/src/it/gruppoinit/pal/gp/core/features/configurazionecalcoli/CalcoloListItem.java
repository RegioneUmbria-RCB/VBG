package it.gruppoinit.pal.gp.core.features.configurazionecalcoli;

import it.gruppoinit.pal.gp.core.domain.ConfigurazioneCalcoli;

public class CalcoloListItem {

    private int id;
    private String descrizione;
    private int versione;
    private String url;

    public static CalcoloListItem fromConfigurazioneCalcoli(ConfigurazioneCalcoli dettaglio) {

	if (dettaglio == null || dettaglio.getId() == null) {
	    return null;
	}
	CalcoloListItem retVal = new CalcoloListItem();
	retVal.setId(dettaglio.getId().getCodice());
	retVal.setDescrizione(dettaglio.getDescrizione());
	retVal.setVersione(dettaglio.getVersione());
	return retVal;
    }

    public int getId() {

	return id;
    }

    public void setId(int id) {

	this.id = id;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public int getVersione() {

	return versione;
    }

    public void setVersione(int versione) {

	this.versione = versione;
    }

    public String getUrl() {

	return url;
    }

    public void setUrl(String url) {

	this.url = url;
    }
}
