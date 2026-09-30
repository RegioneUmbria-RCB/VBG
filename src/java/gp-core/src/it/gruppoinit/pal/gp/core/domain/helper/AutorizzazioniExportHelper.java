package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniFilter;

public class AutorizzazioniExportHelper {

    private Esportazioni esportazioni;
    private Responsabili responsabili;
    private AutorizzazioniFilter autorizzazioniFilter;

    public AutorizzazioniExportHelper() {

    }

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

    public AutorizzazioniFilter getAutorizzazioniFilter() {

	return autorizzazioniFilter;
    }

    public void setAutorizzazioniFilter(AutorizzazioniFilter autorizzazioniFilter) {

	this.autorizzazioniFilter = autorizzazioniFilter;
    }
}
