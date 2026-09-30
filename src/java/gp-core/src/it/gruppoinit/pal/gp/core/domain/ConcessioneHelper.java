package it.gruppoinit.pal.gp.core.domain;

public class ConcessioneHelper {

    private String descrizioneCompleta;
    private MercatiUso mercatoUso;
    private Autorizzazioni autorizzazione;

    public ConcessioneHelper(String descrizioneCompleta, MercatiUso mercatoUso, Autorizzazioni autorizzazione) {

	super();
	this.descrizioneCompleta = descrizioneCompleta;
	this.mercatoUso = mercatoUso;
	this.autorizzazione = autorizzazione;
    }

    public String getDescrizioneCompleta() {

	return descrizioneCompleta;
    }

    public MercatiUso getMercatoUso() {

	return mercatoUso;
    }

    public Autorizzazioni getAutorizzazione() {

	return autorizzazione;
    }
}
