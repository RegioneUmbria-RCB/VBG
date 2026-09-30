package it.gruppoinit.pal.gp.core.features.bollettazione.rateizzazioni;

import java.util.Comparator;

public class BollettazioneRataBeanComparator implements Comparator<BollettazioneRataBean> {

    @Override
    public int compare(BollettazioneRataBean rata1, BollettazioneRataBean rata2) {

	return rata1.getImportoMinimo().compareTo(rata2.getImportoMinimo());
    }
}