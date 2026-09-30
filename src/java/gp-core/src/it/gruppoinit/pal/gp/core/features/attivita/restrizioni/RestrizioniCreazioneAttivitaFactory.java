package it.gruppoinit.pal.gp.core.features.attivita.restrizioni;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.features.attivita.verticalizzazione.IVerticalizzazioneIAttivitaService;
import it.gruppoinit.pal.gp.core.service.VwIAttivitalistaService;

public class RestrizioniCreazioneAttivitaFactory {

    private List<IRestrizione> restrizioni = new ArrayList<IRestrizione>(0);

    public RestrizioniCreazioneAttivitaFactory(IVerticalizzazioneIAttivitaService verticalizzazioneIAttivitaService,
	    VwIAttivitalistaService vwIAttivitalistaService, RestrizioniCreazioneAttivitaParams parametri) {

	this.restrizioni.add(new RestrizioneDenominazioneEsistente(verticalizzazioneIAttivitaService, vwIAttivitalistaService,
		parametri.getDenominazioneAttivita(), parametri.isCheckAttive()));
	this.restrizioni.add(new RestrizioneLocalizzazioneEsistente(verticalizzazioneIAttivitaService, vwIAttivitalistaService,
		parametri.getLocalizzazioni(), parametri.isCheckAttive()));
    }

    public List<RestrizioneVerificataBean> verificaRestrizioni() {

	List<RestrizioneVerificataBean> retVal = new ArrayList<RestrizioneVerificataBean>();
	for (IRestrizione restrizione : this.restrizioni) {
	    RestrizioneVerificataBean restrizioneVerificata = restrizione.verifica();
	    if (!restrizioneVerificata.getElencoIdAttivita().isEmpty()) {
		retVal.add(restrizioneVerificata);
	    }
	}
	return retVal;
    }
}
