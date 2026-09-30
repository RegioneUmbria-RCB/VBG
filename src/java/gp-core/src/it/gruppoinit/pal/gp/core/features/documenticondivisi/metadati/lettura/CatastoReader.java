package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class CatastoReader implements IMetadatiReader {

    private static final String METADATO_CATASTO = "catasto";
    private String catasto = null;

    public CatastoReader(Istanzemappali mappale) {

	if (mappale == null || mappale.getCatasto() == null) {
	    return;
	}
	this.catasto = mappale.getCatasto().getCodice();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATO_CATASTO, this.catasto);
    }
}
