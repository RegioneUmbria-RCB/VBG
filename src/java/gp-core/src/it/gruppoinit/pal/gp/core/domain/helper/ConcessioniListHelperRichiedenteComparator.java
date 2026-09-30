package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;

import java.io.Serializable;
import java.util.Comparator;

// CONC_CODICETITOLARE
public class ConcessioniListHelperRichiedenteComparator implements Comparator<ConcessioniListHelper>, Serializable {

    private static final long serialVersionUID = 6325789133505070164L;
    private DAOOrderTypeEnum orderTypeEnum;

    public ConcessioniListHelperRichiedenteComparator(DAOOrderTypeEnum orderTypeEnum) {

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
	String CONC_CODICETITOLARE1 = ((ConcessioniListHelper) o1).getConc_titolare();
	String CONC_CODICETITOLARE2 = ((ConcessioniListHelper) o2).getConc_titolare();
	switch (orderTypeEnum) {
	case ASC:
	    int sCompAsc = CONC_CODICETITOLARE1.compareTo(CONC_CODICETITOLARE2);
	    return sCompAsc;
	case DESC:
	    int sCompDESC = CONC_CODICETITOLARE2.compareTo(CONC_CODICETITOLARE1);
	    return sCompDESC;
	default:
	    break;
	}
	return 0;
    }
}
