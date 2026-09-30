package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class InternoReader implements IMetadatiReader {

    private static final String METADATO_INTERNO = "interno";
    private String interno = null;

    public InternoReader(Istanzestradario localizzazione) {

	if (localizzazione == null) {
	    return;
	}
	this.interno = localizzazione.getInterno();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATO_INTERNO, this.interno);
    }
}