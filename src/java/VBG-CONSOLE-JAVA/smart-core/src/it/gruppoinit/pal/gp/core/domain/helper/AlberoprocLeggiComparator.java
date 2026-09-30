package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.AlberoprocLeggi;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class AlberoprocLeggiComparator implements Comparator<AlberoprocLeggi>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8612711704943811114L;

    @Override
    public int compare(AlberoprocLeggi o1, AlberoprocLeggi o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	String descrizioneLegge1 = "";
	String descrizioneLegge2 = "";
	if (o1.getLegge() != null) {
	    descrizioneLegge1 = o1.getLegge().getLeDescrizione();
	}
	if (o2.getLegge() != null) {
	    descrizioneLegge2 = o2.getLegge().getLeDescrizione();
	}
	if (StringUtils.isBlank(descrizioneLegge1) && StringUtils.isBlank(descrizioneLegge2)) {
	    return 0;
	}
	if (StringUtils.isNotBlank(descrizioneLegge1) && StringUtils.isBlank(descrizioneLegge2)) {
	    return -1;
	}
	if (StringUtils.isBlank(descrizioneLegge1) && StringUtils.isNotBlank(descrizioneLegge2)) {
	    return 1;
	}
	return descrizioneLegge1.compareTo(descrizioneLegge2);
    }
}
