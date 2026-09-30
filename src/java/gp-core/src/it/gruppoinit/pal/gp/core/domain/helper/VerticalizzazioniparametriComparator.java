package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class VerticalizzazioniparametriComparator implements Comparator<Verticalizzazioniparametri> {

    @Override
    public int compare(Verticalizzazioniparametri o1, Verticalizzazioniparametri o2) {

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
	String descrizione1 = "";
	String descrizione2 = "";
	if (o1.getVerticalizzazioniparametribase() != null && o1.getVerticalizzazioniparametribase().getId() != null) {
	    descrizione1 = o1.getVerticalizzazioniparametribase().getId().getParametro();
	}
	if (o2.getVerticalizzazioniparametribase() != null && o2.getVerticalizzazioniparametribase().getId() != null) {
	    descrizione2 = o2.getVerticalizzazioniparametribase().getId().getParametro();
	}
	if (StringUtils.isBlank(descrizione1) && StringUtils.isBlank(descrizione2)) {
	    return 0;
	}
	if (StringUtils.isNotBlank(descrizione1) && StringUtils.isBlank(descrizione2)) {
	    return -1;
	}
	if (StringUtils.isBlank(descrizione1) && StringUtils.isNotBlank(descrizione2)) {
	    return 1;
	}
	return descrizione1.compareTo(descrizione2);
    }
}
