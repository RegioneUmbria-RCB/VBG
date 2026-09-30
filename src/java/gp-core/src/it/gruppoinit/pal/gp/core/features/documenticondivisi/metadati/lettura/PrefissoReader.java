package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class PrefissoReader implements IMetadatiReader {

    private static final String METADATO_PREFISSO = "prefisso";
    private String prefisso = null;

    public PrefissoReader(Stradario stradario) {

	if (stradario == null) {
	    return;
	}
	this.prefisso = stradario.getPrefisso();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATO_PREFISSO, this.prefisso);
    }
}
