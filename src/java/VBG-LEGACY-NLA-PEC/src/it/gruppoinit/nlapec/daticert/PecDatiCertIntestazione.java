package it.gruppoinit.nlapec.daticert;

import java.util.ArrayList;

public class PecDatiCertIntestazione {

    private String mittente;
    private ArrayList<PecDest> destinatari = new ArrayList<PecDest>();
    private String risposte;
    private String oggetto;

    public String getMittente() {

	return mittente;
    }

    public void setMittente(String mittente) {

	this.mittente = mittente;
    }

    public void addDestinatario(PecDest d) {

	destinatari.add(d);
    }

    public ArrayList<PecDest> getDestinatari() {

	return destinatari;
    }

    public void setDestinatari(ArrayList<PecDest> destinatari) {

	this.destinatari = destinatari;
    }

    public void setRisposte(String risposte) {

	this.risposte = risposte;
    }

    public String getRisposte() {

	return risposte;
    }

    public String getOggetto() {

	return oggetto;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }
}
