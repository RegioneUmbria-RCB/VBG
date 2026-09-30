package it.gruppoinit.pal.gp.core.features.movimenti.stc.segnaposto;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.AmministrazioniCollegateService;

public class SegnapostiMovimentoSTCBuilder {

    private Movimenti movimento;
    private AmministrazioniCollegateService amministrazioniCollegateService;
    private String codiceComune;

    public SegnapostiMovimentoSTCBuilder(Movimenti movimento, AmministrazioniCollegateService amministrazioniCollegateService, String codiceComune) {

	this.amministrazioniCollegateService = amministrazioniCollegateService;
	this.movimento = movimento;
	this.codiceComune = codiceComune;
    }

    public String sostituisciSegnaposto(String segnaposto) {

	if (StringUtils.isBlank(segnaposto)) {
	    return null;
	}
	try {
	    ISegnapostoMovimentiStc segnapostoMovimentiStc;
	    SegnapostoSTCEnum segnapostoDaSostituire = SegnapostoSTCEnum.fromValue(segnaposto);
	    switch (segnapostoDaSostituire) {
	    case MAIL_AMMINISTRAZIONE:
		segnapostoMovimentiStc = new SegnapostoMailAmministrazione(amministrazioniCollegateService, this.movimento.getAmministrazioni(),
			codiceComune);
		break;
	    case PEC_AMMINISTRAZIONE:
		segnapostoMovimentiStc = new SegnapostoPecAmministrazione(amministrazioniCollegateService, this.movimento.getAmministrazioni(),
			codiceComune);
		break;
	    default:
		return segnaposto;
	    }
	    return segnapostoMovimentiStc.recuperaValore();
	} catch (Exception e) {
	    return segnaposto;
	}
    }
}
