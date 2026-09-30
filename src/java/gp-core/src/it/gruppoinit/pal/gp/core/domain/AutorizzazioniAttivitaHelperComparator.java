package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniAttivitaHelper;

import java.util.Comparator;

public class AutorizzazioniAttivitaHelperComparator implements Comparator<AutorizzazioniAttivitaHelper> {

    private static final long serialVersionUID = 2578704837196681848L;
    private DAOOrderTypeEnum orderTypeEnum;

    public AutorizzazioniAttivitaHelperComparator(DAOOrderTypeEnum orderTypeEnum) {

	this.orderTypeEnum = orderTypeEnum;
    }

    @Override
    public int compare(AutorizzazioniAttivitaHelper o1, AutorizzazioniAttivitaHelper o2) {

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
	java.util.Date dataAut1 = ((AutorizzazioniAttivitaHelper) o1).getDataAutorizzazione();
	java.util.Date dataAut2 = ((AutorizzazioniAttivitaHelper) o2).getDataAutorizzazione();
	switch (orderTypeEnum) {
	case ASC:
	    int sCompAsc = dataAut1.compareTo(dataAut2);
	    return sCompAsc;
	case DESC:
	    int sCompDESC = dataAut2.compareTo(dataAut1);
	    return sCompDESC;
	default:
	    break;
	}
	return 0;
    }
}
