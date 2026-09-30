package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.FiltriRicercaDettagliBase;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;

public class FiltriRicercaDettagli extends FiltriRicercaDettagliBase {

    private int idBollettazione;
    private boolean soloPosizioniDebitorieNonPagate;

    public FiltriRicercaDettagli(int idBollettazione, boolean escludiAnagraficheSenzaMail, boolean soloPosizioniDebitorieNonPagate,
	    SceltaTipoMailAnagrafeEnum sceltaTipoMailAnagrafeEnum) {

	super(escludiAnagraficheSenzaMail, sceltaTipoMailAnagrafeEnum);
	this.idBollettazione = idBollettazione;
	this.soloPosizioniDebitorieNonPagate = soloPosizioniDebitorieNonPagate;
    }

    public int getIdBollettazione() {

	return idBollettazione;
    }

    public boolean isSoloPosizioniDebitorieNonPagate() {

	return soloPosizioniDebitorieNonPagate;
    }
}
