package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Tipimovimento;

import java.io.Serializable;
import java.util.Comparator;

public class TipimovimentoComparator implements Comparator<Tipimovimento>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2586270962112763780L;

    @Override
    public int compare(Tipimovimento o1, Tipimovimento o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	String descrizione1 = o1.getMovimento() == null ? "" : o1.getMovimento().toLowerCase();
	String descrizione2 = o2.getMovimento() == null ? "" : o2.getMovimento().toLowerCase();
	return descrizione1.compareTo(descrizione2);
    }
}
