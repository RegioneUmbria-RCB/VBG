package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class FamiglieEndoComparator implements Comparator<FamiglieEndoBean> {

    @Override
    public int compare(FamiglieEndoBean o1, FamiglieEndoBean o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	boolean intervento1 = o1.isIntervento();
	boolean intervento2 = o2.isIntervento();
	if (intervento1 != intervento2) {
	    Integer val1 = intervento1 ? 0 : 1;
	    Integer val2 = intervento2 ? 0 : 1;
	    return val1.compareTo(val2);
	}
	Integer ordine1 = o1.getOrdine();
	Integer ordine2 = o2.getOrdine();
	String descrizione1 = StringUtils.defaultString(o1.getFamiglia());
	String descrizione2 = StringUtils.defaultString(o2.getFamiglia());
	int condizione = ordine1.compareTo(ordine2);
	if (condizione != 0) {
	    return condizione;
	}
	return descrizione1.compareTo(descrizione2);
    }
}
