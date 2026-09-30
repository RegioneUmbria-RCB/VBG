package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Comparator;

public class PosteggioPianoRotazioneHelperComparator implements Comparator<PosteggioPianoRotazioneHelper> {

    @Override
    public int compare(PosteggioPianoRotazioneHelper o1, PosteggioPianoRotazioneHelper o2) {

	// Controlla che gli oggetti non siano vuoti
	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	Integer ordine1 = null;
	Integer ordine2 = null;
	if (o1.getOrdine() != null) {
	    ordine1 = o1.getOrdine();
	}
	if (o2.getOrdine() != null) {
	    ordine2 = o2.getOrdine();
	}
	//		if (ordine1 == null && ordine2 == null) {
	//		    //non riesco a ordinare per  ordine allora uso la descriozne per descrizione
	//		    return getOrdinamentoPerDescrione(o1, o2);
	//		}
	//		if (ordine1 != null && ordine2 == null) {
	//		    return -1;
	//		}
	//		if (ordine1 == null && ordine2 != null) {
	//		    return 1;
	//		}
	int risultato = 0;
	risultato = ordine1.compareTo(ordine2);
	return risultato;
    }
}
