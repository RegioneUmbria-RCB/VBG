package it.gruppoinit.pal.gp.core.dao.helper;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

public class ListaAutorizzazioniPerPeriodo {

    private Set<Integer> auts = new HashSet<Integer>();
    private Set<Integer> presentiOggi = new HashSet<Integer>();
    private Set<Integer> presentiUltimoMercato = new HashSet<Integer>();
    private Set<Integer> presentiAnnoScorso = new HashSet<Integer>();

    public ListaAutorizzazioniPerPeriodo() {

	super();
    }

    public ListaAutorizzazioniPerPeriodo(List<ChiaveValoreBean<BigDecimal, String>> list) {

	this();
	for (ChiaveValoreBean<BigDecimal, String> cvb : list) {
	    Integer idAutorizzazione = cvb.getChiave().intValue();
	    String tipo = cvb.getValore();
	    if ("OGGI".equals(tipo)) {
		this.getPresentiOggi().add(idAutorizzazione);
	    }
	    if ("ULTIMO_MERCATO".equals(tipo)) {
		this.getPresentiUltimoMercato().add(idAutorizzazione);
	    }
	    if ("ANNO_SCORSO".equals(tipo)) {
		this.getPresentiAnnoScorso().add(idAutorizzazione);
	    }
	    this.getAuts().add(idAutorizzazione);
	}
    }

    public void mergeRisultati(ListaAutorizzazioniPerPeriodo otherObject) {

	this.getAuts().addAll(otherObject.getAuts());
	this.getPresentiAnnoScorso().addAll(otherObject.getPresentiAnnoScorso());
	this.getPresentiOggi().addAll(otherObject.getPresentiOggi());
	this.getPresentiUltimoMercato().addAll(otherObject.getPresentiUltimoMercato());
    }

    public Set<Integer> getAuts() {

	return auts;
    }

    public Set<Integer> getPresentiOggi() {

	return presentiOggi;
    }

    public Set<Integer> getPresentiUltimoMercato() {

	return presentiUltimoMercato;
    }

    public Set<Integer> getPresentiAnnoScorso() {

	return presentiAnnoScorso;
    }
}
