package it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;
import it.gruppoinit.pal.gp.core.features.scheduler.IParameter;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;

public class CodiciInterventoParam implements IParameter<List<String>> {

    private AlberoprocService alberoProcService;
    private final String nomeParametro = "TIPO INTERVENTO";

    public CodiciInterventoParam(AlberoprocService alberoProcService) {

	this.alberoProcService = alberoProcService;
    }

    @Override
    public List<String> getValueFromParameters(Set<Taskschedulerparametri> parametri) {

	//1. Tento il recupero dai paramtri
	for (Taskschedulerparametri parametro : parametri) {
	    if (parametro.getId().getParametro().equalsIgnoreCase(nomeParametro) && !StringUtils.isBlank(parametro.getValore())) {
		List<String> retVal = new ArrayList<String>();
		String[] elencoId = parametro.getValore().split(",");
		for (String id : elencoId) {
		    Alberoproc albero = this.alberoProcService.findById(new PkId(Integer.parseInt(id)));
		    if (albero == null) {
			throw new RuntimeException("Impossibile risalire alla voce dell'albero degli interventi con id " + id);
		    }
		    retVal.add(albero.getScCodice());
		}
		return retVal;
	    }
	}
	return null;
    }
}
