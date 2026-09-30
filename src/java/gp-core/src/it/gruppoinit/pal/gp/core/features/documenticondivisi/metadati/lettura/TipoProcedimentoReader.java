package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class TipoProcedimentoReader implements IMetadatiReader {

    private static final String METADATI_TIPOPROCEDIMENTO = "tipo-procedimento";
    private String intervento = null;

    public TipoProcedimentoReader(Istanze istanza) {

	if (istanza == null || istanza.getAlberoproc() == null) {
	    return;
	}
	this.intervento = istanza.getAlberoproc().getDescrizioneCompleta();
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATI_TIPOPROCEDIMENTO, this.intervento);
    }
}
