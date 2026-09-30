package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class PartitaIvaReader implements IMetadatiReader {

    private static final String METADATO_PARTITAIVA = "partita-iva";
    private String partitaIva = null;

    public PartitaIvaReader(Anagrafe anagrafe) {

	if (anagrafe == null) {
	    return;
	}
	this.partitaIva = anagrafe.getPartitaiva();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATO_PARTITAIVA, this.partitaIva);
    }
}
