package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class ViaReader implements IMetadatiReader {

    private static final String METADATO_VIA = "via";
    private String descrizioneVia = null;

    public ViaReader(Stradario stradario) {

	if (stradario == null) {
	    return;
	}
	this.descrizioneVia = stradario.getDescrizione();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATO_VIA, this.descrizioneVia);
    }
}