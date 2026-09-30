package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.Istanze;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class NumeroIstanzaComparator implements Comparator<Istanze> {

    @Override
    public int compare(Istanze istanza1, Istanze istanza2) {

	String numist1 = StringUtils.leftPad(istanza1.getNumeroistanza(), 50, '0');
	String numist2 = StringUtils.leftPad(istanza2.getNumeroistanza(), 50, '0');
	if (numist1 == null && numist2 == null) {
	    return 0;
	}
	if (numist1 != null && numist2 == null) {
	    return -1;
	}
	if (numist1 == null && numist2 != null) {
	    return 1;
	}
	return numist1.compareTo(numist2);
    }
}
