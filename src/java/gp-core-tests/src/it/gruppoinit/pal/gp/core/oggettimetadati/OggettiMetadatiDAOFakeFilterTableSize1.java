package it.gruppoinit.pal.gp.core.oggettimetadati;

import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;

import java.util.ArrayList;
import java.util.List;

public class OggettiMetadatiDAOFakeFilterTableSize1 extends OggettiMetadatiDAOFake {

    public java.util.List<it.gruppoinit.pal.gp.core.domain.OggettiMetadati> findByFilterTable(
	    it.gruppoinit.pal.gp.core.filters.FilterTable filterTable, Integer firstResult, Integer maxResult) {

	List<OggettiMetadati> result = new ArrayList<OggettiMetadati>();
	OggettiMetadati omd = new OggettiMetadati();
	OggettiMetadatiId id = new OggettiMetadatiId("E256", 1000, "UUID");
	omd.setId(id);
	result.add(omd);
	return result;
    };
}
