package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class RichiedenteReader implements IMetadatiReader {

    private static final String METADATO_RICHIEDENTE = "richiedente";
    private String richiedente = null;

    public RichiedenteReader(Anagrafe anagrafe) {

	if (anagrafe != null) {
	    this.richiedente = anagrafe.getDescrizioneRichiedenteBreve();
	}
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATO_RICHIEDENTE, richiedente);
    }
}
