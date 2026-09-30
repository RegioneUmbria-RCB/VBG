package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;

import java.io.Serializable;
import java.util.Comparator;

public class Dyn2ModellitComparator implements Comparator<Dyn2Modellit>, Serializable {

    private static final long serialVersionUID = -8546314937359946148L;

    @Override
    public int compare(Dyn2Modellit o1, Dyn2Modellit o2) {

	String descrizione1 = o1.getDescrizione();
	String descrizione2 = o2.getDescrizione();
	if (descrizione1 == null && descrizione2 == null) {
	    return 0;
	}
	if (descrizione1 != null && descrizione2 == null) {
	    return -1;
	}
	if (descrizione1 == null && descrizione2 != null) {
	    return 1;
	}
	return descrizione1.compareTo(descrizione2);
    }
}
