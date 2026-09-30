package it.gruppoinit.pal.gp.core.service.helper;

import java.io.Serializable;
import java.util.Comparator;

public class AutorizzazioniGraduatoriaRestHelperComparator implements Comparator<AutorizzazioniGraduatoriaRestHelper>, Serializable {

    public static enum TIPO_COMPARAZIONE {
	NUMERO_PRESENZE
    };

    private TIPO_COMPARAZIONE tipoComparazione;

    private AutorizzazioniGraduatoriaRestHelperComparator() {

	super();
    }

    public AutorizzazioniGraduatoriaRestHelperComparator(TIPO_COMPARAZIONE tipoComparazione) {

	this();
	this.tipoComparazione = tipoComparazione;
    }

    @Override
    public int compare(AutorizzazioniGraduatoriaRestHelper o1, AutorizzazioniGraduatoriaRestHelper o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return 1;
	}
	if (o1 == null && o2 != null) {
	    return -1;
	}
	switch (this.tipoComparazione) {
	case NUMERO_PRESENZE:
	    Integer numpresenze1 = Integer.valueOf(o1.getNumero_presenze());
	    Integer numpresenze2 = Integer.valueOf(o2.getNumero_presenze());
	    return numpresenze2.compareTo(numpresenze1); // desc
	default:
	    break;
	}
	return 0;
    }
}
