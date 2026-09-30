package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.web.IstanzeallegatiValoreBean;

import java.io.Serializable;
import java.util.Comparator;

public class IstanzeallegatiAllegatoExtraComparator implements Comparator<IstanzeallegatiValoreBean>, Serializable {

    private static final long serialVersionUID = -5501684397220201410L;

    @Override
    public int compare(IstanzeallegatiValoreBean o1, IstanzeallegatiValoreBean o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	String allegatoextra1 = o1.getChiave().getAllegatoextra();
	String allegatoextra2 = o2.getChiave().getAllegatoextra();
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
