package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class ParticellaReader implements IMetadatiReader {

    private static final String METADATI_PARTICELLA = "particella";
    private String particella = null;

    public ParticellaReader(Istanzemappali mappale) {

	if (mappale == null) {
	    return;
	}
	this.particella = mappale.getParticella();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATI_PARTICELLA, this.particella);
    }
}
