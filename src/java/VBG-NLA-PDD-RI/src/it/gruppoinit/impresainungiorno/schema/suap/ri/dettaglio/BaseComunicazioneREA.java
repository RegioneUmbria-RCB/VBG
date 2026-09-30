package it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio;

public class BaseComunicazioneREA {

    private IComunicazioneREA comunicazioneREA;
    private IComunicazioneREA comunicazioneREAStd;

    public ComunicazioneREA getComunicazioneREA() {

	return (ComunicazioneREA) comunicazioneREA;
    }

    public void setComunicazioneREA(IComunicazioneREA comunicazioneREA) {

	this.comunicazioneREA = comunicazioneREA;
    }

    public ComunicazioneREAStd getComunicazioneREAStd() {

	return (ComunicazioneREAStd)comunicazioneREAStd;
    }

    public void setComunicazioneREAStd(IComunicazioneREA comunicazioneREAStd) {

	this.comunicazioneREAStd = comunicazioneREAStd;
    }
}
