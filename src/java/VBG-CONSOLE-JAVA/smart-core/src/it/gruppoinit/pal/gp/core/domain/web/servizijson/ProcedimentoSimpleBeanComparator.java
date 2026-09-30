package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class ProcedimentoSimpleBeanComparator implements Comparator<ProcedimentoSimpleBean> {

    @Override
    public int compare(ProcedimentoSimpleBean o1, ProcedimentoSimpleBean o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	String descrizione1 = StringUtils.defaultString(o1.getText()).toLowerCase();
	String descrizione2 = StringUtils.defaultString(o2.getText()).toLowerCase();
	return descrizione1.compareTo(descrizione2);
    }
}
