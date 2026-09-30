package it.gruppoinit.pal.gp.core.domain;

import java.util.Comparator;

public class ValoreParametroTypeHelperComparator implements Comparator<ValoreParametroTypeHelper> {

    @Override
    public int compare(ValoreParametroTypeHelper o1, ValoreParametroTypeHelper o2) {

	if (o1.getIdxMolteplicita() < o2.getIdxMolteplicita()) {
	    return -1;
	} else if (o1.getIdxMolteplicita() > o2.getIdxMolteplicita()) {
	    return 1;
	} else
	    return 0;
    }
}
