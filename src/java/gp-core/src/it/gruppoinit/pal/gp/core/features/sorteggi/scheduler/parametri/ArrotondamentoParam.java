package it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;
import it.gruppoinit.pal.gp.core.features.scheduler.IParameter;

public class ArrotondamentoParam implements IParameter<Integer> {

    private final String nomeParametro = "ARROTONDAMENTO";

    @Override
    public Integer getValueFromParameters(Set<Taskschedulerparametri> parametri) {

	//1. Tento il recupero dai paramtri
	for (Taskschedulerparametri parametro : parametri) {
	    if (parametro.getId().getParametro().equalsIgnoreCase(nomeParametro) && !StringUtils.isBlank(parametro.getValore())) {
		if (parametro.getValore().equalsIgnoreCase("INT-")) {
		    return 0;
		} else if (parametro.getValore().equalsIgnoreCase("INT+")) {
		    return 1;
		} else if (parametro.getValore().equalsIgnoreCase("ROUND")) {
		    return 2;
		} else {
		    throw new RuntimeException("Il parametro ARROTONDAMENTO non può accettare il seguente valore: " + parametro.getValore());
		}
	    }
	}
	throw new RuntimeException("Il parametro ARROTONDAMENTO è obbligatorio e non è stato specificato");
    }
}
