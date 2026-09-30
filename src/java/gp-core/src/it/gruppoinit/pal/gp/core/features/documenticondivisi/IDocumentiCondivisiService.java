package it.gruppoinit.pal.gp.core.features.documenticondivisi;

import java.util.List;
import java.util.Set;

public interface IDocumentiCondivisiService {

    Boolean documentoPresente(Integer idDocumento);

    void impostaStatoDocumentoDaCondividere(Integer idDocumento);

    void condividiDocumentiIstanza(Set<Integer> idDocumenti, Integer codiceIstanza);

    void condividiDocumentiMovimento(Set<Integer> idDocumenti, Integer codiceMovimento);

    void condividi(List<DocumentiCondivisiHelper> documenti);

    void elaboraDocumenti(IDocumentiReader reader);

    void inserisciDocumentiMancantiInCondivisioneDocumentale(IDocumentiReader reader);

    void deleteByCodiceMovimento(Integer codiceMovimento);
}
