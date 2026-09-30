package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class ArchivioPraticheReader implements IMetadatiReader {

    private static final String METADATI_POSIZIONEARCHIVIO = "posizione-archivio";
    private String posizioneArchivio = null;

    public ArchivioPraticheReader(Istanze istanza) {

	if (istanza == null) {
	    return;
	}
	this.posizioneArchivio = istanza.getPosizionearchivio();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATI_POSIZIONEARCHIVIO, posizioneArchivio);
    }
}
