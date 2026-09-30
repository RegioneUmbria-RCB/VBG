package it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;
import it.gruppoinit.pal.gp.core.features.scheduler.IParameter;

public class CodiceAlgoritmoParam implements IParameter<Integer> {

    private final String nomeParametro = "ALGORITMO";

    @Override
    public Integer getValueFromParameters(Set<Taskschedulerparametri> parametri) {

	try {
	    //1. Tento il recupero dai paramtri
	    for (Taskschedulerparametri parametro : parametri) {
		if (parametro.getId().getParametro().equalsIgnoreCase(nomeParametro) && !StringUtils.isBlank(parametro.getValore())) {
		    return Integer.parseInt(parametro.getValore());
		}
	    }
	    return WebConstants.SORTEGGIO_STANDARD;
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }
}
