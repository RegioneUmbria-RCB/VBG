package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

public class ButtonComuneModel {

    private String codiceComune;
    private String comune;
    private int messaggi;
    private int informative;
    private int ricariche;

    public String getCodiceComune() {

	return codiceComune;
    }

    public void setCodiceComune(String codiceComune) {

	this.codiceComune = codiceComune;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public int getMessaggi() {

	return messaggi;
    }

    public void setMessaggi(int messaggi) {

	this.messaggi = messaggi;
    }

    public int getInformative() {

	return informative;
    }

    public void setInformative(int informative) {

	this.informative = informative;
    }

    public int getRicariche() {

	return ricariche;
    }

    public void setRicariche(int ricariche) {

	this.ricariche = ricariche;
    }
}
