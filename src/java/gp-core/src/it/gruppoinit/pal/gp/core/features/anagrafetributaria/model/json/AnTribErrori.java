package it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.json;

import javax.xml.bind.annotation.XmlElement;

import it.gruppoinit.pal.gp.core.domain.AtEsitoErrori;

public class AnTribErrori {

    @XmlElement(name = "id")
    private int id;
    @XmlElement(name = "intestazione")
    private String intestazione;
    @XmlElement(name = "tipologia_errore")
    private String tipologiaErrore;
    @XmlElement(name = "errore")
    private String errore;
    @XmlElement(name = "validato")
    private boolean validato;

    public int getId() {

	return id;
    }

    public void setId(int id) {

	this.id = id;
    }

    public String getIntestazione() {

	return intestazione;
    }

    public void setIntestazione(String intestazione) {

	this.intestazione = intestazione;
    }

    public String getTipologiaErrore() {

	return tipologiaErrore;
    }

    public void setTipologiaErrore(String tipologiaErrore) {

	this.tipologiaErrore = tipologiaErrore;
    }

    public String getErrore() {

	return errore;
    }

    public void setErrore(String errore) {

	this.errore = errore;
    }

    public boolean isValidato() {

	return validato;
    }

    public void setValidato(boolean validato) {

	this.validato = validato;
    }

    public static AnTribErrori fromAtEsitoErrori(AtEsitoErrori errore) {

	AnTribErrori e = new AnTribErrori();
	e.setId(errore.getId().getCodice());
	e.setErrore(errore.getDescrizioneErrore());
	e.setTipologiaErrore(errore.getTipoErrore());
	e.setValidato(errore.getFlagVerificato());
	e.setIntestazione(errore.getIntestazione());
	return e;
    }
}
