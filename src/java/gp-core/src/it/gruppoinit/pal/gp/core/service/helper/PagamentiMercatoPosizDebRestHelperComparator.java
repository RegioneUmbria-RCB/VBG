package it.gruppoinit.pal.gp.core.service.helper;

import java.util.Comparator;
import java.util.Date;

public class PagamentiMercatoPosizDebRestHelperComparator implements Comparator<PagamentiMercatoPosizDebRestHelper> {

    @Override
    public int compare(PagamentiMercatoPosizDebRestHelper o1, PagamentiMercatoPosizDebRestHelper o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	// ordinati per ultima data registrazione
	Date ordine1 = o1.getData_presenza();
	Date ordine2 = o2.getData_presenza();
	if (ordine1 == null && ordine2 == null) {
	    return 0;
	}
	if (ordine1 == null) {
	    return -1;
	}
	if (ordine2 == null) {
	    return 1;
	}
	return ordine2.compareTo(ordine1);
    }
}
