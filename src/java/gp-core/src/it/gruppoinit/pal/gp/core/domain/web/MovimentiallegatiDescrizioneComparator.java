package it.gruppoinit.pal.gp.core.domain.web;

import java.io.Serializable;
import java.util.Comparator;

public class MovimentiallegatiDescrizioneComparator implements Comparator<MovimentiallegatiValoreBean>, Serializable {

    private static final long serialVersionUID = 1041115458644663643L;

    @Override
    public int compare(MovimentiallegatiValoreBean o1, MovimentiallegatiValoreBean o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	String allegatoextra1 = o1.getChiave().getDescrizione();
	String allegatoextra2 = o2.getChiave().getDescrizione();
	if (allegatoextra1 == null && allegatoextra2 == null) {
	    return 0;
	}
	if (allegatoextra1 != null && allegatoextra2 == null) {
	    return -1;
	}
	if (allegatoextra1 == null && allegatoextra2 != null) {
	    return 1;
	}
	return allegatoextra1.compareTo(allegatoextra2);
    }
}
