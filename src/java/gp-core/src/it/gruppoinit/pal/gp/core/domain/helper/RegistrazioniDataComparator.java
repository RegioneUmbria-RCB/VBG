package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Registrazioni;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class RegistrazioniDataComparator implements Comparator<Registrazioni>, Serializable {

    private static final long serialVersionUID = 2196501624755914195L;
    /**
     * definisce l'ordinamento crescente da valori più piccoli a valori più grandi
     */
    public static final int ORDER_ASC = 0;
    /**
     * definisce l'ordinamento decrescente da valori più grandi a valori più piccoli
     */
    public static final int ORDER_DESC = 1;
    private int order = ORDER_ASC;

    public RegistrazioniDataComparator() {

    }

    /**
     * il tipo di ordinamento se crescente o decrescente
     * 
     * @param orderAscDesc
     *            intero preso dalle costanti di classe
     * @see RegistrazioniDataComparator.ORDER_ASC
     * @see RegistrazioniDataComparator.ORDER_DESC
     */
    public RegistrazioniDataComparator(int orderAscDesc) {

	if (!(orderAscDesc == ORDER_ASC || orderAscDesc == ORDER_DESC)) {
	    throw new RuntimeException("l'ordinamento deve essere scelto tra le costanti di classe");
	}
	this.order = orderAscDesc;
    }

    @Override
    public int compare(Registrazioni o1, Registrazioni o2) {

	switch (this.order) {
	case ORDER_DESC:
	    return compareDesc(o1, o2);
	default:
	    return compareAsc(o1, o2);
	}
    }

    private int compareAsc(Registrazioni o1, Registrazioni o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	Date data1 = o1.getDataRegistrazione();
	Date data2 = o2.getDataRegistrazione();
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

    private int compareDesc(Registrazioni o1, Registrazioni o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return 1;
	}
	if (o1 == null && o2 != null) {
	    return -1;
	}
	Date data1 = o1.getDataRegistrazione();
	Date data2 = o2.getDataRegistrazione();
	if (data1 == null && data2 == null) {
	    return 0;
	}
	if (data1 != null && data2 == null) {
	    return 1;
	}
	if (data1 == null && data2 != null) {
	    return -1;
	}
	return data1.compareTo(data2);
    }
}
