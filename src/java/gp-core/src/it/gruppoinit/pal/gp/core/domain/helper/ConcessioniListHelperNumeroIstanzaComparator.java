package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

// IST_NUMEROISTANZA
public class ConcessioniListHelperNumeroIstanzaComparator implements Comparator<ConcessioniListHelper>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1934566142924931592L;
    private DAOOrderTypeEnum orderTypeEnum;

    public ConcessioniListHelperNumeroIstanzaComparator(DAOOrderTypeEnum orderTypeEnum) {

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
	String IST_NUMEROISTANZA1 = StringUtils.leftPad(((ConcessioniListHelper) o1).getIst_numeroistanza(), 15, "0");
	String IST_NUMEROISTANZA2 = StringUtils.leftPad(((ConcessioniListHelper) o2).getIst_numeroistanza(), 15, "0");
	// data rilascio
	switch (orderTypeEnum) {
	case ASC:
	    int sCompAsc = IST_NUMEROISTANZA1.compareTo(IST_NUMEROISTANZA2);
	    return sCompAsc;
	case DESC:
	    int sCompDESC = IST_NUMEROISTANZA2.compareTo(IST_NUMEROISTANZA1);
	    return sCompDESC;
	default:
	    break;
	}
	return 0;
    }
}
