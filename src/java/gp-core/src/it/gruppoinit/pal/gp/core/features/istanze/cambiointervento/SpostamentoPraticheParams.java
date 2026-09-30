package it.gruppoinit.pal.gp.core.features.istanze.cambiointervento;

import java.util.Set;

public class SpostamentoPraticheParams {

    private Integer codiceInterventoOrigine;
    private Integer codiceInterventoDestinazione;
    private Set<Integer> idRuoliDaAggiungere;
    private Set<Integer> idSchedeDaAggiungere;

    public SpostamentoPraticheParams(Integer codiceInterventoOrigine, Integer codiceInterventoDestinazione, Set<Integer> idRuoliDaAggiungere,
	    Set<Integer> idSchedeDaAggiungere) {

	super();
	this.codiceInterventoOrigine = codiceInterventoOrigine;
	this.codiceInterventoDestinazione = codiceInterventoDestinazione;
	this.idRuoliDaAggiungere = idRuoliDaAggiungere;
	this.idSchedeDaAggiungere = idSchedeDaAggiungere;
    }

    public Integer getCodiceInterventoOrigine() {

	return codiceInterventoOrigine;
    }

    public Integer getCodiceInterventoDestinazione() {

	return codiceInterventoDestinazione;
    }

    public Set<Integer> getIdRuoliDaAggiungere() {

	return idRuoliDaAggiungere;
    }

    public Set<Integer> getIdSchedeDaAggiungere() {

	return idSchedeDaAggiungere;
    }

    public boolean sonoPresentiRuoliDaAggiungere() {

	return this.idRuoliDaAggiungere != null && this.idRuoliDaAggiungere.size() > 0;
    }

    public boolean sonoPresentiSchedeDaAggiungere() {

	return this.idSchedeDaAggiungere != null && this.idSchedeDaAggiungere.size() > 0;
    }
}
