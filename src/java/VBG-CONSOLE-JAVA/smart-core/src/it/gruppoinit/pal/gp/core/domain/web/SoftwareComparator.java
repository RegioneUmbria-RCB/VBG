package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Software;

import java.io.Serializable;
import java.util.Comparator;

public class SoftwareComparator implements Comparator<Software>, Serializable {

    private static final long serialVersionUID = 6603255567706139185L;

    @Override
    public int compare(Software s1, Software s2) {

	if (s1 == null && s2 == null) {
	    return 0;
	}
	if (s1 != null && s2 == null) {
	    return -1;
	}
	if (s1 == null && s2 != null) {
	    return 1;
	}
	return s1.getDescrizione().compareTo(s2.getDescrizione());
    }
}
