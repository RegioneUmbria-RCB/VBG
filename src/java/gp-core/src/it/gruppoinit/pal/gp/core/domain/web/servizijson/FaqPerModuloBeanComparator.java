package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class FaqPerModuloBeanComparator implements Comparator<FaqPerModuloBean> {

    @Override
    public int compare(FaqPerModuloBean o1, FaqPerModuloBean o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	String descrizione1 = StringUtils.defaultString(o1.getDescrizione());
	String descrizione2 = StringUtils.defaultString(o2.getDescrizione());
	return descrizione1.compareTo(descrizione2);
    }
}
