package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.ArrayList;
import java.util.List;

public class EsitoCalcoloBollettazione {

    private IntervalloDate intervalloDate;
    private List<? extends IRigaDettaglioCalcolo> righe;

    public EsitoCalcoloBollettazione(IntervalloDate intervalloDate, List<? extends IRigaDettaglioCalcolo> righe) {

	this.intervalloDate = intervalloDate;
	this.righe = righe;
    }

    public IntervalloDate getIntervalloDate() {

	return this.intervalloDate;
    }

    public List<? extends IRigaDettaglioCalcolo> getRighe() {

	if (this.righe == null) {
	    this.righe = new ArrayList<IRigaDettaglioCalcolo>();
	}
	return this.righe;
    }
}
