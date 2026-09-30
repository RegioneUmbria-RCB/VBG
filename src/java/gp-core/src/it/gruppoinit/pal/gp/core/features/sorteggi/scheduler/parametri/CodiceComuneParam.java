package it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;
import it.gruppoinit.pal.gp.core.features.scheduler.IParameter;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;

public class CodiceComuneParam implements IParameter<String> {

    private final String nomeParametro = "CODICECOMUNE";
    private ComuniassociatiService comuniAssociatiService;

    public CodiceComuneParam(ComuniassociatiService comuniAssociatiService) {

	this.comuniAssociatiService = comuniAssociatiService;
    }

    @Override
    public String getValueFromParameters(Set<Taskschedulerparametri> parametri) {

	//1. Tento il recupero dai paramtri
	for (Taskschedulerparametri parametro : parametri) {
	    if (parametro.getId().getParametro().equalsIgnoreCase(nomeParametro) && !StringUtils.isBlank(parametro.getValore())) {
		return parametro.getValore();
	    }
	}
	//2. Tento il recupero dalla comuniassociati se installazione a comune singolo
	List<Comuniassociati> comuni = this.comuniAssociatiService.findByIdcomune(ORMHelper.getIdcomune());
	if (comuni.size() > 1) {
	    throw new RuntimeException(
		    "Si sta tentando di effettuare un sorteggio in un'installazione multicomune senza specificare il comune di riferimento.");
	}
	return comuni.get(0).getId().getCodicecomune();
    }
}
