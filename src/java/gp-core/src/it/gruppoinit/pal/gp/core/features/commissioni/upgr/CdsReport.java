package it.gruppoinit.pal.gp.core.features.commissioni.upgr;

import java.util.ArrayList;
import java.util.List;

public class CdsReport {

    private List<String> errori;
    private Integer codiceCds;
    private Integer codiceIstanza;
    private String idcomune;
    private boolean errorePresente = false;

    public CdsReport(CdsDaMigrareBean cds) {

	this.idcomune = cds.getIdcomune();
	this.codiceIstanza = cds.getCodiceistanza();
	this.codiceCds = cds.getId();
    }

    public List<String> listaErrori() {

	if (errori == null) {
	    errori = new ArrayList<String>();
	}
	return errori;
    }

    public void addErrore(String errore) {

	listaErrori().add(errore);
	errorePresente = true;
    }

    public Integer getCodiceCds() {

	return codiceCds;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public boolean isErrore() {

	return this.errorePresente;
    }
}
