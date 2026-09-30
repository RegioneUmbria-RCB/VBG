package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.Comuni;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class ComuniComparator implements Comparator<Comuni> {

    @Override
    public int compare(Comuni comune1, Comuni comune2) {

	if (comune1 == null && comune2 == null) {
	    return 0;
	}
	if (comune1 != null && comune2 == null) {
	    return -1;
	}
	if (comune1 == null && comune2 != null) {
	    return 1;
	}
	String c1 = StringUtils.defaultString(comune1.getComune());
	String c2 = StringUtils.defaultString(comune2.getComune());
	return c1.toLowerCase().compareTo(c2.toLowerCase());
    }
}
