package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class EndoprocedimentoSimpleBeanComparator implements Comparator<EndoprocedimentoSimpleBean> {

    @Override
    public int compare(EndoprocedimentoSimpleBean o1, EndoprocedimentoSimpleBean o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	Integer ordine1 = o1.getOrdine();
	Integer ordine2 = o2.getOrdine();
	String descrizione1 = StringUtils.defaultString(o1.getNome());
	String descrizione2 = StringUtils.defaultString(o2.getNome());
	int condizione = ordine1.compareTo(ordine2);
	if (condizione != 0) {
	    return condizione;
	}
	return descrizione1.compareTo(descrizione2);
    }
}
