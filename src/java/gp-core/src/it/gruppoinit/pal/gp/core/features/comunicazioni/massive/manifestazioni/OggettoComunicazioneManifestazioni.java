package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni;

import it.gruppoinit.pal.gp.core.domain.Mailtipo;

public class OggettoComunicazioneManifestazioni {

    private String oggetto;
    private String corpo;

    public OggettoComunicazioneManifestazioni(String oggetto, String corpo) {

	super();
	this.oggetto = oggetto;
	this.corpo = corpo;
    }

    public String getOggetto() {

	return oggetto;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }

    public String getCorpo() {

	return corpo;
    }

    public void setCorpo(String corpo) {

	this.corpo = corpo;
    }

    public static OggettoComunicazioneManifestazioni fromMailTipo(Mailtipo m) {

	return new OggettoComunicazioneManifestazioni(m.getOggetto(), m.getCorpo());
    }
}
