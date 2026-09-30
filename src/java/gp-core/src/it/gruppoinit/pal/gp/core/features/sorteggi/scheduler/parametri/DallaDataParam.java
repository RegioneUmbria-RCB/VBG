package it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;
import it.gruppoinit.pal.gp.core.features.scheduler.IParameter;
import it.gruppoinit.pal.gp.core.utils.DateFormatService;

public class DallaDataParam implements IParameter<Date> {

    private final String nomeParametro = "DALLA DATA";

    @Override
    public Date getValueFromParameters(Set<Taskschedulerparametri> parametri) {

	try {
	    //1. Tento il recupero dai paramtri
	    for (Taskschedulerparametri parametro : parametri) {
		if (parametro.getId().getParametro().equalsIgnoreCase(nomeParametro) && !StringUtils.isBlank(parametro.getValore())) {
		    return new DateFormatService().getDateDDMMYYYY(parametro.getValore());
		}
	    }
	    return new Date();
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }
}
