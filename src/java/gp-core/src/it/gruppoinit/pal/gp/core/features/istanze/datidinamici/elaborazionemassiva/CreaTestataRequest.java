package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva;

import java.util.Set;
import java.util.TreeSet;

import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model.SchedaDinamicaModel;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model.SchedaDinamicaModelComparator;

public class CreaTestataRequest {

    private Set<Integer> interventi = new TreeSet<Integer>();
    private String descrizione;
    private String statoIstanza;
    private Set<SchedaDinamicaModel> schedeDaElaborare = new TreeSet<SchedaDinamicaModel>(new SchedaDinamicaModelComparator());
    private Set<Integer> registri = new TreeSet<Integer>();

    public Set<Integer> getInterventi() {

	return interventi;
    }

    public String getStatoIstanza() {

	return statoIstanza;
    }

    public Set<SchedaDinamicaModel> getSchedeDaElaborare() {

	return schedeDaElaborare;
    }

    public Set<Integer> getRegistri() {

	return registri;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public CreaTestataRequest(Set<Integer> interventi, String descrizione, String statoIstanza, Set<SchedaDinamicaModel> schedeDaElaborare,
	    Set<Integer> registri) {

	this.interventi = interventi;
	this.descrizione = descrizione;
	this.statoIstanza = statoIstanza;
	this.schedeDaElaborare = schedeDaElaborare;
	this.registri = registri;
    }
}
