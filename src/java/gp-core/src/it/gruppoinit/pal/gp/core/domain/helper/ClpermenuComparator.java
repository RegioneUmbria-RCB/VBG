package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Clpermmenu;

import java.io.Serializable;
import java.util.Comparator;

public class ClpermenuComparator implements Comparator<Clpermmenu>, Serializable {

    private static final long serialVersionUID = -461251680328711835L;

    @Override
    public int compare(Clpermmenu item1, Clpermmenu item2) {

	String menuLink1 = item1.getMenu().getMenulink();
	String menuLink2 = item2.getMenu().getMenulink();
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
