package it.gruppoinit.pal.gp.core.features.movimenti.stc.segnaposto;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.AmministrazioniCollegateService;

public class SegnapostoAmministrazioneSTC {

    protected AmministrazioniCollegateService amministrazioniCollegateService;
    protected Amministrazioni amministrazioni;
    protected String codiceComune;

    public SegnapostoAmministrazioneSTC(AmministrazioniCollegateService amministrazioniCollegateService, Amministrazioni amministrazioni,
	    String codiceComune) {

	this.amministrazioniCollegateService = amministrazioniCollegateService;
	this.amministrazioni = amministrazioni;
	this.codiceComune = codiceComune;
    }

    private Amministrazioni resolveAmministrazione() {

	Integer codiceAmministrazione = amministrazioni.getId().getCodice();
	Amministrazioni ammCollegata = amministrazioniCollegateService.findCollegataByAmministrazioneAndComune(codiceAmministrazione, codiceComune);
	if (ammCollegata != null) {
	    return ammCollegata;
	}
	return this.amministrazioni;
    }

    protected String resolveMailAmministrazioni() {

	return resolveAmministrazione().getEmail();
    }

    protected String resolvePecAmministrazioni() {

	return resolveAmministrazione().getPec();
    }
}
