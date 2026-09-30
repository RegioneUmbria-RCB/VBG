package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Movimenti;

import java.util.Comparator;
import java.util.Date;

public class MovimentiDataComparator implements Comparator<Movimenti> {

    @Override
    public int compare(Movimenti o1, Movimenti o2) {

	// Controlla che gli oggetti non siano vuoti
	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	Date ordine1 = null;
	Date ordine2 = null;
	if (o1.getData() != null) {
	    ordine1 = o1.getData();
	}
	if (o2.getData() != null) {
	    ordine2 = o2.getData();
	}
	if (ordine1 == null && ordine2 == null) {
	    return 0;
	}
	if (ordine1 != null && ordine2 == null) {
	    return -1;
	}
	if (ordine1 == null && ordine2 != null) {
	    return 1;
	}
	int risultato = 0;
	risultato = ordine1.compareTo(ordine2);
	return risultato;
    }
}
