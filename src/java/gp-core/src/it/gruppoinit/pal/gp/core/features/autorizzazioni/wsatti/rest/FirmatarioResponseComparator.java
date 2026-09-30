package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest;

import java.util.Comparator;

public class FirmatarioResponseComparator implements Comparator<FirmatarioResponse> {

    @Override
    public int compare(FirmatarioResponse firmatario1, FirmatarioResponse firmatario2) {

	int c;
	c = firmatario1.getDescrizione().compareToIgnoreCase(firmatario2.getDescrizione());
	if (c == 0) {
	    c = firmatario1.getCodice().compareToIgnoreCase(firmatario2.getCodice());
	}
	return c;
    }
}
