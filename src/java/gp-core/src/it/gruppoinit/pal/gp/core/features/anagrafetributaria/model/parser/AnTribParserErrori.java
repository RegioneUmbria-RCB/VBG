package it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser;

import java.util.ArrayList;
import java.util.List;

public class AnTribParserErrori {

    private String intestazione;
    private String tipologiaErrore;
    private String errore;
    private List<AnTribParserErroriTracciato> righeTracciato;

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

    public List<AnTribParserErroriTracciato> getRigheTracciato() {

	if (this.righeTracciato == null) {
	    this.righeTracciato = new ArrayList<AnTribParserErroriTracciato>();
	}
	return righeTracciato;
    }
}
