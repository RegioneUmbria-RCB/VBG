package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.HashSet;
import java.util.Set;

public class BlackList {

    private Set<Integer> autorizzazioniSpuntisti = new HashSet<Integer>();
    private Set<Integer> autorizzazioniConcessionari = new HashSet<Integer>();

    public Set<Integer> getAutorizzazioniSpuntisti() {

	return autorizzazioniSpuntisti;
    }

    public void setAutorizzazioniSpuntisti(Set<Integer> autorizzazioniSpuntisti) {

	this.autorizzazioniSpuntisti = autorizzazioniSpuntisti;
    }

    public Set<Integer> getAutorizzazioniConcessionari() {

	return autorizzazioniConcessionari;
    }

    public void setAutorizzazioniConcessionari(Set<Integer> autorizzazioniConcessionari) {

	this.autorizzazioniConcessionari = autorizzazioniConcessionari;
    }
}
