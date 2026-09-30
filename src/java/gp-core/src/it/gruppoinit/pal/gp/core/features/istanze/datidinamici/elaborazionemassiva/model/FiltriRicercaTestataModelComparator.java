package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model;

import java.util.Comparator;

public class FiltriRicercaTestataModelComparator implements Comparator<FiltriRicercaTestataModel> {

    @Override
    public int compare(FiltriRicercaTestataModel riga1, FiltriRicercaTestataModel riga2) {

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
	//Controllo che la proprietà filtro
	if (riga1.getFiltro() == null && riga2.getFiltro() == null) {
	    return 0;
	}
	if (riga1.getFiltro() != null && riga2.getFiltro() == null) {
	    return -1;
	}
	if (riga1.getFiltro() == null && riga2.getFiltro() != null) {
	    return 1;
	}
	//Ritorno il risultato della comparazione del filtro
	return riga1.getFiltro().compareTo(riga2.getFiltro());
    }
}
