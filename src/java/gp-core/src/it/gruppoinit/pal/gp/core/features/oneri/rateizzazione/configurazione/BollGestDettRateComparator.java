package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione;

import java.util.Comparator;

import it.gruppoinit.pal.gp.core.domain.BollGestDettRate;

public class BollGestDettRateComparator implements Comparator<BollGestDettRate> {

    @Override
    public int compare(BollGestDettRate rata1, BollGestDettRate rata2) {

	int c;
	c = rata1.getNumeroRata().compareTo(rata2.getNumeroRata());
	return c;
    }
}
