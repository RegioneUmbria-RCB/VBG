package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class RegistrazioniImportiScadenzaComparator implements Comparator<RegistrazioniImporti>, Serializable {

    private static final long serialVersionUID = 9543739190358982L;

    @Override
    public int compare(RegistrazioniImporti o1, RegistrazioniImporti o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	Date data1 = o1.getScadenza();
	Date data2 = o2.getScadenza();
	if (data1 == null && data2 == null) {
	    return 0;
	}
	if (data1 != null && data2 == null) {
	    return -1;
	}
	if (data1 == null && data2 != null) {
	    return 1;
	}
	return data1.compareTo(data2);
    }
}
