package it.gruppoinit.pal.gp.core.features.esportazioni.model;

import it.gruppoinit.pal.gp.core.domain.Responsabili;

public class EsportazioniPentahoResponsabili {

    private Integer codice;
    private String responsabile;

    protected EsportazioniPentahoResponsabili() {

	super();
    }

    public EsportazioniPentahoResponsabili(Integer codice, String responsabile) {

	this();
	this.codice = codice;
	this.responsabile = responsabile;
    }

    public Integer getCodice() {

	return codice;
    }

    public String getResponsabile() {

	return responsabile;
    }

    public static EsportazioniPentahoResponsabili fromResponsabili(Responsabili r) {

	return new EsportazioniPentahoResponsabili(r.getId().getCodice(), r.getResponsabile());
    }
}
