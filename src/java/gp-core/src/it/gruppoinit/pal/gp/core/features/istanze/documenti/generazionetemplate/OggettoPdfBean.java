package it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate;

import it.gruppoinit.pal.gp.core.domain.Oggetti;

public class OggettoPdfBean {

    private Oggetti oggetto;
    private String status;

    public OggettoPdfBean(Oggetti oggetto, String status) {

	this.setOggetto(oggetto);
	this.setStatus(status);
    }

    public Oggetti getOggetto() {

	return oggetto;
    }

    public void setOggetto(Oggetti oggetto) {

	this.oggetto = oggetto;
    }

    public String getStatus() {

	return status;
    }

    public void setStatus(String status) {

	this.status = status;
    }
}
