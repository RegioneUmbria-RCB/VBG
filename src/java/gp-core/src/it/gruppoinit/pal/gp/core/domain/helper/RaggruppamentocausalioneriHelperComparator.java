package it.gruppoinit.pal.gp.core.domain.helper;

import java.io.Serializable;
import java.util.Comparator;

public class RaggruppamentocausalioneriHelperComparator implements Comparator<RaggruppamentocausalioneriHelper>, Serializable {

    @Override
    public int compare(RaggruppamentocausalioneriHelper o1, RaggruppamentocausalioneriHelper o2) {

	String raggruppamentocausalioneri1 = o1.getRaggruppamentocausalioneri().getRcoDescr();
	String raggruppamentocausalioneri2 = o2.getRaggruppamentocausalioneri().getRcoDescr();
	if (raggruppamentocausalioneri1 == null && raggruppamentocausalioneri2 == null) {
	    return 0;
	}
	if (raggruppamentocausalioneri1 != null && raggruppamentocausalioneri2 == null) {
	    return -1;
	}
	if (raggruppamentocausalioneri1 == null && raggruppamentocausalioneri2 != null) {
	    return 1;
	}
	return raggruppamentocausalioneri1.compareTo(raggruppamentocausalioneri2);
    }
}
