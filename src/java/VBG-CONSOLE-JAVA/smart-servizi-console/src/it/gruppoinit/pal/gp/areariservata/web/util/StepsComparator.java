package it.gruppoinit.pal.gp.areariservata.web.util;

import it.gruppoinit.pal.gp.core.domain.FoArjSteps;

import java.util.Comparator;

public class StepsComparator implements Comparator<FoArjSteps> {

    @Override
    public int compare(FoArjSteps o1, FoArjSteps o2) {

	Integer oo1 = o1.getOrdine();
	Integer oo2 = o2.getOrdine();
	return oo1.compareTo(oo2);
    }
}
