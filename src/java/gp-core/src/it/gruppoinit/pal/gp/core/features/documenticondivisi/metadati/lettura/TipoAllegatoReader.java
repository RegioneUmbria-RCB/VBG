package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.TIpoDocumentoDocumentiCondivisiMetadato;
import it.gruppoinit.pal.gp.core.service.DocumentiHelperService;

public class TipoAllegatoReader implements IMetadatiReader {

    private static final String TIPO_ALLEGATO_PROCURA = "Procura";
    private DocumentiCondivisiMetadato tipoAllegato = new TIpoDocumentoDocumentiCondivisiMetadato(null);

    public TipoAllegatoReader(DocumentiHelperService documentiHelper, Istanze istanza, Movimenti movimento, Integer codiceOggetto) {

	if (istanza == null || codiceOggetto == null) {
	    return;
	}
	Integer codiceIstanza = istanza.getId().getCodice();
	Integer codiceMovimento = null;
	if (movimento != null) {
	    codiceMovimento = movimento.getId().getCodice();
	}
	this.tipoAllegato = documentiHelper.findMetadatoProvenienzaDocumento(codiceIstanza, codiceMovimento, codiceOggetto);
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return tipoAllegato;
    }
}
