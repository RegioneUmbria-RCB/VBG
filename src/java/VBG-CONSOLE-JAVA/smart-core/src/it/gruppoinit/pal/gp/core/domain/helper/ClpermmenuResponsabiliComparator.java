package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Clpermmenu;

import java.io.Serializable;
import java.util.Comparator;

public class ClpermmenuResponsabiliComparator implements Comparator<Clpermmenu>, Serializable {

    private static final long serialVersionUID = -6332872093722567067L;

    @Override
    public int compare(Clpermmenu o1, Clpermmenu o2) {

	String menuLink1 = o1.getMenu().getMenulink() == null ? "" : o1.getMenu().getMenulink();
	String menuLink2 = o2.getMenu().getMenulink() == null ? "" : o2.getMenu().getMenulink();
	if (menuLink1 == null && menuLink2 == null) {
	    return 0;
	}
	if (menuLink1 != null && menuLink2 == null) {
	    return -1;
	}
	if (menuLink1 == null && menuLink2 != null) {
	    return 1;
	}
	return menuLink1.compareTo(menuLink2);
    }
}
