package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.ArrayList;
import java.util.List;

public class RigaDettaglioBollettazioneMercatiDett extends RigaDettaglioCalcolo implements IRigaDettaglioCalcolo {

    private Boolean conguaglio;
    private List<BollGestMercatiDettHelper> dettagliMercati;

    public RigaDettaglioBollettazioneMercatiDett() {

	conguaglio = Boolean.FALSE;
    }

    public List<BollGestMercatiDettHelper> getDettagliMercati() {

	if (this.dettagliMercati == null) {
	    this.dettagliMercati = new ArrayList<BollGestMercatiDettHelper>(0);
	}
	return dettagliMercati;
    }

    public void setDettagliMercati(List<BollGestMercatiDettHelper> dettagliMercati) {

	this.dettagliMercati = dettagliMercati;
    }

    public void setConguaglio(Boolean conguaglio) {

	this.conguaglio = conguaglio;
    }

    public Boolean getConguaglio() {

	return conguaglio;
    }
}
