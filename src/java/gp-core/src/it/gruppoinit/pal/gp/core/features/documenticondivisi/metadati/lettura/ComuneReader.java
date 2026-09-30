package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class ComuneReader implements IMetadatiReader {

    private static final String METADATI_COMUNE = "comune";
    private String comune = null;

    public ComuneReader(Istanze istanza) {

	if (istanza == null) {
	    return;
	}
	this.comune = istanza.getComune().getComune();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATI_COMUNE, this.comune);
    }
}
