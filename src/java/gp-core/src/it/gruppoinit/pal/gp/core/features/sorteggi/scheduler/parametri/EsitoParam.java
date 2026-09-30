package it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;
import it.gruppoinit.pal.gp.core.features.scheduler.IParameter;

public class EsitoParam implements IParameter<Boolean> {

    private final String nomeParametro = "ESITO";

    @Override
    public Boolean getValueFromParameters(Set<Taskschedulerparametri> parametri) {

	//1. Tento il recupero dai paramtri
	for (Taskschedulerparametri parametro : parametri) {
	    if (parametro.getId().getParametro().equalsIgnoreCase(nomeParametro) && !StringUtils.isBlank(parametro.getValore())) {
		return "1".equalsIgnoreCase(parametro.getValore());
	    }
	}
	return null;
    }
}
