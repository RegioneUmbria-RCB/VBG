package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

public class DettaglioPeriodicitaElementi {

    private String chiave;
    private String descrizione;

    public DettaglioPeriodicitaElementi(String chiave, String descrizione) {

	super();
	this.chiave = chiave;
	this.descrizione = descrizione;
    }

    public String getChiave() {

	return chiave;
    }

    public String getDescrizione() {

	return descrizione;
    }
}
