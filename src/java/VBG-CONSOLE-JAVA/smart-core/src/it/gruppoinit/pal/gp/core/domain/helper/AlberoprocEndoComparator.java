package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class AlberoprocEndoComparator implements Comparator<AlberoprocEndo> {

    @Override
    public int compare(AlberoprocEndo o1, AlberoprocEndo o2) {

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
	if (o1.getInventarioprocedimento() != null && o1.getInventarioprocedimento().getOrdine() != null) {
	    ordine1 = o1.getInventarioprocedimento().getOrdine();
	}
	if (o2.getInventarioprocedimento() != null && o2.getInventarioprocedimento().getOrdine() != null) {
	    ordine2 = o2.getInventarioprocedimento().getOrdine();
	}
	if (ordine1 == null && ordine2 == null) {
	    //non riesco a ordinare per  ordine allora uso la descrizione 
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
	    //non riesco a ordinare per  ordine allora uso la descrizione 
	    risultato = getOrdinamentoPerDescrione(o1, o2);
	} else {
	    risultato = ordine1.compareTo(ordine2);
	}
	return risultato;
    }

    private int getOrdinamentoPerDescrione(AlberoprocEndo o1, AlberoprocEndo o2) {

	String descrizione1 = "";
	String descrizione2 = "";
	if (StringUtils.isNotBlank(o1.getInventarioprocedimento().getProcedimento())) {
	    descrizione1 = o1.getInventarioprocedimento().getProcedimento();
	}
	if (StringUtils.isNotBlank(o2.getInventarioprocedimento().getProcedimento())) {
	    descrizione2 = o2.getInventarioprocedimento().getProcedimento();
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
