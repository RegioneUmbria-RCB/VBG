package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

public class CreazioneBollCfgTipo {

    Integer bollCfgTipoId;
    String descrizione;

    public CreazioneBollCfgTipo(Integer bollCfgTipoId, String descrizione) {

	this.bollCfgTipoId = bollCfgTipoId;
	this.descrizione = descrizione;
    }

    public CreazioneBollCfgTipo() {

    }

    public Integer getBollCfgTipoId() {

	return bollCfgTipoId;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setBollCfgTipoId(Integer bollCfgTipoId) {

	this.bollCfgTipoId = bollCfgTipoId;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }
}
