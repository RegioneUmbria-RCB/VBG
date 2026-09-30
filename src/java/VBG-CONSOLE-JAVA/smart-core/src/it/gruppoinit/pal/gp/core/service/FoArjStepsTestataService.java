package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.FoArjStepsTestataDAO;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author fabrizioc
 */
public interface FoArjStepsTestataService extends BaseService<FoArjStepsTestata, PkId> {

    /**
     * @see FoArjStepsTestataDAO#findAll(Integer, Integer)
     */
    public List<FoArjStepsTestata> findAll(Integer firstResult, Integer maxResult);

    public List<FoArjStepsTestata> findByDescrizione(String textToSearch);

    /**
     * metodo per il recupero del workflow di default specificato nella tabella FO_ARCONFIGURAZIONE. Se non lo trova
     * rilancia eccezione.
     * 
     * @return
     * @throws Exception
     */
    public FoArjStepsTestata findTestataDefault() throws Exception;

    /**
     * metodo per il recupero del workflow associato all'intervento. Il metodo rilase l'albero per la ricerca. se non lo
     * trova ritorna null. Se codiceIntervento non è specificato rilancia eccezione.
     * 
     * @param codiceIntervento
     * @return
     * @throws Exception
     */
    public FoArjStepsTestata findTestataIntervento(String idcomune, Integer codiceIntervento) throws Exception;
}
