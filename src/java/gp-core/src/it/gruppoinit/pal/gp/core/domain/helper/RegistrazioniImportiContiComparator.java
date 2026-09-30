package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;

import java.io.Serializable;
import java.util.Comparator;

public class RegistrazioniImportiContiComparator implements Comparator<RegistrazioniImporti>, Serializable {

    private static final long serialVersionUID = -7367192161240882347L;

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
	Conti conto1 = o1.getConti();
	Conti conto2 = o2.getConti();
	if (conto1 == null && conto2 == null) {
	    return 0;
	}
	if (conto1 != null && conto2 == null) {
	    return -1;
	}
	if (conto1 == null && conto2 != null) {
	    return 1;
	}
	String descrizione1 = conto1.getDescrizione();
	String descrizione2 = conto2.getDescrizione();
	return descrizione1.compareTo(descrizione2);
    }
}
