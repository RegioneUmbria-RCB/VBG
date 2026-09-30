package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class NumeroIstanzaReader implements IMetadatiReader {

    private static final String METADATI_NUMEROISTANZA = "numero-istanza";
    private String numeroIstanza = null;

    public NumeroIstanzaReader(Istanze istanza) {

	if (istanza == null) {
	    return;
	}
	this.numeroIstanza = istanza.getNumeroistanza();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATI_NUMEROISTANZA, this.numeroIstanza);
    }
}
