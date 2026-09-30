package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.MailConfig;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class MailConfigComparator implements Comparator<MailConfig> {

    @Override
    public int compare(MailConfig o1, MailConfig o2) {

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
	String ordine1 = null;
	String ordine2 = null;
	if (o1.getComuni() != null) {
	    ordine1 = o1.getComuni().getComune();
	}
	if (o2.getComuni() != null) {
	    ordine2 = o2.getComuni().getComune();
	}
	if (StringUtils.isBlank(ordine1) && StringUtils.isBlank(ordine2)) {
	    //non riesco a ordinare per  ordine allora uso la descriozne per descrizione
	    return getOrdinamentoSoftware(o1, o2);
	}
	if (StringUtils.isNotBlank(ordine1) && StringUtils.isBlank(ordine2)) {
	    return -1;
	}
	if (StringUtils.isBlank(ordine1) && StringUtils.isNotBlank(ordine2)) {
	    return 1;
	}
	int risultato = 0;
	if (ordine1.compareTo(ordine2) == 0) {
	    //non riesco a ordinare per  ordine allora uso la descriozne per descrizione
	    risultato = getOrdinamentoSoftware(o1, o2);
	} else {
	    risultato = ordine1.compareTo(ordine2);
	}
	return risultato;
    }

    private int getOrdinamentoSoftware(MailConfig o1, MailConfig o2) {

	String descrizione1 = "";
	String descrizione2 = "";
	if (o1.getSoftware() != null) {
	    descrizione1 = o1.getSoftware().getDescrizione();
	}
	if (o2.getDescrizione() != null) {
	    descrizione1 = o2.getSoftware().getDescrizione();
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
