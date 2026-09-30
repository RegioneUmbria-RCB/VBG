package it.gruppoinit.pal.gp.core.service.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResponsabiliAssegnazioniHelper {

    private List<ResponsabiliAssegnazioniHelperBean> responsabiliAssegnatari = new ArrayList<ResponsabiliAssegnazioniHelperBean>();

    public void addResponsabiliAssegnatari(ResponsabiliAssegnazioniHelperBean b) {

	responsabiliAssegnatari.add(b);
    }

    public List<ResponsabiliAssegnazioniHelperBean> getListaResponsabiliOrdinata() {

	if (responsabiliAssegnatari.size() > 0) {
	    Collections.sort(responsabiliAssegnatari);
	}
	return responsabiliAssegnatari;
    }
}
