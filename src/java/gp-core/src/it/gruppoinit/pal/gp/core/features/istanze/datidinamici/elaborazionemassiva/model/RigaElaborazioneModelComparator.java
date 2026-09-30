package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model;

import java.util.Comparator;

public class RigaElaborazioneModelComparator implements Comparator<RigaElaborazioneModel> {

    @Override
    public int compare(RigaElaborazioneModel riga1, RigaElaborazioneModel riga2) {

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
	//Controllo che la proprietà numero istanza non sia null
	if (riga1.getNumeroIstanza() == null && riga2.getNumeroIstanza() == null) {
	    return 0;
	}
	if (riga1.getNumeroIstanza() != null && riga2.getNumeroIstanza() == null) {
	    return -1;
	}
	if (riga1.getNumeroIstanza() == null && riga2.getNumeroIstanza() != null) {
	    return 1;
	}
	//Ritorno il risultato della comparazione del numero istanza
	return riga1.getNumeroIstanza().compareTo(riga2.getNumeroIstanza());
    }
}
