package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model;

public class ProcBollElaborazioneInvioNodoBean {

    private int idBollettazione;
    private int totaleRecord;
    private int totaleRecordElaborati;

    public ProcBollElaborazioneInvioNodoBean(int idBollettazione) {

	super();
	this.idBollettazione = idBollettazione;
    }

    public void setTotaleRecord(int totaleRecord) {

	this.totaleRecord = totaleRecord;
    }

    public int getIdBollettazione() {

	return idBollettazione;
    }

    public int getTotaleRecord() {

	return totaleRecord;
    }

    public int getTotaleRecordElaborati() {

	return totaleRecordElaborati;
    }

    public void aggiungiElaborato() {

	totaleRecordElaborati = totaleRecordElaborati + 1;
    }
}
