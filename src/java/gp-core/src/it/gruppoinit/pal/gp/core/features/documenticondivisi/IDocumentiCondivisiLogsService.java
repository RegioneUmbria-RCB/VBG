package it.gruppoinit.pal.gp.core.features.documenticondivisi;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.DocumentiCondivisi;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public interface IDocumentiCondivisiLogsService {

    void addLogInserimentoDopoProtocollazione(Integer idTestata);

    void addLogInserimentoDopoProtocollazione(Set<DocumentiCondivisi> testate);

    void addLogDocumentoCondiviso(DocumentiCondivisiHelper documento, List<DocumentiCondivisiMetadato> metadati);

    void addErrorLog(DocumentiCondivisiHelper documento, String messaggio);

    void addErrorLog(DocumentiCondivisiHelper documento, String messaggio, List<DocumentiCondivisiMetadato> metadati);

    void addLogInserimentoDocumentoMancante(DocumentiCondivisi docCondiviso);
}
