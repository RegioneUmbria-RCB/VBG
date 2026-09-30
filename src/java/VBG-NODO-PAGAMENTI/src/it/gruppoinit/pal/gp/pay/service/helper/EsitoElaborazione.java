package it.gruppoinit.pal.gp.pay.service.helper;

public class EsitoElaborazione {

    private boolean esito = false;
    private String messaggio;

    public EsitoElaborazione(boolean esito, String messaggio) {

	this.esito = esito;
	this.messaggio = messaggio;
    }

    public boolean isEsito() {

	return esito;
    }

    public String getMessaggio() {

	return messaggio;
    }
}
