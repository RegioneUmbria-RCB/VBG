package it.gruppoinit.pal.gp.core.features.movimenti.stc.segnaposto;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.AmministrazioniCollegateService;

public class SegnapostoPecAmministrazione extends SegnapostoAmministrazioneSTC implements ISegnapostoMovimentiStc {

    private String pecAmministrazione = null;

    public SegnapostoPecAmministrazione(AmministrazioniCollegateService amministrazioniCollegateService, Amministrazioni amministrazione,
	    String codiceComune) {

	super(amministrazioniCollegateService, amministrazione, codiceComune);
	if (amministrazione == null) {
	    return;
	}
	this.pecAmministrazione = resolvePecAmministrazioni();
    }



    @Override
    public String recuperaValore() {

	return this.pecAmministrazione;
    }
}
