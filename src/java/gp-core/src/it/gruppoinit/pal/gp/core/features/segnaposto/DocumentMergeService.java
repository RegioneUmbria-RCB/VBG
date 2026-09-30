package it.gruppoinit.pal.gp.core.features.segnaposto;

import it.gruppoinit.pal.gp.core.domain.Oggetti;

public interface DocumentMergeService {

    public byte[] eseguiSostituzioniBaseDocumento(Integer codicelettera, Integer codiceIstanza, Integer codiceMovimento,
	    DocumentMergeHelper userData);

    public byte[] eseguiSostituzioniBaseDocumento(Integer codicelettera, DocumentMergeHelper userData);

    public Oggetti insertAllegatoDaDocumentoTipo(Integer codicelettera, Integer codiceIstanza, Integer codiceMovimento, DocumentMergeHelper userData);

    public Oggetti createAllegatoDaDocumentoTipo(Integer codicelettera, Integer codiceIstanza, Integer codiceMovimento, DocumentMergeHelper userData);

    public String getUrlGeneraAllegato();

    public void verificaConvertiRtfInOdt(Integer codiceOggetto, boolean saveOggetto);
}
