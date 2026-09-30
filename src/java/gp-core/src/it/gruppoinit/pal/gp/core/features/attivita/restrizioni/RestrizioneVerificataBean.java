package it.gruppoinit.pal.gp.core.features.attivita.restrizioni;

import java.util.ArrayList;
import java.util.List;

public class RestrizioneVerificataBean {

    private String etichettaEccezione;
    private String criterioDiRicerca;
    private List<Integer> elencoIdAttivita = new ArrayList<Integer>();

    public RestrizioneVerificataBean() {

	super();
    }

    public void setElencoAttivita(List<Integer> elencoIdAttivita) {

	this.elencoIdAttivita = elencoIdAttivita;
    }

    public List<Integer> getElencoIdAttivita() {

	return elencoIdAttivita;
    }

    public String getCriterioDiRicerca() {

	return criterioDiRicerca;
    }

    public void setCriterioDiRicerca(String criterioDiRicerca) {

	this.criterioDiRicerca = criterioDiRicerca;
    }

    public String getEtichettaEccezione() {

	return etichettaEccezione;
    }

    public void setEtichettaEccezione(String etichettaEccezione) {

	this.etichettaEccezione = etichettaEccezione;
    }
}
