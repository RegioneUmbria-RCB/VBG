package it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;
import it.gruppoinit.pal.gp.core.features.scheduler.IParameter;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;

public class AmministrazioneParam implements IParameter<Amministrazioni> {

    private AmministrazioniService service;
    private final String nomeParametro = "CODICE AMMINISTRAZIONE";

    public AmministrazioneParam(AmministrazioniService service) {

	this.service = service;
    }

    @Override
    public Amministrazioni getValueFromParameters(Set<Taskschedulerparametri> parametri) {

	//1. Tento il recupero dai paramtri
	for (Taskschedulerparametri parametro : parametri) {
	    if (parametro.getId().getParametro().equalsIgnoreCase(nomeParametro) && !StringUtils.isBlank(parametro.getValore())) {
		return this.service.findById(new PkId(Integer.parseInt(parametro.getValore())));
	    }
	}
	return null;
    }
}
