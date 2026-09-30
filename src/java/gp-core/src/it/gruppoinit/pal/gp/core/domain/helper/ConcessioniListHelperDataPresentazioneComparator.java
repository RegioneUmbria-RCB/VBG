package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Data istanza
 * 
 * @author gianpaolot
 *
 */
public class ConcessioniListHelperDataPresentazioneComparator implements Comparator<ConcessioniListHelper>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2578704837196681848L;
    private DAOOrderTypeEnum orderTypeEnum;

    public ConcessioniListHelperDataPresentazioneComparator(DAOOrderTypeEnum orderTypeEnum) {

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
	// data rilascio
	java.util.Date dataIstanza1 = ((ConcessioniListHelper) o1).getData_istanza();
	java.util.Date dataIstanza2 = ((ConcessioniListHelper) o2).getData_istanza();
	switch (orderTypeEnum) {
	case ASC:
	    int sCompAsc = dataIstanza1.compareTo(dataIstanza2);
	    return sCompAsc;
	case DESC:
	    int sCompDESC = dataIstanza2.compareTo(dataIstanza1);
	    return sCompDESC;
	default:
	    break;
	}
	return 0;
    }
}
