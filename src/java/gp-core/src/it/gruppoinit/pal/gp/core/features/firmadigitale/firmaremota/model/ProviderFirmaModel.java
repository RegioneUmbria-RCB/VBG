package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model;

public class ProviderFirmaModel {

    private String chiave;
    private String descrizione;

    public ProviderFirmaModel(String chiave, String descrizione) {

	this.chiave = chiave;
	this.descrizione = descrizione;
    }

    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }
}
