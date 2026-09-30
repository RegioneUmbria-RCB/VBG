package it.gruppoinit.pal.gp.core.domain.helper.menu.v2;

import java.util.Comparator;

public class ClmenuBeanComparator implements Comparator<ClmenuBean> {

    @Override
    public int compare(ClmenuBean o1, ClmenuBean o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	String menuLink1 = o1.getOrdinamento();
	String menuLink2 = o2.getOrdinamento();
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
