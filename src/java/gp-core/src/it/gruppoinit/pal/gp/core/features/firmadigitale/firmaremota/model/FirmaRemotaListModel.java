package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model;

public class FirmaRemotaListModel {

    private int id;
    private ProviderFirmaModel provider;
    private String descrizione;
    private boolean attiva;
    private String endpoint;

    public int getId() {

	return id;
    }

    public void setId(int id) {

	this.id = id;
    }

    public ProviderFirmaModel getProvider() {

	return provider;
    }

    public void setProvider(ProviderFirmaModel provider) {

	this.provider = provider;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public boolean isAttiva() {

	return attiva;
    }

    public void setAttiva(boolean attiva) {

	this.attiva = attiva;
    }

    public String getEndpoint() {

	return endpoint;
    }

    public void setEndpoint(String endpoint) {

	this.endpoint = endpoint;
    }
}
