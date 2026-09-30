package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class CivicoReader implements IMetadatiReader {

    private static final String METADATO_CIVICO = "civico";
    private String civico = null;

    public CivicoReader(Istanzestradario localizzazione) {

	if (localizzazione == null) {
	    return;
	}
	this.civico = localizzazione.getCivico();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATO_CIVICO, this.civico);
    }
}
