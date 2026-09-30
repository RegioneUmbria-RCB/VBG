package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Tipibandoinput;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class TipibandoinputComparator implements Comparator<Tipibandoinput>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7951869507956732122L;

    @Override
    public int compare(Tipibandoinput o1, Tipibandoinput o2) {

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
	descrizione1 = StringUtils.defaultString(o1.getEtichetta()).toLowerCase();
	descrizione2 = StringUtils.defaultString(o2.getEtichetta()).toLowerCase();
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
