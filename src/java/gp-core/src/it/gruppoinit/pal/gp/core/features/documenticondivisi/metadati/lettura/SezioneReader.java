package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class SezioneReader implements IMetadatiReader {

    private static final String METADATO_SEZIONE = "sezione";
    private String sezione = null;

    public SezioneReader(Istanzemappali mappale) {

	if (mappale == null) {
	    return;
	}
	this.sezione = mappale.getSezione();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATO_SEZIONE, this.sezione);
    }
}
