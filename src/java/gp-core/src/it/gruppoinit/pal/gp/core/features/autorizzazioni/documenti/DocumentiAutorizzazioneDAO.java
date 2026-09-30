package it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.DocumentiAutorizzazione;
import it.gruppoinit.pal.gp.core.domain.DocumentiAutorizzazioneId;

/**
 * 
 * @author
 */
public interface DocumentiAutorizzazioneDAO extends BaseDAO<DocumentiAutorizzazione, DocumentiAutorizzazioneId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<DocumentiAutorizzazione> findAll(Integer firstResult, Integer maxResult);

    /**
     * Lista dei documenti (DocumentiAutorizzazioneDTO) assegnati all'autorizzazione
     * 
     * @param codiceAutorizzazione
     * @return
     */
    public List<DocumentiAutorizzazioneDTO> findDocumentiAutorizzazioneDTOByAutorizzazione(Integer codiceAutorizzazione);
    //    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByCodicedocumentiistanza(Integer codicedocumentoIstanza, Integer firstResult,
    //	    Integer maxResult);

    public void impostaDocumentoPrincipale(Integer codiceAutorizzazione, Integer codiceOggetto);

    public boolean documentPrincipalePresente(Integer idAutorizzazione);
}
