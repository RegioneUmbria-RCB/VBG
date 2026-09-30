package it.gruppoinit.pal.gp.core.features.documenticondivisi;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.DocumentiCondivisi;

public interface IDocumentiCondivisiDAO extends BaseDAO {

    boolean documentoPresente(Integer idDocumento);

    void impostaStato(Integer idDocumento, StatiDocumentiCondivisiEnum daCondividere);

    Set<DocumentiCondivisi> condividiDocumentiIstanza(Set<Integer> idDocumenti, Integer codiceIstanza);

    DocumentiCondivisi condividiDocumentoIstanza(Integer idDocumento, Integer codiceIstanza);

    Set<DocumentiCondivisi> condividiDocumentiMovimento(Set<Integer> idDocumenti, Integer codiceIstanza, Integer codiceMovimento);

    DocumentiCondivisi condividiDocumentoMovimento(Integer idDocumento, Integer codiceIstanza, Integer codiceMovimento);

    public void deleteByCodiceOggetto(Integer codiceOggetto);

    void condividiDocumenti(List<DocumentiCondivisiHelper> docDaCondividere);

    void deleteByCodiceMovimento(Integer codiceMovimento);
}
