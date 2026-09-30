package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniAttivitaHelper;

import java.util.ArrayList;
import java.util.List;

public class IstanzeAttivitaHelper {

    private String numeroIstanza;
    private List<AutorizzazioniAttivitaHelper> autorizzazioniAttivitaHelpers;

    public String getNumeroIstanza() {

	return numeroIstanza;
    }

    public void setNumeroIstanza(String numeroIstanza) {

	this.numeroIstanza = numeroIstanza;
    }

    public List<AutorizzazioniAttivitaHelper> getAutorizzazioniAttivitaHelpers() {

	if (null == autorizzazioniAttivitaHelpers) {
	    autorizzazioniAttivitaHelpers = new ArrayList<AutorizzazioniAttivitaHelper>();
	}
	return autorizzazioniAttivitaHelpers;
    }

    public void setAutorizzazioniAttivitaHelpers(List<AutorizzazioniAttivitaHelper> autorizzazioniAttivitaHelpers) {

	this.autorizzazioniAttivitaHelpers = autorizzazioniAttivitaHelpers;
    }
}
