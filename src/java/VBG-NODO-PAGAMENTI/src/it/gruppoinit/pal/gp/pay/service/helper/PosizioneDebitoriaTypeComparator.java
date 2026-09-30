package it.gruppoinit.pal.gp.pay.service.helper;

import java.util.Comparator;

import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaType;

public class PosizioneDebitoriaTypeComparator implements Comparator<PosizioneDebitoriaType> {

    public PosizioneDebitoriaTypeComparator() {

    }

    @Override
    public int compare(PosizioneDebitoriaType o1, PosizioneDebitoriaType o2) {

	if (o1 == null || o1.getDataScadenza() == null) {
	    return o2 == null || o2.getDataScadenza() == null ? 0 : 1;
	} else if (o2 == null || o2.getDataScadenza() == null) {
	    return -1;
	} else {
	    return o1.getDataScadenza().compare(o2.getDataScadenza());
	}
    }
}
