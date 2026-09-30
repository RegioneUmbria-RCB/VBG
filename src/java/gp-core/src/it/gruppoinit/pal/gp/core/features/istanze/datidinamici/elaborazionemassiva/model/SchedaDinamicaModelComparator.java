package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model;

import java.util.Comparator;

public class SchedaDinamicaModelComparator implements Comparator<SchedaDinamicaModel> {

    @Override
    public int compare(SchedaDinamicaModel riga1, SchedaDinamicaModel riga2) {

	// Controlla che gli oggetti non siano vuoti
	if (riga1 == null && riga2 == null) {
	    return 0;
	}
	if (riga1 != null && riga2 == null) {
	    return -1;
	}
	if (riga1 == null) {
	    return 1;
	}
	if (riga1.getIdScheda() == riga2.getIdScheda()) {
	    if (riga1.getOrdine() == riga2.getOrdine()) {
		return 0;
	    }
	    return riga1.getOrdine() > riga2.getOrdine() ? 1 : -1;
	} else {
	    return riga1.getIdScheda() > riga2.getIdScheda() ? 1 : -1;
	}
    }
}
