package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class DataIstanzaReader implements IMetadatiReader {

    private static final String METADATI_DATAISTANZA = "data-istanza";
    private Date data = null;

    public DataIstanzaReader(Istanze istanza) {

	if (istanza == null) {
	    return;
	}
	this.data = istanza.getData();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATI_DATAISTANZA, this.data);
    }
}
