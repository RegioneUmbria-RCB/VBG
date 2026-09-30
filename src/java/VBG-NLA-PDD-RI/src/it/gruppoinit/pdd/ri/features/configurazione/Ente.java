package it.gruppoinit.pdd.ri.features.configurazione;

public class Ente {

    private String codiceCatastale;
    private String nomeFile;

    public Ente() {

    }

    public Ente(String codiceCatastale, String nomeFile) {

	this.codiceCatastale = codiceCatastale;
	this.nomeFile = nomeFile;
    }

    public String getCodiceCatastale() {

	return codiceCatastale;
    }

    public void setCodiceCatastale(String codiceCatastale) {

	this.codiceCatastale = codiceCatastale;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }
}
