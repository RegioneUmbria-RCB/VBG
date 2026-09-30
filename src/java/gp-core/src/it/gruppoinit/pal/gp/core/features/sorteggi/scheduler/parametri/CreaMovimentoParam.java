package it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.features.scheduler.IParameter;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;

public class CreaMovimentoParam implements IParameter<Tipimovimento> {

    private TipiMovimentoService service;
    private final String nomeParametro = "CREA MOVIMENTO";

    public CreaMovimentoParam(TipiMovimentoService service) {

	this.service = service;
    }

    @Override
    public Tipimovimento getValueFromParameters(Set<Taskschedulerparametri> parametri) {

	//1. Tento il recupero dai paramtri
	for (Taskschedulerparametri parametro : parametri) {
	    if (parametro.getId().getParametro().equalsIgnoreCase(nomeParametro) && !StringUtils.isBlank(parametro.getValore())) {
		return this.service.findById(new TipimovimentoId(parametro.getValore()));
	    }
	}
	return null;
    }
}