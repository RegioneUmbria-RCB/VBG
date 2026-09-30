package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.FiltriRicercaDettagliBase;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;

public class FiltriRicercaDettagliCommissioni extends FiltriRicercaDettagliBase {

    private int idCommissione;

    public FiltriRicercaDettagliCommissioni(int idCommissione, boolean escludiAnagraficheSenzaMail,
	    SceltaTipoMailAnagrafeEnum sceltaTipoMailAnagrafeEnum) {

	super(escludiAnagraficheSenzaMail, sceltaTipoMailAnagrafeEnum);
	this.idCommissione = idCommissione;
    }

    public int getIdCommissione() {

	return idCommissione;
    }
}
