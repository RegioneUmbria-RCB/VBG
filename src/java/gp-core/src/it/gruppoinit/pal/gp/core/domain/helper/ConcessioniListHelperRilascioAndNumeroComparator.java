package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class ConcessioniListHelperRilascioAndNumeroComparator implements Comparator<ConcessioniListHelper>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3177305502409577572L;
    private DAOOrderTypeEnum orderTypeEnum;

    public ConcessioniListHelperRilascioAndNumeroComparator(DAOOrderTypeEnum orderTypeEnum) {

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
	String numeroCon1 = StringUtils.leftPad(((ConcessioniListHelper) o1).getConc_numero(), 15, "0");
	String numeroCon2 = StringUtils.leftPad(((ConcessioniListHelper) o2).getConc_numero(), 15, "0");
	// data rilascio
	java.util.Date dataRilascio1 = ((ConcessioniListHelper) o1).getDatarilascio();
	java.util.Date dataRilascio2 = ((ConcessioniListHelper) o2).getDatarilascio();
	switch (orderTypeEnum) {
	case ASC:
	    int sCompAsc = dataRilascio1.compareTo(dataRilascio2);
	    if (sCompAsc != 0) {
		return sCompAsc;
	    } else {
		return numeroCon1.compareTo(numeroCon2);
	    }
	case DESC:
	    int sCompDESC = dataRilascio2.compareTo(dataRilascio1);
	    if (sCompDESC != 0) {
		return sCompDESC;
	    } else {
		return numeroCon2.compareTo(numeroCon1);
	    }
	default:
	    break;
	}
	return 0;
    }
}
