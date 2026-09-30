package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;

import java.io.Serializable;
import java.util.Comparator;

public class IstanzeallegatiInventarioprocedimentiAllegatoextraComparator implements Comparator<Istanzeallegati>, Serializable {

    private static final long serialVersionUID = -2323253085952832644L;

    public IstanzeallegatiInventarioprocedimentiAllegatoextraComparator() {

    }

    @Override
    public int compare(Istanzeallegati o1, Istanzeallegati o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	String inventarioprocedimenti1 = o1.getInventarioprocedimenti().getProcedimento();
	String inventarioprocedimenti2 = o2.getInventarioprocedimenti().getProcedimento();
	String allegatoextra1 = o1.getAllegatoextra();
	String allegatoextra2 = o2.getAllegatoextra();
	int condizioneInventarioprocedimenti = inventarioprocedimenti1.compareTo(inventarioprocedimenti2);
	if (condizioneInventarioprocedimenti != 0) {
	    return inventarioprocedimenti1.compareTo(inventarioprocedimenti2);
	}
	return allegatoextra1.compareTo(allegatoextra2);
    }
}
