package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoDomandeDAO;
import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.service.helper.FiltriRicercafoDomande;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface FoDomandeService extends BaseService<FoDomande, PkId> {

    /**
     * @see FoDomandeDAO#findAll(Integer, Integer)
     */
    public List<FoDomande> findAll(Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle FoDomande filtrando per idcomune e identificativodomanda
     * 
     * @param identificativodomanda
     * @return
     */
    public List<FoDomande> findByIdentificativodomanda(String identificativodomanda);

    /**
     * restituisce la domanda che ha l'id CART passato come argomento (il campo id CART è utilizzato anche per
     * memorizzare i SEM ID per le domande RFC 329)
     * 
     * @param idCart
     * @return
     */
    public FoDomande findByIdentificativoCart(String idCart);

    /**
     * Torna la lista delle FoDomande di un'anagrafe
     * 
     * @param codiceAnagrafe
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<FoDomande> findInCompilazioneByAnagrafe(String cfUtenteLoggato, Integer firstResult, Integer maxResult);

    public int countInCompilazioneByUtenteLoggato(String cfUtenteLoggato);

    @Override
    public void insert(FoDomande entity);

    public int countByFiltriRicerca(FiltriRicercafoDomande filtriRicercafoDomande);

    public List<FoDomande> findByFiltriRicerca(FiltriRicercafoDomande filtriRicercafoDomande, Integer firstResult, Integer maxResult);

    public void checkDomandaDellUtente(String cfUtenteLoggato, Integer fodomandaid, boolean checkSoloPresentatore) throws SecurityException;

    public void updateSegnaDomandaCancellata(Integer fodomandaid);

    public List<CodiceDescrizioneBean> getListaModuli(String idcomune, Integer codice);

    public boolean checkPermessoTitolare(String idcomune, Integer codice, String cfUtenteLoggato);
}
