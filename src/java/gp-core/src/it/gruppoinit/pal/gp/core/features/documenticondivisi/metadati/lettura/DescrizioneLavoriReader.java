package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class DescrizioneLavoriReader implements IMetadatiReader {

    private static final String METADATI_DESCRIZIONELAVORI = "descrizione-lavori";
    private String descrizioneLavori = null;

    public DescrizioneLavoriReader(Istanze istanza) {

	if (istanza == null) {
	    return;
	}
	this.descrizioneLavori = istanza.getLavori();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATI_DESCRIZIONELAVORI, this.descrizioneLavori);
    }
}
