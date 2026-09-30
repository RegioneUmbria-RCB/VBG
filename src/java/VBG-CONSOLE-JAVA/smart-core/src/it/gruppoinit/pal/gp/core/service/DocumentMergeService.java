package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentMergeHelper;

public interface DocumentMergeService {

    public byte[] eseguiSostituzioniBaseDocumento(Integer codicelettera, Integer codiceIstanza, Integer codiceMovimento, DocumentMergeHelper userData);

    public Oggetti insertAllegatoDaDocumentoTipo(Integer codicelettera, Integer codiceIstanza, Integer codiceMovimento, DocumentMergeHelper userData);
}
