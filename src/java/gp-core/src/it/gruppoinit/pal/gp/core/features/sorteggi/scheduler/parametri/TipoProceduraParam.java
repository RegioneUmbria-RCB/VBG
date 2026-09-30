package it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;
import it.gruppoinit.pal.gp.core.features.scheduler.IParameter;

public class TipoProceduraParam implements IParameter<List<Integer>> {

    private final String nomeParametro = "TIPO PROCEDURA";

    @Override
    public List<Integer> getValueFromParameters(Set<Taskschedulerparametri> parametri) {

	//1. Tento il recupero dai paramtri
	for (Taskschedulerparametri parametro : parametri) {
	    if (parametro.getId().getParametro().equalsIgnoreCase(nomeParametro) && !StringUtils.isBlank(parametro.getValore())) {
		List<Integer> retVal = new ArrayList<Integer>();
		String[] ids = parametro.getValore().split(",");
		for (String id : ids) {
		    retVal.add(Integer.parseInt(id));
		}
		return retVal;
	    }
	}
	return null;
    }
}
