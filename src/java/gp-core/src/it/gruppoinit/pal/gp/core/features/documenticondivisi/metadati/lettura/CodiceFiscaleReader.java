package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class CodiceFiscaleReader implements IMetadatiReader {

    private static final String METADATO_CODICEFISCALE = "codice-fiscale";
    private String codiceFiscale = null;

    public CodiceFiscaleReader(Anagrafe anagrafe) {

	if (anagrafe == null) {
	    return;
	}
	this.codiceFiscale = anagrafe.getCodicefiscale();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATO_CODICEFISCALE, codiceFiscale);
    }
}
