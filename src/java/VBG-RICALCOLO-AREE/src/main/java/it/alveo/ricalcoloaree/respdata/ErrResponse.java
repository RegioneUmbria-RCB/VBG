package it.alveo.ricalcoloaree.respdata;

public class ErrResponse {

    private String codice;
    private String messaggio;
    private String dettaglio;

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public String getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }

    public String getDettaglio() {

	return dettaglio;
    }

    public void setDettaglio(String dettaglio) {

	this.dettaglio = dettaglio;
    }
}
