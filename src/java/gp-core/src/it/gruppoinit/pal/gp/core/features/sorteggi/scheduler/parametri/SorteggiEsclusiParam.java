package it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.parametri;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestata;
import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;
import it.gruppoinit.pal.gp.core.features.scheduler.IParameter;
import it.gruppoinit.pal.gp.core.features.sorteggi.testata.SorteggitestataService;

public class SorteggiEsclusiParam implements IParameter<List<Integer>> {

    private final String nomeParametro = "ESCLUDI ESTRATTE";
    private final String nomeParametroSoftware = "SOFTWARE";
    private SorteggitestataService sorteggiService;

    public SorteggiEsclusiParam(SorteggitestataService sorteggiService) {

	this.sorteggiService = sorteggiService;
    }

    @Override
    public List<Integer> getValueFromParameters(Set<Taskschedulerparametri> parametri) {

	boolean escludiEstratte = true;
	try {
	    //1. Tento il recupero dai paramtri
	    Taskschedulerparametri parEscludiEstratte = this.getParametro(parametri, nomeParametro);
	    if (parEscludiEstratte != null && !StringUtils.isBlank(parEscludiEstratte.getValore())) {
		escludiEstratte = !parEscludiEstratte.getValore().equals("0");
	    }
	    if (!escludiEstratte) {
		return null;
	    }
	    //2. Ricavo il software dai parametri e non dalla sessione
	    Taskschedulerparametri parSoftware = this.getParametro(parametri, nomeParametroSoftware);
	    String software = (parSoftware != null && !StringUtils.isBlank(parSoftware.getValore())) ? parSoftware.getValore()
		    : ORMHelper.getSoftware();
	    List<Integer> elenco = new ArrayList<Integer>();
	    List<Sorteggitestata> sorteggi = this.sorteggiService.findAllSenzaCategoria(software);
	    for (Sorteggitestata sorteggio : sorteggi) {
		elenco.add(sorteggio.getId().getCodice());
	    }
	    return elenco.isEmpty() ? null : elenco;
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    private Taskschedulerparametri getParametro(Set<Taskschedulerparametri> parametri, String nomePar) {

	for (Taskschedulerparametri parametro : parametri) {
	    if (parametro.getId().getParametro().equalsIgnoreCase(nomePar)) {
		return parametro;
	    }
	}
	return null;
    }
}
