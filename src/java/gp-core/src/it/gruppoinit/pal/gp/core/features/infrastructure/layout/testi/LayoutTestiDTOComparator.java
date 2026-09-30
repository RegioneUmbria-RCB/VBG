package it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi;

import java.util.Comparator;

public class LayoutTestiDTOComparator implements Comparator<LayoutTestiDTO> {

    @Override
    public int compare(LayoutTestiDTO testo1, LayoutTestiDTO testo2) {

	int c;
	c = testo1.getCodiceTesto().compareTo(testo2.getCodiceTesto());
	if (c == 0) {
	    c = testo1.getSoftware().compareTo(testo2.getSoftware());
	}
	return c;
    }
}
