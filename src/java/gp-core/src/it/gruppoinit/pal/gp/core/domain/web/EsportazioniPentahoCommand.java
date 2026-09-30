package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Responsabili;

public class EsportazioniPentahoCommand {

    private Esportazioni esportazioni;
    private Responsabili responsabili;

    public Esportazioni getEsportazioni() {

	return esportazioni;
    }

    public void setEsportazioni(Esportazioni esportazioni) {

	this.esportazioni = esportazioni;
    }

    public Responsabili getResponsabili() {

	return responsabili;
    }

    public void setResponsabili(Responsabili responsabili) {

	this.responsabili = responsabili;
    }
}
