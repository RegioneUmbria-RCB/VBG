package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.Comparator;

public class PresenzeSpuntistiHelperComparator implements Comparator<PresenzeSpuntistiHelper> {

    @Override
    public int compare(PresenzeSpuntistiHelper o1, PresenzeSpuntistiHelper o2) {

	int numpresC = o1.getNumeropresenze() - o2.getNumeropresenze();
	if (numpresC != 0) {
	    // deve essere ordinato DESC inverto l'ordine
	    if (numpresC > 0) {
		return -1;
	    } else {
		return 1;
	    }
	}
	// lo stesso numero presenze valuto la data anzianita CCIAA
	Integer thisAnnoCciaa = 99993112;
	Integer otherAnnoCciaa = 99993112;
	if (o1.getDataCciaa() != null) {
	    String anno = Utilities.formatDate(o1.getDataCciaa(), "yyyyMMdd");
	    thisAnnoCciaa = Integer.valueOf(anno);
	}
	if (o2.getDataCciaa() != null) {
	    String anno = Utilities.formatDate(o2.getDataCciaa(), "yyyyMMdd");
	    otherAnnoCciaa = Integer.valueOf(anno);
	}
	int cciaaC = thisAnnoCciaa.compareTo(otherAnnoCciaa);
	if (cciaaC != 0) {
	    // deve essere ordinato ASC
	    return cciaaC;
	}
	// lo stesso numero presenze valuto la data anzianita autorizzazione
	Integer thisAnnoAut = 99993112;
	Integer otherAnnoAut = 99993112;
	if (o1.getDataAutorizzazione() != null) {
	    String anno = Utilities.formatDate(o1.getDataAutorizzazione(), "yyyyMMdd");
	    thisAnnoAut = Integer.valueOf(anno);
	}
	if (o2.getDataAutorizzazione() != null) {
	    String anno = Utilities.formatDate(o2.getDataAutorizzazione(), "yyyyMMdd");
	    otherAnnoAut = Integer.valueOf(anno);
	}
	int AutC = thisAnnoAut.compareTo(otherAnnoAut);
	if (AutC != 0) {
	    // deve essere ordinato ASC
	    return AutC;
	}
	return 0;
    }
}
