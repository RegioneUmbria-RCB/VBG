package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class CodiceEdificioReader implements IMetadatiReader {

    private static final String METADATO_CODICEEDIFICIO = "codice-edificio";
    private String fabbricato = null;

    public CodiceEdificioReader(Istanzestradario localizzazione) {

	if (localizzazione == null) {
	    return;
	}
	this.fabbricato = localizzazione.getFabbricato();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATO_CODICEEDIFICIO, this.fabbricato);
    }
}
