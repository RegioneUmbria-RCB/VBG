package it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;
import it.gruppoinit.pal.gp.core.features.scheduler.IParameter;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;

public class ResponsabileParam implements IParameter<Responsabili> {

    private ResponsabiliService service;
    private final String nomeParametro = "CODICE RESPONSABILE";

    public ResponsabileParam(ResponsabiliService service) {

	this.service = service;
    }

    @Override
    public Responsabili getValueFromParameters(Set<Taskschedulerparametri> parametri) {

	//1. Tento il recupero dai paramtri
	for (Taskschedulerparametri parametro : parametri) {
	    if (parametro.getId().getParametro().equalsIgnoreCase(nomeParametro) && !StringUtils.isBlank(parametro.getValore())) {
		return this.service.findById(new PkId(Integer.parseInt(parametro.getValore())));
	    }
	}
	return null;
    }
}
