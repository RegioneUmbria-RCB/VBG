package it.gruppoinit.pal.gp.core.features.oggetti;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.OggettiStoricoDAO;
import it.gruppoinit.pal.gp.core.domain.OggettiStorico;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.core.service.helper.TipoDocumentoPratica;

/**
 * 
 * @author
 */
public interface OggettiStoricoService extends BaseService<OggettiStorico, PkId> {

    /**
     * @see OggettiStoricoDAO#findAll(Integer, Integer)
     */
    public List<OggettiStorico> findAll(Integer firstResult, Integer maxResult);

    /**
     * 
     */
    public void updateSostituisciOggetto(Integer codiceIstanza, Integer codiceOggettoDaSostituire, Integer codiceOggettoNuovo,
	    TipoDocumentoPratica tipoDocumentoPratica);

    public List<OggettiStorico> findbyCodiceOggetto(Integer codice);

    public boolean isStoricizzato(Integer codiceOggetto);

    /**
     * Recupera la lista di oggetti storico a partire dal codice oggetto
     * 
     * @param codiceOggetto
     * @return
     */
    public List<OggettoStoricoBean> findStoricoBy(Integer codiceOggetto);

    /**
     * Il metodo effettua il ripristino di un oggetto storico, nel seguente modo: 1 - recupera l'oggetto attuale (
     * ricavandolo dalla colonna OGGETTI_STORICO.CODICEOGGETTO ) 2 - recupera l'oggetto storicizzato ( ricavandolo dalla
     * colonna OGGETTI_STORICO.CODICEOGGETTO_SOSTIT ) 3 - aggiorna i dati dell'oggetto attuale con i dati dell'oggetto
     * storicizzato 4 - cancella la riga di OGGETTI_STORICO con ID = idOggettiStorico passato
     * 
     * @param idOggettiStorico
     * @return OGGETTI_STORICO.CODICEOGGETTO
     */
    public Integer ripristina(Integer idOggettiStorico);
}
