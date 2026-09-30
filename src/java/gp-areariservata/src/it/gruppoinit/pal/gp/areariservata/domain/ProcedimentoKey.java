package it.gruppoinit.pal.gp.areariservata.domain;

public class ProcedimentoKey implements Comparable<ProcedimentoKey> {

    private Integer ordine;
    private String descrizione;
    private boolean visualizza;

    public ProcedimentoKey(Integer ordine, String descrizione) {

	this.ordine = ordine != null ? ordine : 0;
	this.descrizione = descrizione != null ? descrizione : "";
    }

    @Override
    public int compareTo(ProcedimentoKey o) {

	if (ordine.compareTo(o.getOrdine()) == 0) {
	    return descrizione.compareTo(o.getDescrizione());
	} else {
	    return ordine.compareTo(o.getOrdine());
	}
    }

    public Integer getOrdine() {

	return ordine;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public boolean isVisualizza() {

	return visualizza;
    }

    public void setVisualizza(boolean visualizza) {

	this.visualizza = visualizza;
    }
}
