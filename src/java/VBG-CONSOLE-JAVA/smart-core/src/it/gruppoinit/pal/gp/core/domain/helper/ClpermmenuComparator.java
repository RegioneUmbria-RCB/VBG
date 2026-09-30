package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Clpermmenu;

import java.io.Serializable;
import java.util.Comparator;

public class ClpermmenuComparator implements Comparator<Clpermmenu>, Serializable {

    private static final long serialVersionUID = 2782355963099065557L;

    @Override
    public int compare(Clpermmenu menuItem1, Clpermmenu menuItem2) {

	if (menuItem1 == null && menuItem2 == null) {
	    return 0;
	}
	if (menuItem1 != null && menuItem2 == null) {
	    return -1;
	}
	if (menuItem1 == null && menuItem2 != null) {
	    return 1;
	}
	String software1 = String.valueOf(menuItem1.getSoftware().getOrdine());
	String software2 = String.valueOf(menuItem2.getSoftware().getOrdine());
	String menu1 = menuItem1.getMenu().getMenulink();
	String menu2 = menuItem2.getMenu().getMenulink();
	if (menu1 == null && menu2 == null) {
	    return 0;
	}
	if (menu1 != null && menu2 == null) {
	    return -1;
	}
	if (menu1 == null && menu2 != null) {
	    return 1;
	}
	String stringa1DaComparare = menu1 + software1;
	String stringa2DaComparare = menu2 + software2;
	return stringa1DaComparare.compareTo(stringa2DaComparare);
    }
}
