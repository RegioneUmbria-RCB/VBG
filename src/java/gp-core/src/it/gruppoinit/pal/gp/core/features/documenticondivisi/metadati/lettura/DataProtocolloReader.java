package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class DataProtocolloReader implements IMetadatiReader {

    private static final String METADATI_DATAPROTOCOLLO = "data-protocollo";
    private Date data = null;

    public DataProtocolloReader(Istanze istanza) {

	if (istanza == null) {
	    return;
	}
	this.data = istanza.getDataprotocollo();
    }

    public DataProtocolloReader(Movimenti movimento) {

	if (movimento == null) {
	    return;
	}
	this.data = movimento.getDataprotocollo();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATI_DATAPROTOCOLLO, data);
    }
}
