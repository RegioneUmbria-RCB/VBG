package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Bandiinput;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class BandiinputComparator implements Comparator<Bandiinput>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 242823627505109910L;

    @Override
    public int compare(Bandiinput o1, Bandiinput o2) {

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
	if (o1.getTipibandoinput() != null) {
	    descrizione1 = StringUtils.defaultString(o1.getTipibandoinput().getEtichetta()).toLowerCase();
	}
	if (o2.getTipibandoinput() != null) {
	    descrizione2 = StringUtils.defaultString(o2.getTipibandoinput().getEtichetta()).toLowerCase();
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
