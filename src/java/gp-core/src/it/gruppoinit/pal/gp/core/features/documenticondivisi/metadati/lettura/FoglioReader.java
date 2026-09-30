package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class FoglioReader implements IMetadatiReader {

    private static final String METADATO_FOGLIO = "foglio";
    private String foglio = null;

    public FoglioReader(Istanzemappali mappale) {

	if (mappale == null) {
	    return;
	}
	this.foglio = mappale.getFoglio();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATO_FOGLIO, this.foglio);
    }
}
