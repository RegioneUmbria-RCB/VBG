package it.gruppoinit.pal.gp.core.features.movimenti.stc.segnaposto;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.AmministrazioniCollegateService;

public class SegnapostoMailAmministrazione extends SegnapostoAmministrazioneSTC implements ISegnapostoMovimentiStc {

    public SegnapostoMailAmministrazione(AmministrazioniCollegateService amministrazioniCollegateService, Amministrazioni amministrazione,
	    String codiceComune) {

	super(amministrazioniCollegateService, amministrazione, codiceComune);
	if (amministrazione == null) {
	    return;
	}
	this.mailAmministrazione = resolveMailAmministrazioni();
    }

    
    private String mailAmministrazione = null;

    @Override
    public String recuperaValore() {

	return this.mailAmministrazione;
    }
}
