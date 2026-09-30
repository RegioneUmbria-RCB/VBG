package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

// STRADARIO_DESCRIZIONE
public class ConcessioniListHelperLocalizzazioneComparator implements Comparator<ConcessioniListHelper>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5595693599543862231L;
    private DAOOrderTypeEnum orderTypeEnum;

    public ConcessioniListHelperLocalizzazioneComparator(DAOOrderTypeEnum orderTypeEnum) {

	this.orderTypeEnum = orderTypeEnum;
    }

    @Override
    public int compare(ConcessioniListHelper o1, ConcessioniListHelper o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	// Numero conc
	String STRADARIO_DESCRIZIONE1 = ((ConcessioniListHelper) o1).getStradario_descrizione();
	String STRADARIO_DESCRIZIONE2 = ((ConcessioniListHelper) o2).getStradario_descrizione();
	STRADARIO_DESCRIZIONE1 = StringUtils.defaultIfEmpty(STRADARIO_DESCRIZIONE1, "");
	STRADARIO_DESCRIZIONE2 = StringUtils.defaultIfEmpty(STRADARIO_DESCRIZIONE2, "");
	switch (orderTypeEnum) {
	case ASC:
	    int sCompAsc = StringUtils.defaultString(STRADARIO_DESCRIZIONE1).compareTo(STRADARIO_DESCRIZIONE2);
	    return sCompAsc;
	case DESC:
	    int sCompDESC = StringUtils.defaultString(STRADARIO_DESCRIZIONE2).compareTo(STRADARIO_DESCRIZIONE1);
	    return sCompDESC;
	default:
	    break;
	}
	return 0;
    }
}
