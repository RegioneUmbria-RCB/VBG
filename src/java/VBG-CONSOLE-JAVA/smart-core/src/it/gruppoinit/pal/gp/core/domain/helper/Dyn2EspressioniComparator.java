package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Dyn2Espressioni;

import java.util.Comparator;

public class Dyn2EspressioniComparator implements Comparator<Dyn2Espressioni> {

    public Dyn2EspressioniComparator() {

    }

    @Override
    public int compare(Dyn2Espressioni o1, Dyn2Espressioni o2) {

	Integer prog1 = o1.getProgressivo();
	Integer prog2 = o2.getProgressivo();
	if (prog1 == null && prog2 == null) {
	    return 0;
	}
	if (prog1 != null && prog2 == null) {
	    return -1;
	}
	if (prog1 == null && prog2 != null) {
	    return 1;
	}
	return prog1.compareTo(prog2);
    }
}
