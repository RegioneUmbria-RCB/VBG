package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class NumeroProtocolloReader implements IMetadatiReader {

    private static final String METADATI_NUMEROPROTOCOLLO = "numero-protocollo";
    private String numeroProtocollo = null;

    public NumeroProtocolloReader(Istanze istanza) {

	if (istanza == null) {
	    return;
	}
	this.numeroProtocollo = istanza.getNumeroprotocollo();
    }

    public NumeroProtocolloReader(Movimenti movimento) {

	if (movimento == null) {
	    return;
	}
	this.numeroProtocollo = movimento.getNumeroprotocollo();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATI_NUMEROPROTOCOLLO, this.numeroProtocollo);
    }
}
