package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class AlberoprocDocumentiComparator implements Comparator<AlberoprocDocumenti> {

    @Override
    public int compare(AlberoprocDocumenti o1, AlberoprocDocumenti o2) {

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
	if (ordine1 == null && ordine2 == null) {
	    //non riesco a ordinare per  ordine allora uso la descriozne per descrizione
	    return getOrdinamentoPerDescrione(o1, o2);
	}
	if (ordine1 != null && ordine2 == null) {
	    return -1;
	}
	if (ordine1 == null && ordine2 != null) {
	    return 1;
	}
	int risultato = 0;
	if (ordine1.compareTo(ordine2) == 0) {
	  //non riesco a ordinare per  ordine allora uso la descriozne per descrizione
	    risultato = getOrdinamentoPerDescrione(o1, o2);
	} else {
	    risultato = ordine1.compareTo(ordine2);
	}
	return risultato;
    }

    private int getOrdinamentoPerDescrione(AlberoprocDocumenti o1, AlberoprocDocumenti o2) {

	String descrizione1 = "";
	String descrizione2 = "";
	if (o1.getDescrizione() != null) {
	    descrizione1 = o1.getDescrizione();
	}
	if (o2.getDescrizione() != null) {
	    descrizione2 = o2.getDescrizione();
	}
	if (StringUtils.isBlank(descrizione1) && StringUtils.isBlank(descrizione2)) {
	    return 0;
	}
	if (StringUtils.isNotBlank(descrizione1) && StringUtils.isBlank(descrizione2)) {
	    return -1;
	}
	if (StringUtils.isBlank(descrizione1) && StringUtils.isNotBlank(descrizione2)) {
	    return 1;
	}
	return descrizione1.compareTo(descrizione2);
    }
}
