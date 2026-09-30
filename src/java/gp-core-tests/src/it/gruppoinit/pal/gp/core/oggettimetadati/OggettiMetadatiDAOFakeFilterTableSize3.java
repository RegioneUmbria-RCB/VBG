package it.gruppoinit.pal.gp.core.oggettimetadati;

import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;

import java.util.ArrayList;
import java.util.List;

public class OggettiMetadatiDAOFakeFilterTableSize3 extends OggettiMetadatiDAOFake {

    public java.util.List<it.gruppoinit.pal.gp.core.domain.OggettiMetadati> findByFilterTable(
	    it.gruppoinit.pal.gp.core.filters.FilterTable filterTable, Integer firstResult, Integer maxResult) {

	List<OggettiMetadati> result = new ArrayList<OggettiMetadati>();
	OggettiMetadati omd = new OggettiMetadati();
	result.add(omd);
	OggettiMetadati omd1 = new OggettiMetadati();
	result.add(omd1);
	OggettiMetadati omd2 = new OggettiMetadati();
	result.add(omd2);
	return result;
    };
}
