package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;

public class FiltriRicercaDettagliBase {

    private boolean escludiAnagraficheSenzaMail;
    private SceltaTipoMailAnagrafeEnum sceltaTipoMailAnagrafeEnum;

    public FiltriRicercaDettagliBase(boolean escludiAnagraficheSenzaMail, SceltaTipoMailAnagrafeEnum sceltaTipoMailAnagrafeEnum) {

	this.escludiAnagraficheSenzaMail = escludiAnagraficheSenzaMail;
	this.sceltaTipoMailAnagrafeEnum = sceltaTipoMailAnagrafeEnum;
    }

    public SceltaTipoMailAnagrafeEnum getSceltaTipoMailAnagrafeEnum() {

	return sceltaTipoMailAnagrafeEnum;
    }

    public boolean isEscludiAnagraficheSenzaMail() {

	return escludiAnagraficheSenzaMail;
    }
}
