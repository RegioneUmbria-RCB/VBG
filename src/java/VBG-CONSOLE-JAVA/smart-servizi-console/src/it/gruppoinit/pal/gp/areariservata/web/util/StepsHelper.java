package it.gruppoinit.pal.gp.areariservata.web.util;

import it.gruppoinit.pal.gp.core.domain.FoArjSteps;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsParams;
import it.gruppoinit.pal.gp.core.domain.helper.StepsEnum;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StepsHelper {

    private List<FoArjSteps> steps;
    private Map<Integer, List<ChiaveValoreBean<String, String>>> stepsParams = new HashMap<Integer, List<ChiaveValoreBean<String, String>>>();

    private StepsHelper() {

	super();
    }

    public StepsHelper(List<FoArjSteps> steps) {

	this();
	this.steps = steps;
	int ordinePager = 0;
	for (FoArjSteps s : steps) {
	    s.setOrdinePager(++ordinePager);
	    List<ChiaveValoreBean<String, String>> stepParams = new ArrayList<ChiaveValoreBean<String, String>>();
	    for (FoArjStepsParams p : s.getFoArjStepsParamses()) {
		ChiaveValoreBean<String, String> param = new ChiaveValoreBean<String, String>();
		param.setChiave(p.getFoArjStepsParamsBase().getChiave());
		param.setValore(p.getValore());
		stepParams.add(param);
	    }
	    stepsParams.put(s.getId().getCodice(), stepParams);
	}
    }

    public List<FoArjSteps> getSteps() {

	return this.steps;
    }

    public FoArjSteps getNextStep(Integer ordinePager) {

	if (ordinePager == null) {
	    return steps.get(0);
	}
	for (FoArjSteps step : steps) {
	    if (step.getOrdinePager().compareTo(ordinePager) > 0) {
		return step;
	    } else {
		step.setVisited(true);
	    }
	}
	return null;
    }

    public FoArjSteps getCurrentStep(Integer ordinePager) throws Exception {

	if (ordinePager == null) {
	    throw new Exception("ordinePager deve essere valorizzato");
	}
	for (FoArjSteps step : steps) {
	    if (step.getOrdinePager().compareTo(ordinePager) == 0) {
		return step;
	    }
	}
	return null;
    }

    public FoArjSteps getPreviousStep(Integer ordinePager) {

	FoArjSteps temp = null;
	if (ordinePager == null) {
	    return steps.get(0);
	}
	for (FoArjSteps step : steps) {
	    if (step.getOrdinePager().compareTo(ordinePager) < 0) {
		// cerco l'ultimo con ordine minore
		temp = step;
	    }
	}
	return temp;
    }

    public FoArjSteps getLastStep() {

	return steps.get(steps.size() - 1);
    }

    public boolean isStepPresent(StepsEnum nomeStep) {

	for (FoArjSteps s : steps) {
	    if (s.getFoArjStepsBase().getNomeStep().equals(nomeStep.toString())) {
		return true;
	    }
	}
	return false;
    }

    public List<ChiaveValoreBean<String, String>> getParametriStep(Integer codiceStep) {

	List<ChiaveValoreBean<String, String>> result = stepsParams.get(codiceStep);
	if (result == null) {
	    return new ArrayList<ChiaveValoreBean<String, String>>();
	}
	return result;
    }
}
