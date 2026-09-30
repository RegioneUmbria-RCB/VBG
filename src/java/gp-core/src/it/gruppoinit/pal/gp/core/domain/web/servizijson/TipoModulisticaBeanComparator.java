package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class TipoModulisticaBeanComparator implements Comparator<TipoModulisticaBean> {

    @Override
    public int compare(TipoModulisticaBean o1, TipoModulisticaBean o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	String descrizione1 = StringUtils.defaultString(o1.getNome());
	String descrizione2 = StringUtils.defaultString(o2.getNome());
	return descrizione1.compareTo(descrizione2);
    }
}
