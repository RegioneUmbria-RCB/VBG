package it.gruppoinit.pal.gp.core.dao.helper;

import it.gruppoinit.pal.gp.core.domain.SituazioneContabile;

import java.util.Comparator;

public class SituazioneContabileAnnoComparator implements Comparator<Object> {

    private static SituazioneContabileAnnoComparator istance = null;

    private SituazioneContabileAnnoComparator() {

    }

    public static SituazioneContabileAnnoComparator getInstance() {

	if (istance == null) {
	    istance = new SituazioneContabileAnnoComparator();
	}
	return istance;
    }

    @Override
    public int compare(Object o1, Object o2) {

	if (o1 == null && o2 == null)
	    return 0;
	// assuming you want null values shown last
	if (o1 != null && o2 == null)
	    return -1;
	if (o1 == null && o2 != null)
	    return 1;
	if (!(o1 instanceof SituazioneContabile) || !(o2 instanceof SituazioneContabile)) {
	    throw new IllegalArgumentException("...");
	}
	SituazioneContabile c1 = (SituazioneContabile) o1;
	SituazioneContabile c2 = (SituazioneContabile) o2;
	Short anno1 = c1.getAnno();
	Short anno2 = c2.getAnno();
	if (anno1 == null && anno2 == null)
	    return 0;
	// assuming you want null values shown last
	if (anno1 != null && anno2 == null)
	    return -1;
	if (anno1 == null && anno2 != null)
	    return 1;
	return anno1.compareTo(anno2);
    }
}
