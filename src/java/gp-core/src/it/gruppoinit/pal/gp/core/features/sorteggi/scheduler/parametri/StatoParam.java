package it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;
import it.gruppoinit.pal.gp.core.features.scheduler.IParameter;

public class StatoParam implements IParameter<List<String>> {

    private final String nomeParametro = "STATO";

    @Override
    public List<String> getValueFromParameters(Set<Taskschedulerparametri> parametri) {

	//1. Tento il recupero dai paramtri
	for (Taskschedulerparametri parametro : parametri) {
	    if (parametro.getId().getParametro().equalsIgnoreCase(nomeParametro) && !StringUtils.isBlank(parametro.getValore())) {
		List<String> retVal = new ArrayList<String>();
		String[] elenco = parametro.getValore().split(",");
		for (String id : elenco) {
		    retVal.add(id);
		}
		return retVal;
	    }
	}
	return null;
    }
}
