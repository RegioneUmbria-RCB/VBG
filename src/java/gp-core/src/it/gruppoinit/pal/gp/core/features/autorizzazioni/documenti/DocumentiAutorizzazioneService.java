package it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.DocumentiAutorizzazione;
import it.gruppoinit.pal.gp.core.domain.DocumentiAutorizzazioneId;
import it.gruppoinit.pal.gp.core.service.BaseService;

/**
 * 
 * @author
 */
public interface DocumentiAutorizzazioneService extends BaseService<DocumentiAutorizzazione, DocumentiAutorizzazioneId> {

    /**
     * @see DocumentiAutorizzazioneDAO#findAll(Integer, Integer)
     */
    public List<DocumentiAutorizzazione> findAll(Integer firstResult, Integer maxResult);

    public List<DocumentiAutorizzazione> findByAutorizzazioni(Integer codiceautorizzazione, Integer firstResult, Integer maxResult);

    public List<DocumentiAutorizzazioneDTO> findDocumentiAutorizzazioneDTOByAutorizzazione(Integer codiceAutorizzazione);

    public List<DocumentiAutorizzazione> findDocumentiAutorizzazioneByOggetto(Integer codiceOggetto, Integer firstResult, Integer maxResult);

    public void insertDocumentoAutorizzazione(Integer codiceOggetto, Integer idAutorizzazione, Integer codice, Integer tipocodice);

    public void insertDocumentoAutorizzazione(Integer codiceOggetto, Integer idAutorizzazione, Integer codice, boolean principale,
	    Integer tipocodice);

    /**
     * Verifica se esiste il record in DOCUMENTI_AUTORIZZAZIONE.
     * 
     * @param codiceautorizzazione
     * @param codicedocumento
     *            : codice del documento da cercare.
     * @param associationPath
     *            : il nome della property al quale fa riferimento il documento da cercare. Per esempio se si vuole
     *            verificare l'esistenza di un allegato del movimento in DOCUMENTI AUTORIZZAZIONE, associationPath sarà
     *            uguale a movimentiallegati nome della property di DOCUMENTI_AUTORIZZAZIONE.
     * @return
     */
    public Boolean isInAutorizzazione(Integer codiceautorizzazione, Integer codicedocumento, String associationPath);

    public Boolean isInAutorizzazione(Integer codicedocumento, String associationPath);

    public void impostaDocumentoPrincipale(Integer codiceAutorizzazione, Integer codiceOggetto);

    public boolean documentPrincipalePresente(Integer idAutorizzazione);
}
