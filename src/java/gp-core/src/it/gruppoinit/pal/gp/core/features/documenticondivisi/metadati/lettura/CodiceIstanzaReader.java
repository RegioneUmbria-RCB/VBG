package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class CodiceIstanzaReader implements IMetadatiReader {

    private static final String METADATI_CODICEISTANZA = "codice-istanza";
    private String codiceIstanza = null;

    public CodiceIstanzaReader(Istanze istanza) {

	if (istanza == null) {
	    return;
	}
	this.codiceIstanza = istanza.getId().getCodice().toString();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATI_CODICEISTANZA, this.codiceIstanza);
    }
}
