package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class SubReader implements IMetadatiReader {

    private static final String METADATI_SUB = "sub";
    private String sub = null;

    public SubReader(Istanzemappali mappale) {

	if (mappale == null) {
	    return;
	}
	this.sub = mappale.getSub();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATI_SUB, this.sub);
    }
}
