package it.gruppoinit.pal.gp.core.features.documenticondivisi;

import java.util.List;

public interface IDocumentiReader {

    List<DocumentiCondivisiHelper> getDocumentiDaInviare();

    List<DocumentoMancanteInCondivisioneDocumentale> getDocumentiDaCondividere();
}
